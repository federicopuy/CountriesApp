package com.example.countriesapp.data

import com.example.countriesapp.data.model.Country
import com.example.countriesapp.data.network.NetworkApiDataSource
import com.example.countriesapp.data.persistence.RoomDBDataSource
import com.example.countriesapp.testCountries
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CountriesRepositoryTest {

    private val networkApiDataSource = mockk<NetworkApiDataSource>()
    private val roomDBDataSource = mockk<RoomDBDataSource>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `allCountries should return the list of countries from the database`() {
        runTest {
            every { roomDBDataSource.getAllCountries() } returns flow { emit(testCountries) }
            coEvery { roomDBDataSource.saveCountry(any()) } answers { nothing }
            every { networkApiDataSource.fetchCountries() } returns flow { emit(testCountries) }
            val repository = createRepository()

            val result = repository.allCountries.single()

            assertEquals(testCountries, result)
            verify(exactly = 1) { roomDBDataSource.getAllCountries() }
            verify(exactly = 1) { networkApiDataSource.fetchCountries() }
            for (country in testCountries) {
                coVerify { roomDBDataSource.saveCountry(country) }
            }
        }
    }

    @Test
    fun `getCountriesByName should return the list of countries that match an uppercase search value`() {
        assertCountriesFilteredByName("AR", listOf(testCountries[0], testCountries[1]))
    }

    @Test
    fun `getCountriesByName should return the list of countries that match a lowercase search value`() {
        assertCountriesFilteredByName("ar", listOf(testCountries[0], testCountries[1]))
    }

    @Test
    fun `getCountriesByName should return all countries when search value is empty`() {
        assertCountriesFilteredByName("", testCountries)
    }

    @Test
    fun `getCountriesByName should not return any countries when search value does not match any`() {
        assertCountriesFilteredByName("Argien", emptyList())
    }

    private fun assertCountriesFilteredByName(name: String, expected: List<Country>) {
        runTest {
            every { roomDBDataSource.getAllCountries() } returns flow { emit(testCountries) }

            val repository = createRepository()

            val actualCountries = repository.getCountriesByName(name).single()

            assertEquals(expected, actualCountries)
            verify(exactly = 2) { roomDBDataSource.getAllCountries() }
        }
    }

    private fun createRepository() =
        CountriesRepository(networkApiDataSource, roomDBDataSource, testCoroutineDispatcher)
}