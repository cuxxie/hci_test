package com.example.hci.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.hci.R
import com.example.hci.injector.DaggerApplicationComponent
import com.example.hci.interactor.model.ApiResponse
import com.example.hci.view.model.Mapper
import com.example.hci.view.viewmodel.MainViewModel
import com.example.hci.view.viewmodel.State
import com.example.hci.view.component.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel:MainViewModel

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = DaggerApplicationComponent
            .builder()
            .build().getMainViewModel()
        mainViewModel.apiResponse.observe(this, Observer { getApiResult(it) })
        mainViewModel.state.observe(this, Observer { getState(it) })
        val layoutManager = LinearLayoutManager(this@MainActivity)
        mainAdapter = MainAdapter()
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = mainAdapter
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getApiData()
    }

    private fun getState(state:State){
        if(state.state == "error"){
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(state.message)
                .show()
        }
    }

    private fun getApiResult(apiResponse: ApiResponse){
        val data = Mapper.mapToItemDataModel(apiResponse)
        mainAdapter.setItems(data)
    }
}
