package com.test.walmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.test.walmart.repository.Repository
import com.test.walmart.repository.remote.model.Result

class CountryViewModel(private val repository: Repository): ViewModel() {

    fun fetchCountries(): LiveData<Result> = liveData {
        val data = repository.fetchCountries()
        emit(data)
    }
}

class CountryViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}