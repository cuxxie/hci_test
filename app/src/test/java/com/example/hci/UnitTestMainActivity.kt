package com.example.hci

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.view.activity.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ActivityScenario.launch
import org.junit.Assert

@RunWith(RobolectricTestRunner::class)
class UnitTestMainActivity {

    @Test
    fun testActivity(){
        val scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        var recyclerView:RecyclerView? = null
        scenario.onActivity { activity ->
            recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
            Assert.assertNotNull(recyclerView)
        }
    }
}