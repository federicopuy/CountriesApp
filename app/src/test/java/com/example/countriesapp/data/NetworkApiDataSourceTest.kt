package com.example.countriesapp.data

import com.example.countriesapp.data.network.NetworkApiDataSource
import com.example.countriesapp.data.network.RetrofitCountriesApi
import com.example.countriesapp.testCountries
import com.example.countriesapp.testCountriesNetworkDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkApiDataSourceTest {

    private val retrofitCountriesApi = mockk<RetrofitCountriesApi>()
    private val networkApiDataSource =
        NetworkApiDataSource(retrofitCountriesApi)

    @Test
    fun `country dto items are fetched from api and mapped into country items`() {
        runTest {
            coEvery { retrofitCountriesApi.getAllCountries() } returns testCountriesNetworkDto

            val result = networkApiDataSource.fetchCountries().single()

            assertEquals(testCountries, result)
            coVerify { retrofitCountriesApi.getAllCountries() }
        }
    }
}