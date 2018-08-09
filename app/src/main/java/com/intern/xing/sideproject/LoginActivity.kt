package com.intern.xing.sideproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
//        NavigationUI.setupWithNavController(bottom_navigation_leaderboard,navController!!)


//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, MenuScreen.newInstance())
////                    .commitNow()
////        }
    }

    override fun onSupportNavigateUp() =
            findNavController(R.id.login_host_fragment).navigateUp()



}
