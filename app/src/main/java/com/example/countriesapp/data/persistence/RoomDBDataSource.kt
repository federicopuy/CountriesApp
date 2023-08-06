package com.example.countriesapp.data.persistence

import com.example.countriesapp.data.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDBDataSource(private val countryDao: CountryDao) {

    suspend fun saveCountry(country: Country) {
        countryDao.insert(country.toCountryEntity())
    }

    fun getAllCountries(): Flow<List<Country>> =
        countryDao.getAllCountries().map { countryList ->
            countryList.map { it.toCountry() }
        }
}