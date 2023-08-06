package com.example.countriesapp.data

import com.example.countriesapp.data.persistence.CountryDao
import com.example.countriesapp.data.persistence.RoomDBDataSource
import com.example.countriesapp.testCountries
import com.example.countriesapp.testCountriesEntities
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RoomDBDataSourceTest {

    private val countryDao = mockk<CountryDao>()

    private val dataSource = RoomDBDataSource(countryDao)

    @Test
    fun `saveCountry should convert to countryDto and save the country to the database`() {
        runTest {
            val country = testCountries[0]
            coEvery { countryDao.insert(any()) } answers { nothing }

            dataSource.saveCountry(country)

            coVerify { countryDao.insert(testCountriesEntities[0]) }
        }
    }

    @Test
    fun `getAllCountries should return the list of countries from the database`() {
        runTest {
            every { countryDao.getAllCountries() } returns flow { emit(testCountriesEntities) }

            val actualCountries = dataSource.getAllCountries().single()

            assertEquals(testCountries, actualCountries)
        }
    }
}