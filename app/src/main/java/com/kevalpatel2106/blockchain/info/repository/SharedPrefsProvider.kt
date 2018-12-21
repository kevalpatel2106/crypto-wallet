/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable

/**
 * Class contains all the helper functions to deal with shared prefs.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class SharedPrefsProvider(private val sharedPreference: SharedPreferences) {
    private val rxPreferences = RxSharedPreferences.create(sharedPreference)

    /**
     * Remove and clear data from preferences for given field
     *
     * @param key key of preference field to remove
     */
    fun removePreferences(key: String) {
        sharedPreference.edit { remove(key) }
    }

    fun savePreferences(save: (edit: SharedPreferences.Editor) -> Unit) {
        sharedPreference.edit { save.invoke(this) }
    }

    @JvmOverloads
    fun observeStringFromPreference(key: String, defVal: String? = null): Observable<String> {
        return run {
            if (defVal != null) {
                rxPreferences.getString(key, defVal)
            } else {
                rxPreferences.getString(key)
            }
        }.asObservable()
    }

    @JvmOverloads
    fun observeBoolFromPreference(key: String, defVal: Boolean = false): Observable<Boolean> {
        return rxPreferences.getBoolean(key, defVal).asObservable()
    }

    @JvmOverloads
    fun observeLongFromPreference(key: String, defVal: Long = -1): Observable<Long> {
        return rxPreferences.getLong(key, defVal).asObservable()
    }

    @JvmOverloads
    fun observeIntFromPreference(key: String, defVal: Int = -1): Observable<Int> {
        return rxPreferences.getInteger(key, defVal).asObservable()
    }
}
