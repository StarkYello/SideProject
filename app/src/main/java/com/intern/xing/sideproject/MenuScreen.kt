package com.intern.xing.sideproject

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_menu_screen.*

class MenuScreen : Fragment() {

    companion object {
        fun newInstance() = MenuScreen()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        goToAccountBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toAccount))
//        goToSettingBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toSetting))
        create_account_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_menuScreen_to_createAccount))
        login_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_menuScreen_to_loginScreen))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
