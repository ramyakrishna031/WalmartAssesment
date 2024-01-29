package com.test.walmart.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.walmart.R
import com.test.walmart.repository.remote.model.Country

class CountryAdapter  :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var countryList: List<Country> = emptyList()

    fun setData(countryList: List<Country>) {
        this.countryList = countryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.country_list_layout, parent, false)
        )

    override fun getItemCount(): Int = if (countryList.isEmpty()) 0 else countryList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]

        country.apply {
            holder.nameRegion.text = String.format("%s, %s", this.name, this.region)
            holder.code.text = this.code
            holder.capital.text = this.capital
        }
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameRegion = view.findViewById<TextView>(R.id.name_region)
        val code = view.findViewById<TextView>(R.id.code)
        val capital = view.findViewById<TextView>(R.id.capital)
    }
}