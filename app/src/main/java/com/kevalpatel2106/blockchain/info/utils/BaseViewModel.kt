/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.utils

import androidx.annotation.CallSuper
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base class for [ViewModel].
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * [CompositeDisposable] to hold all the disposables from Rx and repository.
     */
    @VisibleForTesting
    internal val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Add new [Disposable] to be dispose when the view model cleared.
     */
    protected fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        //Delete all the API connections.
        compositeDisposable.dispose()
    }
}
