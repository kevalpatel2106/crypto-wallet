/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.utils

import androidx.lifecycle.MutableLiveData

/**
 * Extention functions for live data
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

/**
 * Regenerate the onChanged event without changing the data.
 */
fun <T> MutableLiveData<T>.recall() {
    value = value
}