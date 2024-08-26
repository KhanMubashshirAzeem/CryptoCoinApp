package com.example.cryptocoinapp.fragment.api

import retrofit2.Response
import retrofit2.http.GET
import com.example.cryptocoinapp.models.MarketModel

// Retrofit interface to define the API endpoint for fetching market data.
interface ApiInterface {
    // The @GET annotation indicates that this method will send a GET request.
    // The URL endpoint is appended to the base URL in the Retrofit instance.
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel> // The function returns a Response containing MarketModel.
}
