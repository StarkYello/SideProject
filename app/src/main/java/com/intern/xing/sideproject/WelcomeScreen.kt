package com.intern.xing.sideproject


import android.os.Bundle
//import android.app.Fragment
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.login_activity.*
import java.lang.Thread.sleep
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.firebase.database.ServerValue
import com.intern.xing.sideproject.R.id.action_welcomeScreen_to_menuScreen
import com.intern.xing.sideproject.objects.Post


class WelcomeScreen : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var mDelayHandler:Handler?=null

    internal val delay: Runnable = Runnable {
            if(viewModel.fireBaseUtils.checkForLogin()){
                findNavController(this).navigate(R.id.homeActivity)
            }
            else {
                findNavController(this).navigate(action_welcomeScreen_to_menuScreen)
            }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mDelayHandler=Handler()
        mDelayHandler!!.postDelayed(delay,1000)
//        viewModel.fireBaseUtils.addPost(Post(ownerName = "lalala",timeStamp = ServerValue.TIMESTAMP,ownerUid = "123456677",postDescription = "halalal",postTags = listOf("java","android"),postTitle = "question"))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        Thread {
//            kotlin.run {
//                sleep(10000)
//                findNavController(this).navigate(action_welcomeScreen_to_menuScreen)
//            }
//        }.run()
    }


}
