package com.example.countriesapp.data

import com.example.countriesapp.data.model.Country
import com.example.countriesapp.data.network.NetworkApiDataSource
import com.example.countriesapp.data.persistence.RoomDBDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val networkApiDataSource: NetworkApiDataSource,
    private val roomDBDataSource: RoomDBDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    /** Returns a flow containing the entire list of countries*/
    val allCountries: Flow<List<Country>> =
        roomDBDataSource.getAllCountries()
            .onStart {
                networkApiDataSource.fetchCountries().collect { countries ->
                    countries.forEach { roomDBDataSource.saveCountry(it) }
                }
            }
            .flowOn(dispatcher)

    /** Returns a flow containing the list of countries whose name begins with [searchValue]*/
    fun getCountriesByName(searchValue: String): Flow<List<Country>> =
        roomDBDataSource.getAllCountries().map { countries ->
            countries.filter { it.name.lowercase().startsWith(searchValue.lowercase()) }
        }.flowOn(dispatcher)
}

