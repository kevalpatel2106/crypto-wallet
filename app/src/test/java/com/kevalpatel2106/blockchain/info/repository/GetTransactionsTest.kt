package com.kevalpatel2106.blockchain.info.repository

import com.kevalpatel2106.blockchain.info.repository.network.Network
import com.kevalpatel2106.testutils.MockServerManager
import com.kevalpatel2106.testutils.MockSharedPreference
import com.kevalpatel2106.testutils.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class GetTransactionsTest {

    @Rule
    @JvmField
    val rxRule = RxSchedulersOverrideRule()

    private var mockSharedPreference = MockSharedPreference()
    private lateinit var mockServerManager: MockServerManager
    private lateinit var biRepository: BIRepository

    @Before
    fun setUp() {
        mockServerManager = MockServerManager()
        mockServerManager.startMockWebServer()

        biRepository = BIRepository(
            Network(mockServerManager.getBaseUrl(), true),
            SharedPrefsProvider(mockSharedPreference)
        )
    }

    @After
    fun tearDown() {
        mockServerManager.close()
        mockSharedPreference.edit().clear().commit()
    }

    @Test
    fun givenAddress_whenLoadingFirstPage_checkResponse() {
        mockServerManager.enqueueResponse(RESPONSE_JSON)

        val testObserver = biRepository.getTransactions("34234756", 0).test()

        testObserver.awaitTerminalEvent()

        testObserver.assertNoErrors()
            .assertComplete()
            .assertValueAt(0) { it.first.size == NO_OF_TRANSACTIONS_IN_PAGE }
            .assertValueAt(0) { it.second == NO_OF_TRANSACTIONS }
    }

    @Test
    fun givenAddress_whenLoadingSuccess_checkWalletInfoCached() {
        mockServerManager.enqueueResponse(RESPONSE_JSON)

        val testObserver = biRepository.getTransactions("34234756", 0).test()

        testObserver.awaitTerminalEvent()

        testObserver.assertNoErrors()
            .assertComplete()
            .assertValue { mockSharedPreference.getLong(BIRepository.FINAL_BALANCE_KEY, 0L) == FINAL_BALANCE }
            .assertValue { mockSharedPreference.getLong(BIRepository.NO_OF_TRANSACTIONS_KEY, 0L) == NO_OF_TRANSACTIONS }
            .assertValue { mockSharedPreference.getLong(BIRepository.TOTAL_RECEIVED_KEY, 0L) == TOTAL_RECEIVED }
            .assertValue { mockSharedPreference.getLong(BIRepository.TOTAL_SENT_KEY, 0L) == TOTAL_SENT }
    }

    @Test
    fun givenAddress_whenLoadingSecondPage_checkResponse() {
        mockServerManager.enqueueResponse(RESPONSE_JSON)

        val testObserver = biRepository.getTransactions("34234756", 200).test()

        testObserver.awaitTerminalEvent()

        testObserver.assertNoErrors()
            .assertComplete()
            .assertValueAt(0) { it.first.size == NO_OF_TRANSACTIONS_IN_PAGE }
            .assertValueAt(0) { it.second == NO_OF_TRANSACTIONS }
    }

    @Test
    fun givenNoTransactionInPage_whenLoading_checkResponse() {
        mockServerManager.enqueueResponse(EMPTY_RESPONSE_JSON)

        val testObserver = biRepository.getTransactions("34234756", 200).test()

        testObserver.awaitTerminalEvent()

        testObserver.assertNoErrors()
            .assertComplete()
            .assertValueAt(0) { it.first.isEmpty() }
            .assertValueAt(0) { it.second == NO_OF_TRANSACTIONS }
    }

    @Test
    fun given500FromServer_whenLoading_checkResponse() {
        mockServerManager.enqueueResponse(response = "", responseCode = HttpURLConnection.HTTP_INTERNAL_ERROR)

        val testObserver = biRepository.getTransactions("34234756", 200).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertErrorMessage("HTTP 500 Server Error")
    }

    companion object {
        private val RESPONSE_JSON: File = File(MockServerManager.getResponsesPath(), "multi_addr_response.json")
        private val EMPTY_RESPONSE_JSON: File =
            File(MockServerManager.getResponsesPath(), "multi_addr_empty_response.json")

        private const val NO_OF_TRANSACTIONS = 380L
        private const val NO_OF_TRANSACTIONS_IN_PAGE = 3
        private const val FINAL_BALANCE = 621236L
        private const val TOTAL_RECEIVED = 29715281L
        private const val TOTAL_SENT = 29094045L
    }
}