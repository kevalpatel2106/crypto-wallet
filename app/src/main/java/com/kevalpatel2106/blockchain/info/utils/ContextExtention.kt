/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.utils

import android.content.Context
import android.content.Intent
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * Extentions for context.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

/**
 * Get the color from color res.
 */
fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun <T : AppCompatActivity> Context.prepareLaunchIntent(
        aClass: Class<T>,
        isNewTask: Boolean = false
): Intent {

    return Intent(this, aClass).apply {
        if (isNewTask) {
            addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            or Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }
    }
}