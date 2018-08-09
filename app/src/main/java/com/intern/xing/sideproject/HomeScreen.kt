package com.intern.xing.sideproject


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.intern.xing.sideproject.ui.main.MainViewModel
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.intern.xing.sideproject.objects.HomePostAdapter
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.android.synthetic.main.home_activity.*



class HomeScreen : Fragment() {

    companion object {
        lateinit var viewModel: MainViewModel

    }
    var navController: NavController?=null
    var adapter:HomePostAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController= Navigation.findNavController(view)
        HomeActivity.stopGoBack=true
        activity!!.bottom_navigation.visibility=View.VISIBLE
        NavigationUI.setupWithNavController(activity!!.bottom_navigation,navController!!)
        viewModel.fireBaseUtils.updateCurrentUser()
        viewModel.fireBaseUtils.updateCurrentTags()

    }

    override fun onStart() {
        super.onStart()
        viewModel.fireBaseUtils.getPost()

        viewModel.getPost().observe(this,
                Observer {
                    if(it!=null) {
                        if (adapter == null) {
                            recycler_home_post.layoutManager = LinearLayoutManager(activity)
                            adapter = HomePostAdapter(context!!, it, navController!!)
                            recycler_home_post.adapter = adapter
                        } else {
                            recycler_home_post.layoutManager = LinearLayoutManager(activity)
                            recycler_home_post.adapter = adapter
                            adapter!!.listOfPost = it
                            adapter!!.notifyDataSetChanged()
                        }
                    }
                })
        home_post_launcher.setOnClickListener {
            navController!!.navigate(R.id.action_homeScreen_to_startPostScreen)
        }
    }



    override fun onStop() {
        super.onStop()
        viewModel.getPost().value=null
        HomeActivity.stopGoBack=false
    }



}
