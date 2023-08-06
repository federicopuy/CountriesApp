package com.example.countriesapp.ui

import com.example.countriesapp.MainDispatcherRule
import com.example.countriesapp.data.CountriesRepository
import com.example.countriesapp.testCountries
import com.example.countriesapp.ui.countries.SearchCountriesUiState
import com.example.countriesapp.ui.countries.SearchCountriesViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCountriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val countriesRepository = mockk<CountriesRepository>()

    @Test
    fun `initial state contains default state values`() = runTest {
        every { countriesRepository.allCountries } returns emptyFlow()

        assertSearchCountriesUiState(SearchCountriesUiState())
    }

    @Test
    fun `countries are fetched from repository and ui state is updated`() = runTest {
        every { countriesRepository.allCountries } returns flow { emit(testCountries) }

        assertSearchCountriesUiState(SearchCountriesUiState(countries = testCountries))
    }

    @Test
    fun `when error fetching countries, ui state is updated with empty list of countries`() = runTest {
        every { countriesRepository.allCountries } returns flow { throw Exception() }

        assertSearchCountriesUiState(SearchCountriesUiState(countries = emptyList()))
    }

    @Test
    fun `when user inputs text, the searched countries ui state should be updated`() {
        runTest {
            val testInputText = "Ar"
            val expectedCountries = listOf(testCountries[0], testCountries[1])
            every { countriesRepository.allCountries } returns flow { emit(testCountries) }
            every { countriesRepository.getCountriesByName(testInputText) } returns flow {
                emit(
                    expectedCountries
                )
            }

            val expected =
                SearchCountriesUiState(countries = expectedCountries, searchText = testInputText)

            assertSearchCountriesUiState(expected) { viewModel ->
                viewModel.userInputtedText(
                    testInputText
                )
            }
            coVerify { countriesRepository.getCountriesByName(testInputText) }
        }
    }

    @Test
    fun `when user inputs empty text, the searched countries ui state should be updated`() {
        runTest {
            val testInputText = ""
            val expectedCountries = listOf(testCountries[0], testCountries[1])
            every { countriesRepository.allCountries } returns flow { emit(testCountries) }
            every { countriesRepository.getCountriesByName(testInputText) } returns flow {
                emit(
                    expectedCountries
                )
            }

            val expected = SearchCountriesUiState(countries = testCountries, searchText = null)

            assertSearchCountriesUiState(expected) { viewModel ->
                viewModel.userInputtedText(
                    testInputText
                )
            }
        }
    }

    @Test
    fun `when user clicks on a country, the selected country ui state should be updated`() {
        runTest {
            every { countriesRepository.allCountries } returns flow { emit(testCountries) }

            val expected = SearchCountriesUiState(
                countries = testCountries,
                searchText = null,
                selectedCountry = testCountries[0]
            )

            assertSearchCountriesUiState(expected) { viewModel ->
                viewModel.countryClicked(
                    testCountries[0]
                )
            }
        }
    }

    @Test
    fun `when user dismisses the country dialog, the selected country ui state should be updated`() {
        runTest {
            every { countriesRepository.allCountries } returns flow { emit(testCountries) }

            val expected = SearchCountriesUiState(
                countries = testCountries,
                searchText = null,
                selectedCountry = null
            )

            assertSearchCountriesUiState(expected) { viewModel ->
                viewModel.countryDialogDismissed()
            }
        }
    }

    private fun TestScope.assertSearchCountriesUiState(
        expectedState: SearchCountriesUiState,
        foo: (SearchCountriesViewModel) -> Unit = {},
    ) {

        val viewModel = createViewModel()

        var lastCollected = SearchCountriesUiState()
        val job = launch {
            viewModel.searchedCountriesUiState.collectLatest {
                lastCollected = it
            }
        }
        advanceUntilIdle()
        foo(viewModel)
        advanceUntilIdle()

        coVerify { countriesRepository.allCountries }
        assertEquals(expectedState, lastCollected)

        job.cancel()
    }

    private fun createViewModel() = SearchCountriesViewModel(countriesRepository)
}






