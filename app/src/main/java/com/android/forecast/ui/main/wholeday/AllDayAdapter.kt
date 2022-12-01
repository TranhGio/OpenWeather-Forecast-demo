package com.android.forecast.ui.main.wholeday

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.forecast.R
import com.android.forecast.arch.extensions.inflate
import com.android.forecast.arch.extensions.onClick
import com.android.forecast.arch.extensions.timeUTCToLocal
import com.android.forecast.data.model.Hourly
import com.android.forecast.databinding.ItemHourBinding

class AllDayAdapter(var hours: MutableList<Hourly>) :
    RecyclerView.Adapter<AllDayAdapter.HourViewHolder>() {

    internal var onItemClicked: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HourViewHolder(
        parent.inflate(R.layout.item_hour)
    )

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount() = hours.size

    inner class HourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemHourBinding.bind(itemView)

        fun bindView(position: Int) {
            binding.run {
                itemView.run {
                    textHour.text = hours[position].dt?.timeUTCToLocal()
                    textTemperature.text = itemView.context.getString(
                        R.string.temperature_c,
                        hours[position].temp.toString()
                    )
                    textHumidity.text = itemView.context.getString(
                        R.string.humidity,
                        hours[position].humidity.toString()
                    )
                }
                itemHour.onClick {
                    onItemClicked.invoke(position)
                }
            }
        }
    }
}
