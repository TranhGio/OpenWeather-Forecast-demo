package com.android.forecast.ui.main.cities

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.forecast.R
import com.android.forecast.arch.extensions.inflate
import com.android.forecast.arch.extensions.onClick
import com.android.forecast.data.model.CityForecast
import com.android.forecast.databinding.ItemCityBinding

class CitiesAdapter(var cities: MutableList<CityForecast>) :
    RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityViewHolder(
        parent.inflate(R.layout.item_city)
    )

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount() = cities.size

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemCityBinding.bind(itemView)

        fun bindView(position: Int) {
            binding.run {
                itemView.run {
                    textViewCityName.text = cities[position].name
                }
                itemCity.onClick {
                    onItemClicked.invoke(position)
                }
            }
        }
    }
}
