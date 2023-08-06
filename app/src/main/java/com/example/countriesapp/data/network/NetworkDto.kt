package com.example.countriesapp.data.network

import com.example.countriesapp.data.model.Country
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(val name: NameDto, val flags: FlagsDto)

fun CountryDto.toCountry(): Country {
    return Country(name = name.common, flagImage = flags.png, flagDescription = flags.alt)
}

@Serializable
data class NameDto(val common: String)

@Serializable
data class FlagsDto(val png: String, val alt: String)

