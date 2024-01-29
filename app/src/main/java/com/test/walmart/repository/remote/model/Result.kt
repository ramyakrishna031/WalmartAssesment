package com.test.walmart.repository.remote.model

/**
 * Sealed class to handle Success and failure results
 */
sealed class Result {

    data class Success(val countryList: List<Country>?): Result()
    data class Error(val exception: CountryException): Result()
}

data class CountryException(
    val errorMessage: String?
) : Exception()
