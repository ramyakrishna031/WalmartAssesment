package com.test.walmart.repository.remote

import com.test.walmart.repository.remote.model.Country
import retrofit2.Response

interface RemoteSource {

    suspend fun fetchCountries(): Response<List<Country>>
}