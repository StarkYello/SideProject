package com.intern.xing.sideproject


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController

import androidx.navigation.fragment.findNavController
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_create_account.*



class CreateAccount : Fragment() {

    var viewModel: MainViewModel?=null
    var navController:NavController?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController=Navigation.findNavController(view)
        viewModel!!.getLoginErrorMessage().observe(this,
                Observer {
                    errorMessage.text=it
                })

        create_account_finish_btn.setOnClickListener {
            //                viewModel.fireBaseUtils.createUser(username=username_box.text.toString(),email=email_box.text.toString(),password = password_box.text.toString())
            if(username_box.text.toString()!=""&&email_box.text.toString()!=""&&password_box.text.toString()!=""){
                var bundle:Bundle=bundleOf(
                        "username" to username_box.text.toString().trim(),
                        "email" to email_box.text.toString().trim(),
                        "password" to password_box.text.toString().trim())
                navController!!.navigate(R.id.action_createAccount_to_tagScreen,bundle)
            }else{
                viewModel!!.getLoginErrorMessage().value="INFORMATION MISSING"
            }
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
