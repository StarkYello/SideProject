package com.intern.xing.sideproject


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ServerValue
import com.intern.xing.sideproject.objects.Reply
import com.intern.xing.sideproject.objects.ReplyAdapter
import com.intern.xing.sideproject.objects.SolutionAdapter
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_post_screen.*
import kotlinx.android.synthetic.main.fragment_reply_popup.*
import kotlinx.android.synthetic.main.home_activity.*
import java.text.SimpleDateFormat
import java.util.*


class ReplyPopup : Fragment() {
    companion object {
        lateinit var viewModel: MainViewModel
    }
    var adapter: ReplyAdapter?=null
    var bundle:Bundle?=null
    var navController: NavController?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reply_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController= Navigation.findNavController(view)
        activity!!.bottom_navigation.visibility=View.GONE


//        val dm = DisplayMetrics()
//        activity!!.windowManager.defaultDisplay.getMetrics(dm)
//        activity!!.window.setLayout(dm.widthPixels*0.8.toInt(),dm.heightPixels*0.8.toInt())

        bundle=arguments!!

        viewModel.getReplies().observe(this,
                androidx.lifecycle.Observer {
                    if(it!=null){
                        if(adapter==null){
                            reply_popup_recycler.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                            reply_popup_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                            adapter = ReplyAdapter(context!!, it, navController!!)
                            reply_popup_recycler.adapter=adapter
                            viewModel.getReplies().value=null
                        }
                        else{
                            reply_popup_recycler.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                            reply_popup_recycler.adapter = adapter
                            adapter!!.listOfReplies=it
                            adapter!!.notifyDataSetChanged()
                            viewModel.getReplies().value=null

                        }
                    }


                })
        viewModel.fireBaseUtils.updateCurrentUser()
        viewModel.setDisabled(reply_btn,false)
        reply_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(reply_input.text.toString()!=""){
                    viewModel.setDisabled(reply_btn,true)
                }else{
                    viewModel.setDisabled(reply_btn,false)
                }
            }

        })

        reply_btn.setOnClickListener {
            if(reply_input.text.toString()!="") {
                val input = reply_input.text.toString().trim()
                if (!input.isEmpty()) {
                    viewModel.fireBaseUtils.postNewReply(bundle!!.getString("postUID"), bundle!!.getString("solutionUID"), Reply(username = viewModel.currentUser!!.username, userUID = viewModel.currentUser!!.UID, replyText = input))
                    reply_input.setText("")
                }
            }

        }

        viewModel.fireBaseUtils.getCurrentReplies(bundle!!.getString("postUID"),bundle!!.getString("solutionUID"))


    }

}
