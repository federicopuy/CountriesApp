package com.example.countriesapp.data.network

import com.example.countriesapp.data.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkApiDataSource @Inject constructor(
    private val countriesApi: RetrofitCountriesApi,
) : CountriesApi {

    override fun fetchCountries(): Flow<List<Country>> = flow {
        emit(countriesApi.getAllCountries().map { it.toCountry() })
    }
}