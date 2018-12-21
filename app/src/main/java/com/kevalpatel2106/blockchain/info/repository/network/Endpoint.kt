/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.repository.network

import com.kevalpatel2106.blockchain.info.repository.dto.MultiAddrDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * List of all the server endpoints.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal interface Endpoint {

    @GET("multiaddr?n=100")
    fun getData(@Query("active") active: String, @Query("offset") offset: Int = 0): Single<MultiAddrDto>
}