package com.example.hci.component

import com.example.hci.injector.TestInjector.DaggerTestApplicationComponent
import com.example.hci.injector.TestInjector.MockConfigurator
import com.example.hci.injector.TestInjector.TestApplicationComponent
import okhttp3.mockwebserver.MockWebServer

abstract class CommonMockWebServerJUnitTest {
    lateinit var mockWebServer: MockWebServer

    private fun setupWebServer(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    fun setupAndGetDaggerComponent():TestApplicationComponent{
        setupWebServer()
        return DaggerTestApplicationComponent.
            builder().
            mockConfigurator(MockConfigurator(mockWebServer.url("/"))).
            build()
    }
}