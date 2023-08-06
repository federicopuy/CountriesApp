package com.example.countriesapp.data.network

import com.example.countriesapp.data.model.Country
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface RetrofitCountriesApi {

    @GET("all?fields=name,flags")
    suspend fun getAllCountries(): List<CountryDto>
}

interface CountriesApi {
    /** Returns a flow containing a list of all the countries */
    fun fetchCountries(): Flow<List<Country>>
}