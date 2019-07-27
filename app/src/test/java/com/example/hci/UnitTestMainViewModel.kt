package com.example.hci

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hci.component.CommonMockWebServerJUnitTest
import com.example.hci.component.MyMockResponse
import com.example.hci.view.viewmodel.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import org.junit.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class UnitTestMainViewModel: CommonMockWebServerJUnitTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    lateinit var viewModel:MainViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = setupAndGetDaggerComponent().getMainViewModel()
    }

    fun triggerGetApiData(){
        val latch = CountDownLatch(1)
        runBlocking(Dispatchers.Main) {
            launch(Dispatchers.Main) {
                viewModel.getApiData()
            }
        }

        viewModel.state.observeForever {
            latch.countDown()
        }
        latch.await(2, TimeUnit.SECONDS)
    }

    @Test
    fun testSuccess(){
        mockWebServer.enqueue(MockResponse().setBody(MyMockResponse.POSITIVE_RESPONSE_BODY))
        triggerGetApiData()
        val state = viewModel.state.value!!
        Assert.assertEquals(state.state,"success")
    }

    @Test
    fun testFailed(){
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        triggerGetApiData()
        val state = viewModel.state.value!!
        Assert.assertEquals(state.state,"error")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}