package com.test.walmart.repository

import com.test.walmart.repository.remote.RemoteSource
import com.test.walmart.repository.remote.model.Country
import com.test.walmart.repository.remote.model.Currency
import com.test.walmart.repository.remote.model.Language
import com.test.walmart.repository.remote.model.Result
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class RepositoryTest {

    private var remoteSource = Mockito.mock(RemoteSource::class.java)
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = RepositoryImpl(remoteSource)
    }

    @Test
    fun `fetch business success`() = runBlocking {
        val currency = Currency(
            "AFN",
            "Afghan afghani",
            "؋"
        )
        val language = Language(
            "ps",
            "Pashto"
        )
        val country = Country(
            "Kabul",
            "AF",
            "https://restcountries.eu/data/afg.svg",
            "Afghanistan",
            "AS",
            currency,
            language
        )
        val responseList = listOf(country)

        Mockito.`when`(remoteSource.fetchCountries())
            .thenReturn(Response.success(responseList))

        val expectedResult: Result = repository.fetchCountries()
        assertTrue(expectedResult is Result.Success)
        val expectedSuccessResult = expectedResult as Result.Success
        assertEquals(expectedSuccessResult.countryList, listOf(country))
    }

    @Test
    fun `fetch meanings error`() = runBlocking {
        Mockito.`when`(remoteSource.fetchCountries())
            .thenReturn(Response.error(500, ResponseBody.create(null, "Server Down")))

        val expectedResult: Result = repository.fetchCountries()
        assertTrue(expectedResult is Result.Error)

        val expectedErrorResult = expectedResult as Result.Error
        assertEquals(expectedErrorResult.exception.errorMessage, "No Countries to display!")
    }
}