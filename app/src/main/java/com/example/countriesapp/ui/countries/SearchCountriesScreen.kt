package com.example.countriesapp.ui.countries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countriesapp.R
import com.example.countriesapp.data.model.Country
import com.example.countriesapp.ui.components.ImageTextAlertDialog
import com.example.countriesapp.ui.components.RowItem
import com.example.countriesapp.ui.components.SingleLineTextField
import com.example.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun SearchCountriesScreen(
    state: SearchCountriesUiState,
    onInputText: (String) -> Unit,
    onItemClicked: (Country) -> Unit,
    onDismissDialog: () -> Unit,
) {
    state.selectedCountry?.let {
        ImageTextAlertDialog(
            imageUrl = it.flagImage,
            title = it.name,
            text = it.flagDescription,
            onDismissDialog = onDismissDialog
        )
    }

    Column(modifier = Modifier.padding(24.dp)) {
        SingleLineTextField(
            placeholderValue = stringResource(R.string.enter_country_name),
            onInputText = onInputText,
        )
        LazyColumn {
            items(state.countries) {
                RowItem(title = it.name, onItemClicked = { onItemClicked(it) })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchCountriesScreenPreview() {
    CountriesAppTheme {
        SearchCountriesScreen(
            SearchCountriesUiState(
                countries = listOf(
                    Country("Argentina"),
                    Country("Brazil"),
                    Country("Chile"),
                ),
            ),
            onInputText = {},
            onItemClicked = {},
            onDismissDialog = {}
        )
    }
}