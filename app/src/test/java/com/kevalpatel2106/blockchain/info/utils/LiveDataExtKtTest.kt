/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(JUnit4::class)
class LiveDataExtKtTest {
    private val testString = "test"

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var testLiveData: MutableLiveData<String>

    @Mock
    private lateinit var eventObserver: Observer<String>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this@LiveDataExtKtTest)

        testLiveData = MutableLiveData()
        testLiveData.value = testString
    }

    @Test
    fun checkNullSafeObserver_whenNullValueSet() {
        testLiveData.value = testString

        testLiveData.observeForever(eventObserver)
        testLiveData.value = null

        Mockito.verify(eventObserver, Mockito.never())
    }

    @Test
    fun checkNullSafeObserver_whenNonNullValueSet() {
        testLiveData.value = ""

        testLiveData.observeForever(eventObserver)
        testLiveData.value = testString

        Mockito.verify(eventObserver, Mockito.times(1)).onChanged(testString)
    }
}