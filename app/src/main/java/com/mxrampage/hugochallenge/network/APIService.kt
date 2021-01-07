package com.mxrampage.hugochallenge.network

import com.mxrampage.hugochallenge.dashboard.Beers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("beers")
    suspend fun getBeersFromNetwork(): Response<List<Beers>>

    @GET("beers")
    suspend fun searchBeersOnNetwork(
        @Query("beer_name") beerName: String
    ): Response<List<Beers>>

    @GET("beers/{id}")
    suspend fun getBeerDetailsFromNetwork(
        @Path("id") beerId: Long
    ): Response<List<Beers>>
}
