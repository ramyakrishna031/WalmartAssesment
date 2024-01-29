package com.test.walmart.repository.remote

import com.test.walmart.repository.remote.api.CountryService
import com.test.walmart.repository.remote.model.Country
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteSourceImpl(private val countryService: CountryService): RemoteSource {

    companion object {
        fun create(baseURL: String): RemoteSource {
            // Initialization of Retrofit instance
            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(CountryService::class.java)
            return RemoteSourceImpl(service)
        }
    }

    override suspend fun fetchCountries(): Response<List<Country>> =
        countryService.fetchCountries()
}