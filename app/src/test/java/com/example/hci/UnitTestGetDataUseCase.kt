package com.example.hci

import com.example.hci.component.CommonMockWebServerJUnitTest
import com.example.hci.component.MyMockResponse
import com.example.hci.domain.GetDataUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import okhttp3.mockwebserver.MockResponse
import retrofit2.HttpException


class UnitTestGetDataUseCase: CommonMockWebServerJUnitTest() {

    lateinit var useCase:GetDataUseCase

    @Before
    fun setup(){
        useCase = setupAndGetDaggerComponent().getDataUseCase()
    }

    @Test
    fun testGetDataPositive(){
        mockWebServer.enqueue(MockResponse().setBody(MyMockResponse.POSITIVE_RESPONSE_BODY))
        val apiData = runBlocking { useCase.getApiData() }
        Assert.assertNotNull(apiData)
        Assert.assertEquals(apiData.data.size,2)
        Assert.assertNotNull(apiData.data[0])
        Assert.assertEquals(apiData.data[0].section,"products")
        Assert.assertEquals(apiData.data[1].section,"articles")
        Assert.assertEquals(apiData.data[0].items.size,6)
        Assert.assertEquals(apiData.data[1].items.size,4)
    }

    @Test
    fun testGetDataError500(){
        var isError = false
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        try {
            val apiData = runBlocking { useCase.getApiData() }
        }
        catch (e:HttpException){
            isError = true
        }
        Assert.assertEquals(isError,true)
    }

}