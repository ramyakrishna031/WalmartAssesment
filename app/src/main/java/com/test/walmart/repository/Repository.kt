package com.test.walmart.repository

import com.test.walmart.repository.remote.model.Result

interface Repository {

    suspend fun fetchCountries(): Result
}