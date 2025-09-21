package com.example.walmartassigment_jonathantorres.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartassigment_jonathantorres.data.Country
import com.example.walmartassigment_jonathantorres.data.Repositories
import com.example.walmartassigment_jonathantorres.data.ViewsStates
import kotlinx.coroutines.launch


class CountriesViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val repo = Repositories()
    val state = MutableLiveData<ViewsStates>()
    val stateToObserve: LiveData<ViewsStates> = state

    private val allCountries = MutableLiveData<List<Country>>(emptyList())
    val query: MutableLiveData<String> =
        savedStateHandle.getLiveData("key_query", "")

    val filteredCountries = MediatorLiveData<List<Country>>().apply {
        fun compute() {
            val toLowercase = query.value.orEmpty().trim().lowercase()
            val allItems = allCountries.value.orEmpty()
            value = if (toLowercase.isEmpty()) allItems else allItems.filter { it.name.lowercase().contains(toLowercase) }
        }
        addSource(allCountries) { compute() }
        addSource(query) { compute() }
    }

    fun setQuery(text: String) {
        savedStateHandle["key_query"] = text
        query.value = text
    }

    fun loadCountries() {
        if(!allCountries.value.isNullOrEmpty()){
            return
        }
        state.value = ViewsStates.Loading
        viewModelScope.launch {
            val result: Result<List<Country>> = repo.fetchCountries()
            result
                .onSuccess { list ->
                    allCountries.postValue(list)
                    state.postValue(ViewsStates.Success(list))
                }
                .onFailure { t ->
                    state.postValue(ViewsStates.Error(t.message ?: "Unknown error"))
                }
        }
    }
}