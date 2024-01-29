package com.test.walmart.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.walmart.R
import com.test.walmart.provider.CountryProvider
import com.test.walmart.repository.Repository
import com.test.walmart.repository.RepositoryImpl
import com.test.walmart.repository.remote.RemoteSourceImpl
import com.test.walmart.repository.remote.model.Constant
import com.test.walmart.repository.remote.model.Result
import com.test.walmart.ui.adapter.CountryAdapter
import com.test.walmart.viewmodel.CountryViewModel
import com.test.walmart.viewmodel.CountryViewModelFactory
import java.security.Provider

class MainActivity : AppCompatActivity(), Observer<Result> {

    private lateinit var viewModelFactory: CountryViewModelFactory
    private lateinit var viewModel: CountryViewModel
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var errorView: TextView
    private lateinit var repository: Repository
    private lateinit var loadingSpinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.country_list)
        errorView = findViewById(R.id.error_layout)
        loadingSpinner = findViewById(R.id.loadingSpinner);
        countryAdapter = CountryAdapter()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = countryAdapter

        // Create an instance of the repository and pass the data source
        repository = CountryProvider.getRepository()

        viewModelFactory = CountryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CountryViewModel::class.java)
        viewModel.fetchCountries().observe(this, this)
        showLoadingSpinner()
    }

    override fun onChanged(value: Result) {
        hideLoadingSpinner()
        value.apply {
            when (this) {
                is Result.Success -> {
                    val responseList = this.countryList
                    if (responseList.isNullOrEmpty()) {
                        errorView.text = getString(R.string.no_results)
                    } else {
                        countryAdapter.setData(responseList)
                    }
                }

                is Result.Error -> {
                    errorView.text = this.exception.errorMessage
                }
            }
        }
    }

    private fun showLoadingSpinner() {
        loadingSpinner.visibility = View.VISIBLE
    }

    private fun hideLoadingSpinner() {
        loadingSpinner.visibility = View.GONE
    }
}