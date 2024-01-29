package com.test.walmart.repository

import com.test.walmart.repository.remote.RemoteSource
import com.test.walmart.repository.remote.model.Country
import com.test.walmart.repository.remote.model.CountryException
import com.test.walmart.repository.remote.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryImpl(private val remoteSource: RemoteSource): Repository {

    override suspend fun fetchCountries(): Result {
        val countryList = mutableListOf<Country>()
        val countryDeferred = CoroutineScope(Dispatchers.IO).async {
            remoteSource.fetchCountries()
        }

        val results = countryDeferred.await()
        if (results.isSuccessful) {
            results.body()?.let {
                countryList.addAll(it)
            }
        }
        if (countryList.isEmpty()) {
            return Result.Error(CountryException("No Countries to display!"))
        }
        return Result.Success(countryList)
    }
}