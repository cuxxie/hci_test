package com.example.hci.interactor.service

import com.example.hci.interactor.model.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("/home")
    fun getApiDataAsync(): Deferred<ApiResponse>
}