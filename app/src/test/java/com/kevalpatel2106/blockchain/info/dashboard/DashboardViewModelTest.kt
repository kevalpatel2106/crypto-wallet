package com.kevalpatel2106.blockchain.info.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.kevalpatel2106.blockchain.info.bin.Transaction
import com.kevalpatel2106.blockchain.info.repository.BIRepository
import com.kevalpatel2106.blockchain.info.repository.SharedPrefsProvider
import com.kevalpatel2106.blockchain.info.repository.network.Network
import com.kevalpatel2106.testutils.MockServerManager
import com.kevalpatel2106.testutils.MockSharedPreference
import com.kevalpatel2106.testutils.RxSchedulersOverrideRule
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.File

@RunWith(JUnit4::class)
class DashboardViewModelTest {

    @Rule
    @JvmField
    val rxRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val liveDataRule = InstantTaskExecutorRule()

    @Mock
    lateinit var transactionObserver: Observer<List<Transaction>>

    private var mockSharedPreference = MockSharedPreference()
    private val mockServerManager = MockServerManager()
    private val repository: BIRepository by lazy {
        BIRepository(
            Network(mockServerManager.getBaseUrl(), true),
            SharedPrefsProvider(mockSharedPreference)
        )
    }

    @Before
    fun setUp() {
        mockServerManager.startMockWebServer()
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        mockServerManager.close()
        mockSharedPreference.edit().clear().commit()
    }

    @Test
    fun checkInit() {
        mockServerManager.enqueueResponse(RESPONSE_JSON)
        val model = DashboardViewModel(repository)

        Assert.assertNull(model.errorMessage.value)
        Assert.assertFalse(model.isInitialLoading.value ?: true)
        Assert.assertNotNull(model.transactions.value)
    }

    @Test
    fun givenLoadingFirstPage_whenLoadingTransaction_checkTransactionsList() {
        mockServerManager.enqueueResponse(RESPONSE_JSON)

        val model = DashboardViewModel(repository)

        //Load first page
        model.transactions.observeForever(transactionObserver)

        Mockito.verify(transactionObserver, atLeastOnce()).onChanged(ArgumentMatchers.any())
        Assert.assertEquals(NO_OF_TRANSACTIONS_IN_PAGE, model.transactions.value?.size)

        model.transactions.removeObserver(transactionObserver)
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