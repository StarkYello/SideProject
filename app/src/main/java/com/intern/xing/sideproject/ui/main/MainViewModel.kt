package com.intern.xing.sideproject.ui.main

import android.app.ProgressDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intern.xing.sideproject.objects.Post
import com.intern.xing.sideproject.objects.Reply
import com.intern.xing.sideproject.objects.User
import com.intern.xing.sideproject.util.FirebaseUtils
import android.graphics.ColorMatrixColorFilter
import android.graphics.ColorMatrix
import android.widget.ImageButton


class MainViewModel : ViewModel() {
    val fireBaseUtils=FirebaseUtils(this)
    var currentUser: User?=null
    var progressBar: ProgressDialog?=null
    var activity:FragmentActivity?=null
    var loginErrorCode:String?=null
    var currentTags:List<String>?=null

    private val _loginStatus= MutableLiveData<Boolean>()
    fun getLoginStatus() : MutableLiveData<Boolean>{
        return _loginStatus
    }
    private var loginErrorMessage=MutableLiveData<String>()
    fun getLoginErrorMessage() : MutableLiveData<String>{
        return loginErrorMessage
    }

    private val _userLoginStatus= MutableLiveData<Boolean>()
    fun getUserLoginStatus() : MutableLiveData<Boolean>{
        return _userLoginStatus
    }
    private val _getPost= MutableLiveData<List<Post>>()
    fun getPost() : MutableLiveData<List<Post>>{
        return _getPost
    }
    private val _getAllUser= MutableLiveData<List<User>>()
    fun getAllUser() : MutableLiveData<List<User>>{
        return _getAllUser
    }
    private val _Replies= MutableLiveData<List<Reply>>()
    fun getReplies() : MutableLiveData<List<Reply>>{
        return _Replies
    }
    private val _currentPost=MutableLiveData<Post>()
    fun getCurrentPost() : MutableLiveData<Post>{
        return _currentPost
    }
    private val _currentUserScore=MutableLiveData<Int>()
    fun getCurrentUserScore() : MutableLiveData<Int>{
        return _currentUserScore
    }

    fun setDisabled(imageButton: ImageButton, status: Boolean) {
        if(!status){
            val grayScaleMatrix = ColorMatrix()
            grayScaleMatrix.setSaturation(0f)
            val filter = ColorMatrixColorFilter(grayScaleMatrix)
            imageButton.colorFilter = filter
        }else{
            imageButton.colorFilter=null
        }
        imageButton.isActivated = status
    }



}
