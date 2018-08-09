package com.intern.xing.sideproject


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.intern.xing.sideproject.objects.Post
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_start_post_screen.*
import kotlinx.android.synthetic.main.home_activity.*
import org.w3c.dom.Text


class StartPostScreen : Fragment() {
    companion object {
        lateinit var viewModel: MainViewModel

    }
    var navController: NavController?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_post_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController= Navigation.findNavController(view)
        activity!!.bottom_navigation.visibility=View.GONE
        viewModel.fireBaseUtils.updateCurrentTags()



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setDisabled(post_submit_btn,false)
        var adapter = ArrayAdapter<String>(context,R.layout.autocomplete_text, viewModel.currentTags)
        post_tag_input.setAdapter(adapter)
        post_tag_input.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        title_input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(title_input.text.toString()!=""&&description_input.text.toString()!=""&&post_tag_input.text.toString()!=""){
                    viewModel.setDisabled(post_submit_btn,true)
                }else{
                    viewModel.setDisabled(post_submit_btn,false)
                }
            }

        })
        description_input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(title_input.text.toString()!=""&&description_input.text.toString()!=""&&post_tag_input.text.toString()!=""){
                    viewModel.setDisabled(post_submit_btn,true)
                }else{
                    viewModel.setDisabled(post_submit_btn,false)
                }
            }
        })
        post_tag_input.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(title_input.text.toString()!=""&&description_input.text.toString()!=""&&post_tag_input.text.toString()!=""){
                    viewModel.setDisabled(post_submit_btn,true)
                }else{
                    viewModel.setDisabled(post_submit_btn,false)
                }
            }
        })
        post_submit_btn.setOnClickListener {
            if(title_input.text.toString()!=""&&description_input.text.toString()!=""&&post_tag_input.text.toString()!="") {
                var listOfInputTag = post_tag_input.text.split(",").toMutableList()
                var newInputTags = mutableListOf<String>()
                listOfInputTag.forEach {
                    var newString = it.replace("\\s+".toRegex(), " ").trim()
                    if (newString != "") {
                        newInputTags.add(newString)
                    }
                }
                var post = Post(ownerName = viewModel.currentUser!!.username, ownerUID = viewModel.currentUser!!.UID, postDescription = description_input.text.toString(), postTags = newInputTags, postTitle = title_input.text.toString(), popularity = 0)
                viewModel.fireBaseUtils.addPost(post)

                navController!!.popBackStack()
            }
        }
    }





}
