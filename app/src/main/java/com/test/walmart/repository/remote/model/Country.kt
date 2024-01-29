package com.test.walmart.repository.remote.model

data class Country(
    val capital: String,
    val code: String,
    val flag: String,
    val name: String,
    val region: String,
    val currency: Currency,
    val language: Language
)

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

data class Language(
    val code: String,
    val name: String
)