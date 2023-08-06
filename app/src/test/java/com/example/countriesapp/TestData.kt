package com.example.countriesapp

import com.example.countriesapp.data.model.Country
import com.example.countriesapp.data.network.CountryDto
import com.example.countriesapp.data.network.FlagsDto
import com.example.countriesapp.data.network.NameDto
import com.example.countriesapp.data.persistence.CountryEntity

val testCountries = listOf(
    Country(
        "Argentina",
        "www.argentinaFlag.com",
        "descriptionArgentina"
    ),
    Country(
        "Armenia",
        "www.ArmeniaFlag.com",
        "descriptionArmenia"
    ),
    Country(
        "Brazil",
        "www.BrazilFlag.com",
        "descriptionBrazil"
    ),
    Country("Chile", "www.ChileFlag.com", "descriptionChile")
)

val testCountriesNetworkDto = listOf(
    CountryDto(NameDto("Argentina"), FlagsDto("www.argentinaFlag.com", "descriptionArgentina")),
    CountryDto(NameDto("Armenia"), FlagsDto("www.ArmeniaFlag.com", "descriptionArmenia")),
    CountryDto(NameDto("Brazil"), FlagsDto("www.BrazilFlag.com", "descriptionBrazil")),
    CountryDto(NameDto("Chile"), FlagsDto("www.ChileFlag.com", "descriptionChile")),
)

val testCountriesEntities = listOf(
    CountryEntity("Argentina", "www.argentinaFlag.com", "descriptionArgentina"),
    CountryEntity("Armenia", "www.ArmeniaFlag.com", "descriptionArmenia"),
    CountryEntity("Brazil", "www.BrazilFlag.com", "descriptionBrazil"),
    CountryEntity("Chile", "www.ChileFlag.com", "descriptionChile")
)