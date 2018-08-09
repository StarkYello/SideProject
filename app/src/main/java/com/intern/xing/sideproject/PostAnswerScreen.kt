package com.intern.xing.sideproject


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.intern.xing.sideproject.objects.Solution
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_post_answer_screen.*
import kotlinx.android.synthetic.main.fragment_reply_popup.*


class PostAnswerScreen : Fragment() {

    companion object {
        lateinit var viewModel: MainViewModel
    }
    var navController: NavController?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_answer_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController= Navigation.findNavController(view)
        viewModel.setDisabled(solution_submit_btn,false)
        solution_input.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(solution_input.text.toString()!=""){
                    viewModel.setDisabled(solution_submit_btn,true)
                }else{
                    viewModel.setDisabled(solution_submit_btn,false)
                }
            }
        })


        val postUID=arguments!!.getString("postUID")
        solution_submit_btn.setOnClickListener {
            if(!solution_input.text.isEmpty()){
//                viewModel.fireBaseUtils.updateCurrentUser()
                if(arguments!!.containsKey("solutionIDList")){
                    var solutionIDList:ArrayList<String> =arguments!!.getStringArrayList("solutionIDList")
                    if(!solutionIDList.contains(viewModel.fireBaseUtils.mAuth.currentUser!!.uid)){
                        val proposedSolution = Solution(score=0,ownerName = viewModel.currentUser!!.username,solution=solution_input.text.toString().trim(),ownerUID = viewModel.fireBaseUtils.mAuth.currentUser!!.uid)
                        viewModel.fireBaseUtils.addSolution(postUID,proposedSolution)

                    }
                    else{
                        viewModel.fireBaseUtils.updateSolution(postUID,viewModel.fireBaseUtils.mAuth.currentUser!!.uid,solution_input.text.toString().trim())
                    }
                }else{
                    val proposedSolution = Solution(score=0,ownerName = viewModel.currentUser!!.username,solution=solution_input.text.toString().trim(),ownerUID = viewModel.fireBaseUtils.mAuth.currentUser!!.uid)
                    viewModel.fireBaseUtils.addSolution(postUID,proposedSolution)
                }

                solution_input.setText("")
                navController!!.popBackStack()
            }
        }

    }



}
