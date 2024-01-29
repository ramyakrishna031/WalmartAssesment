package com.test.walmart.provider

import com.test.walmart.repository.Repository
import com.test.walmart.repository.RepositoryImpl
import com.test.walmart.repository.remote.RemoteSource
import com.test.walmart.repository.remote.RemoteSourceImpl
import com.test.walmart.repository.remote.model.Constant

object CountryProvider {

    fun getRemoteSource(): RemoteSource =
        RemoteSourceImpl.create(Constant.BASE_URL)

    fun getRepository(): Repository =
        RepositoryImpl(getRemoteSource())
}