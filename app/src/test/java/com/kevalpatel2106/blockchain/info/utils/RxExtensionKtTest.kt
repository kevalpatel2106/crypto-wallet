package com.kevalpatel2106.blockchain.info.utils

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RxExtensionKtTest {

    @Test
    fun checkAddDisposable() {
        val compositeDisposable = CompositeDisposable()
        Observable.just("").subscribe().addTo(compositeDisposable)
        assert(compositeDisposable.size() == 1)
    }
}