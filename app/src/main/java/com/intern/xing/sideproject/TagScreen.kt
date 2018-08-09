package com.intern.xing.sideproject


import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import androidx.navigation.Navigation
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_tag_screen.*
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import com.intern.xing.sideproject.objects.ExpandableHeightGridView
import com.intern.xing.sideproject.objects.GridAdapter


class TagScreen : Fragment() {
    companion object {
        lateinit var viewModel: MainViewModel
        fun populateTag(list:MutableList<String>,context: Context,gridLayout: ExpandableHeightGridView,continue_btn:ImageButton) {

            val adapter=GridAdapter(context=context,list=list,button=continue_btn)
            gridLayout.adapter=adapter
            gridLayout.isExpanded=true

        }

    }
    var bundle:Bundle?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        continue_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_tagScreen_to_homeScreen))
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        retrieveTag()
        viewModel.activity=activity
        continue_btn.setOnClickListener {
            viewModel.progressBar=ProgressDialog(context)
            viewModel.progressBar!!.setMessage("logging in")
            viewModel.progressBar!!.show()

            var selectedTags= mutableListOf<String>()
            tag_grid.forEach {
                if(it.isActivated){
                selectedTags!!.add(it.findViewById<TextView>(R.id.tag_text).text.toString())}
            }
            bundle=arguments!!
            bundle!!.putCharSequenceArrayList("tags",ArrayList(selectedTags))
            viewModel.fireBaseUtils.createUser(
                    tags=bundle!!.get("tags") as List<String>,
                    email= bundle!!.getString("email"),
                    username = bundle!!.getString("username"),
                    password = bundle!!.getString("password"))

        }
        viewModel.getLoginStatus().observe(this,
                Observer {
                    if(it==true){
                        viewModel.getLoginStatus().postValue(null)
                        Navigation.findNavController(view).navigate(R.id.homeActivity)
                    }
                    else if(it==false){
                        viewModel.getLoginStatus().postValue(null)
                        Navigation.findNavController(view).navigate(R.id.action_tagScreen_to_createAccount)
                    }
                })




    }
    private fun retrieveTag(){
        viewModel.progressBar=ProgressDialog(context)
        viewModel.progressBar!!.setMessage("loading tags")
        viewModel.progressBar!!.show()
        viewModel.fireBaseUtils.getTag(context!!,tag_grid,continue_btn)
        viewModel.progressBar!!.dismiss()
    }




}

