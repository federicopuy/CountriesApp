package com.example.countriesapp.data.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.countriesapp.data.model.Country

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val name: String,
    val flagImage: String? = null,
    val flagDescription: String? = null
)

fun CountryEntity.toCountry(): Country {
    return Country(name = name, flagImage = flagImage, flagDescription = flagDescription)
}

fun Country.toCountryEntity(): CountryEntity {
    return CountryEntity(name = name, flagImage = flagImage, flagDescription = flagDescription)
}