package com.example.cryptocoinapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Object to create and provide a Retrofit instance.
object ApiUtilities {
    // Function to create and return the Retrofit instance.
    fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.coinmarketcap.com/") // The base URL for the API.
            .addConverterFactory(GsonConverterFactory.create()) // Adds a converter to handle JSON to object conversion.
            .build() // Builds and returns the Retrofit instance.
    }
}
