package com.example.countriesapp.ui.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapp.data.CountriesRepository
import com.example.countriesapp.data.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

private val FLOW_SHARING_STOP_MILLIS = 5.seconds.inWholeMilliseconds

data class SearchCountriesUiState(
    val countries: List<Country> = emptyList(),
    val searchText: String? = null,
    val selectedCountry: Country? = null,
)

@HiltViewModel
class SearchCountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository,
) :
    ViewModel() {

    private val inputtedText = MutableStateFlow<String?>(null)
    private val selectedCountry = MutableStateFlow<Country?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _searchedCountriesUiState: Flow<SearchCountriesUiState> =
        inputtedText
            .flatMapLatest { text ->
                if (text == null) {
                    countriesRepository.allCountries.catch { emit(emptyList()) }
                } else {
                    countriesRepository.getCountriesByName(text)
                }
            }
            .combine(selectedCountry) { countryList, selectedCountry ->
                SearchCountriesUiState(
                    countries = countryList,
                    searchText = inputtedText.value,
                    selectedCountry = selectedCountry
                )
            }

    val searchedCountriesUiState: StateFlow<SearchCountriesUiState> =
        _searchedCountriesUiState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(FLOW_SHARING_STOP_MILLIS),
            initialValue = SearchCountriesUiState()
        )

    fun userInputtedText(text: String) {
        if (text.isEmpty()) {
            inputtedText.update { null }
            return
        }
        inputtedText.update { text }
    }

    fun countryClicked(country: Country) {
        selectedCountry.update { country }
    }

    fun countryDialogDismissed() {
        selectedCountry.update { null }
    }
}