package com.example.walmartassigment_jonathantorres.data

 sealed class ViewsStates {
     object Loading : ViewsStates()
     data class Success(val data: List<Country>) : ViewsStates()
     data class Error(val message: String) : ViewsStates()
}