package com.example.walmartassigment_jonathantorres.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartassigment_jonathantorres.data.Repositories
import com.example.walmartassigment_jonathantorres.data.ViewsStates
import kotlinx.coroutines.launch


class CountriesViewModel(
    private val repo: Repositories = Repositories()
) : ViewModel() {

    private val _state = MutableLiveData<ViewsStates>()
    val state: LiveData<ViewsStates> = _state

    fun loadCountries() {
        _state.value = ViewsStates.Loading
        viewModelScope.launch {
            val result = repo.fetchCountries()
            _state.value = result.fold(
                onSuccess = { ViewsStates.Success(it) },
                onFailure = { ViewsStates.Error(it.localizedMessage ?: "Unknown error") }
            )
        }
    }
}