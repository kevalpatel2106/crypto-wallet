/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.repository

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test class for [SharedPrefsProvider].
 */
@RunWith(AndroidJUnit4::class)
class SharedPrefsProviderTest {

    private val TEST_KEY = "test_key"

    private lateinit var mockSharedPreference: SharedPreferences

    private lateinit var sharedPrefsProvider: SharedPrefsProvider

    @Before
    fun setUp() {
        mockSharedPreference = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext())
        sharedPrefsProvider = SharedPrefsProvider(mockSharedPreference)
    }

    @After
    fun after() {
        mockSharedPreference.edit().clear().commit()
    }

    @Test
    @Throws(Exception::class)
    fun removePreferences() {
        val editor = mockSharedPreference.edit()
        editor.putString(TEST_KEY, "String")
        editor.apply()

        sharedPrefsProvider.removePreferences(TEST_KEY)
        assertTrue(mockSharedPreference.getString(TEST_KEY, null) == null)
    }

    @Test
    @Throws(Exception::class)
    fun savePreferences() {
        assertFalse(mockSharedPreference.getInt(TEST_KEY, -1) != -1)
        sharedPrefsProvider.savePreferences {
            it.putString(TEST_KEY, "String")
        }
        assertTrue(mockSharedPreference.getString(TEST_KEY, null) != null)
    }

    @Test
    @Throws(Exception::class)
    fun getStringFromPreferences() {
        val testObserver = sharedPrefsProvider.observeStringFromPreference(TEST_KEY).test()
        val testVal = "String"

        val editor = mockSharedPreference.edit()
        editor.putString(TEST_KEY, testVal)
        editor.apply()

        testObserver.awaitCount(2)
        testObserver.assertNoErrors()
            .assertNotComplete()
            .assertValueAt(1) { it == testVal }
    }

    @Test
    @Throws(Exception::class)
    fun getBoolFromPreferences() {
        val testObserver = sharedPrefsProvider.observeBoolFromPreference(TEST_KEY).test()
        val testVal = true

        val editor = mockSharedPreference.edit()
        editor.putBoolean(TEST_KEY, testVal)
        editor.apply()

        testObserver.awaitCount(2)
        testObserver.assertNoErrors()
            .assertNotComplete()
            .assertValueAt(1) { it == testVal }
    }

    @Test
    @Throws(Exception::class)
    fun getLongFromPreference() {
        val testObserver = sharedPrefsProvider.observeLongFromPreference(TEST_KEY).test()
        val testVal = 100000L

        val editor = mockSharedPreference.edit()
        editor.putLong(TEST_KEY, testVal)
        editor.apply()

        testObserver.awaitCount(2)
        testObserver.assertNoErrors()
            .assertNotComplete()
            .assertValueAt(1) { it == testVal }
    }

    @Test
    @Throws(Exception::class)
    fun getIntFromPreference() {
        val testObserver = sharedPrefsProvider.observeIntFromPreference(TEST_KEY).test()
        val testVal = 100

        val editor = mockSharedPreference.edit()
        editor.putInt(TEST_KEY, testVal)
        editor.apply()

        testObserver.awaitCount(2)
        testObserver.assertNoErrors()
            .assertNotComplete()
            .assertValueAt(1) { it == testVal }
    }
}
