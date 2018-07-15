package com.intern.xing.sideproject


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.intern.xing.sideproject.objects.User
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_create_account.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CreateAccount : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        create_account_finish_btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_createAccount_to_tagScreen))

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        var list= ArrayList<String>()
//        list.add("android")
//        list.add("python")
//        list.add("sleep")
//
//
//        var ajksdfhkxcv= User(list, "bala@gmail.com","king the second","ajksdfhkxcv")
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
//        viewModel.fireBaseUtils.addUser(ajksdfhkxcv)
    }

}
