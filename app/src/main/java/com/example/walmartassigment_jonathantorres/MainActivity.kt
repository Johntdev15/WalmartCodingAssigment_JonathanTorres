package com.example.walmartassigment_jonathantorres

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartassigment_jonathantorres.data.ViewsStates
import com.example.walmartassigment_jonathantorres.utils.CountryRVAdapter
import com.example.walmartassigment_jonathantorres.viewmodel.CountriesViewModel

class MainActivity : ComponentActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var errorText: TextView
    private val vm: CountriesViewModel by viewModels()
    private val adapter = CountryRVAdapter()
    private var rvState: Parcelable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recycler_RV)
        errorText = findViewById(R.id.errorText_TV)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        vm.state.observe(this) { state ->
            when (state) {
                is ViewsStates.Loading -> {
                    errorText.visibility = View.GONE
                }
                is ViewsStates.Success -> {
                    errorText.visibility = View.GONE
                    adapter.setItems(state.data)
                    recycler.post {
                        rvState?.let {
                            recycler.layoutManager?.onRestoreInstanceState(it)
                            rvState = null
                        }
                    }
                }
                is ViewsStates.Error -> {
                    errorText.visibility = View.VISIBLE
                    errorText.text = state.message
                }
            }
        }

        vm.loadCountries()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        rvState = recycler.layoutManager?.onSaveInstanceState()
        outState.putParcelable("rv_state", rvState)
    }

    @Suppress("DEPRECATION")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        rvState = savedInstanceState.getParcelable("rv_state")
    }
}