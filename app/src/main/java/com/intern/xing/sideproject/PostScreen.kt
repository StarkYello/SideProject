package com.intern.xing.sideproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.intern.xing.sideproject.objects.SolutionAdapter
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_post_screen.*
import kotlinx.android.synthetic.main.home_activity.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_home_screen.*


class PostScreen : Fragment() {
    companion object {
        lateinit var viewModel: MainViewModel
    }
    var adapter: SolutionAdapter?=null

    var navController: NavController?=null
    var bundle:Bundle?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController= Navigation.findNavController(view)
        activity!!.bottom_navigation.visibility=View.GONE

        bundle=arguments!!
        viewModel.getCurrentPost().observe(this,
                Observer {
                            if(it!=null) {
                                if(it.solutions!=null) {
                                    var solutionIDList = arrayListOf<String>()
                                    it.solutions!!.forEach {
                                        solutionIDList.add(it.value.ownerUID!!)
                                    }

                                    bundle!!.putStringArrayList("solutionIDList", solutionIDList)
                                }
                        post_screen_title.text=it.postTitle
                        post_screen_owner.text="By: ${it.ownerName}"
                        post_screen_detail.text=it.postDescription
                        post_screen_timestamp.text= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(it.timestamp!!))
                        if(it.solutions!=null) {
                            if (adapter == null) {
//                                post_screen_recycler.layoutManager = CustomLinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                                post_screen_recycler.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)

                                post_screen_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                                adapter = SolutionAdapter(context!!, ArrayList(it.solutions!!.values), navController!!,it.postUID!!,it)
                                post_screen_recycler.adapter = adapter
                                viewModel.getCurrentPost().value=null
                            } else {
//                                post_screen_recycler.layoutManager = CustomLinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                                post_screen_recycler.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                                post_screen_recycler.adapter = adapter
                                adapter!!.listOfSolutions = ArrayList(it.solutions!!.values)
                                adapter!!.notifyDataSetChanged()
                                viewModel.getCurrentPost().value=null

                            }
                        }
                    }
                })
        solution_answer_btn.setOnClickListener {
            navController!!.navigate(R.id.action_postScreen_to_postAnswerScreen,bundle)
        }
        viewModel.fireBaseUtils.getCurrentPost(bundle!!.getString("postUID"))

    }


    override fun onStop() {
        super.onStop()

        adapter=null
    }

}
