package com.intern.xing.sideproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : AppCompatActivity() {
    companion object {
        var stopGoBack=false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navController = findNavController(R.id.home_host_fragment)



        NavigationUI.setupWithNavController(bottom_navigation,navController!!)


//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, MenuScreen.newInstance())
////                    .commitNow()
////        }
    }

    override fun onSupportNavigateUp() =
            findNavController(R.id.home_host_fragment).navigateUp()

    override fun onBackPressed() {
        if(stopGoBack){

        }
        else{
            super.onBackPressed()
        }
    }

}