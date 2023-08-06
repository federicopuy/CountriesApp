package com.example.countriesapp

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.example.countriesapp.data.model.Country
import com.example.countriesapp.ui.countries.SearchCountriesScreen
import com.example.countriesapp.ui.countries.SearchCountriesUiState
import org.junit.Rule
import org.junit.Test

class SearchCountriesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textFieldIsRendered() {
        composeTestRule.setContent {
            SearchCountriesScreen(
                state = SearchCountriesUiState(
                    countries = listOf(),
                    selectedCountry = null
                ),
                onInputText = {},
                onItemClicked = {},
                onDismissDialog = {}
            )
        }

        composeTestRule.onNodeWithText("Enter Country Name").assertExists()
    }

    @Test
    fun listOfCountriesIsRendered() {
        val countries = listOf(
            Country(name = "Argentina"),
            Country(name = "Brazil"),
            Country(name = "Chile")
        )
        composeTestRule.setContent {
            SearchCountriesScreen(
                state = SearchCountriesUiState(
                    countries = countries,
                    selectedCountry = null
                ),
                onInputText = {},
                onItemClicked = {},
                onDismissDialog = {}
            )
        }

        countries.forEach { country ->
            composeTestRule.onNodeWithText(country.name).assertExists()
        }
    }

    @Test
    fun selectedCountryDialogIsRendered() {
        val countries = listOf(
            Country(name = "Argentina", "image", "argentinaDescription"),
            Country(name = "Brazil"),
            Country(name = "Chile")
        )
        composeTestRule.setContent {
            SearchCountriesScreen(
                state = SearchCountriesUiState(
                    countries = countries,
                    selectedCountry = countries[0]
                ),
                onInputText = {},
                onItemClicked = {},
                onDismissDialog = {}
            )
        }

        composeTestRule.onAllNodesWithText(countries[0].name).assertCountEquals(2)
        composeTestRule.onNodeWithText(countries[0].flagDescription!!).assertExists()
    }
}
