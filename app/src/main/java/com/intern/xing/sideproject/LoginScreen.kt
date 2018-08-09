package com.intern.xing.sideproject


import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_login_screen.*




class LoginScreen : Fragment() {
    companion object {
        lateinit var viewModel: MainViewModel
    }
    var navController: NavController?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login_screen, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController=Navigation.findNavController(view)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        account_login_btn.setOnClickListener {
            if(email_login_box.text.toString()!=""&&password_login_box.text.toString()!=""){
                viewModel.progressBar= ProgressDialog(context)
                viewModel.progressBar!!.setMessage("logging in")
                viewModel.progressBar!!.show()
                viewModel!!.fireBaseUtils.login(email_login_box.text.toString().trim(), password_login_box.text.toString().trim())

            }else{
                login_error.text="INFORMATION MISSING".toUpperCase()
            }
        }
        forgot_password_btn.setOnClickListener {
            if(email_login_box.text.toString()==""){
                login_error.text="EMAIL REQUIRED".toUpperCase()
            }
            else{
                viewModel.progressBar=ProgressDialog(context)
                viewModel.progressBar!!.setMessage("processing")
                viewModel.progressBar!!.show()
                viewModel.fireBaseUtils.resetPassword(email_login_box.text.toString())
            }
        }
        viewModel!!.getUserLoginStatus().observe(this,
                Observer {
                    if(it==true){
                        viewModel.getUserLoginStatus().postValue(null)
                        navController!!.navigate(R.id.homeActivity)
                    }
                    else if(it==false){
                        login_error.text= viewModel.loginErrorCode
                        viewModel.getUserLoginStatus().postValue(null)
                    }
                })
    }


}
