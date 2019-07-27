package com.example.hci.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hci.domain.GetDataUseCase
import com.example.hci.interactor.model.ApiResponse
import kotlinx.coroutines.launch

data class State(val state:String, val message:String?)

class MainViewModel(private val getDataUseCase: GetDataUseCase): ViewModel(){
    var apiResponse = MutableLiveData<ApiResponse>()
    var state = MutableLiveData<State>()

    fun getApiData(){
        viewModelScope.launch {
            try {
                val apiData = getDataUseCase.getApiData()
                apiResponse.postValue(apiData)
                state.postValue(State("success",null))
            } catch (error: Exception) {
                error.printStackTrace()
                state.postValue(State("error",error.message ?: "Error occured"))
            }
        }
    }
}