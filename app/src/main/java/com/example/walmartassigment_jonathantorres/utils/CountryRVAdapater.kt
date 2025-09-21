package com.example.walmartassigment_jonathantorres.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassigment_jonathantorres.R
import com.example.walmartassigment_jonathantorres.data.Country

class CountryRVAdapter : RecyclerView.Adapter<CountryRVAdapter.CountryVH>() {

    private val items = mutableListOf<Country>()

    init { setHasStableIds(true) }

    override fun getItemId(position: Int) = items[position].code.hashCode().toLong()

    fun setItems(newItems: List<Country>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryVH(view)
    }

    override fun onBindViewHolder(holder: CountryVH, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class CountryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.nameText)
        private val capitalText: TextView = itemView.findViewById(R.id.capitalText)
        private val codeText: TextView = itemView.findViewById(R.id.codeText)

        fun bind(item: Country) {
            nameText.text = item.name + ", " + (item.region?.takeIf { it.isNotBlank() } ?: "N/A")
            capitalText.text = (item.capital?.takeIf { it.isNotBlank() } ?: "N/A")
            codeText.text    = item.code
        }
    }
}