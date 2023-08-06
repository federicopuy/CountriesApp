package com.example.countriesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countriesapp.ui.countries.SearchCountriesScreen
import com.example.countriesapp.ui.countries.SearchCountriesUiState
import com.example.countriesapp.ui.countries.SearchCountriesViewModel

@Composable
fun CountriesApp(searchCountriesViewModel: SearchCountriesViewModel = viewModel()) {

    val searchedCountriesUiState by searchCountriesViewModel.searchedCountriesUiState.collectAsStateWithLifecycle(
        initialValue = SearchCountriesUiState()
    )

    SearchCountriesScreen(
        state = searchedCountriesUiState,
        onInputText = {
            searchCountriesViewModel.userInputtedText(it)
        },
        onItemClicked = { country ->
            searchCountriesViewModel.countryClicked(country)
        },
        onDismissDialog = {
            searchCountriesViewModel.countryDialogDismissed()
        }
    )
}