package com.example.hci.domain

import com.example.hci.interactor.model.ApiResponse
import com.example.hci.interactor.service.ApiService

class GetDataUseCase(private val service:ApiService) {
    suspend fun getApiData():ApiResponse{
        return service.getApiDataAsync().await()
    }
}