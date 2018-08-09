package com.intern.xing.sideproject.util

import android.content.Context
import android.widget.ImageButton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.intern.xing.sideproject.LoginScreen
import com.intern.xing.sideproject.TagScreen
import com.intern.xing.sideproject.ui.main.MainViewModel
import com.google.firebase.database.DataSnapshot
import com.intern.xing.sideproject.PostAnswerScreen
import com.intern.xing.sideproject.objects.*


class FirebaseUtils(    val viewModel:MainViewModel?=null){

    private val fireBaseDataBase=FirebaseDatabase.getInstance()
    val rootRef=fireBaseDataBase.reference
    val postRef=rootRef.child("posts")
    val tagsRef=rootRef.child("tags")
    val userRef=rootRef.child("users")
    val mAuth:FirebaseAuth=FirebaseAuth.getInstance()

   fun login(email:String, password:String){
            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                task: Task<AuthResult>  ->
                if(task.isSuccessful){
                   LoginScreen.viewModel.getUserLoginStatus().postValue(true)
                   getUser(mAuth.currentUser!!.uid)
                }
                else{
                    try{
                        throw task.exception!!
                    }catch (e : FirebaseAuthException){
                        LoginScreen.viewModel.loginErrorCode=e.errorCode
                    }
                    LoginScreen.viewModel.getUserLoginStatus().postValue(false)
                }
                LoginScreen.viewModel.progressBar!!.dismiss()

            }

    }
    fun logout(){
        mAuth.signOut()
    }

    fun resetPassword(emailAddress:String){
        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener {
            task ->
            if (task.isSuccessful) {
                LoginScreen.viewModel.loginErrorCode="Recovery Email Sent"
                LoginScreen.viewModel.getUserLoginStatus().postValue(false)
                LoginScreen.viewModel.progressBar!!.dismiss()
            } else {
                try {
                    throw task.exception!!
                }catch (e:FirebaseAuthException){
                    LoginScreen.viewModel.loginErrorCode=e.errorCode
                }
                LoginScreen.viewModel.getUserLoginStatus().postValue(false)
                LoginScreen.viewModel.progressBar!!.dismiss()
            }
        }

    }
    fun updateScorers(postUID:String, solutionUID:String, referenceName:String,vote:Int){
        val scorerRef=postRef.child(postUID).child("solutions").child(solutionUID).child("scorers").child(referenceName)
        scorerRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                scorerRef.setValue(vote)
            }

        })
    }
    fun checkForLogin():Boolean{
        val user=mAuth.currentUser
        return if(user!=null){
            true
        }
        else{
            false
        }
    }
    fun createUser(username:String, email:String, password: String, tags: List<String>?=null){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task: Task<AuthResult> ->
            if(task.isSuccessful){
                mAuth!!.signInWithEmailAndPassword(email,password)
                val registeringUser=User(tags=tags,email=email,username = username,UID=mAuth!!.currentUser!!.uid)
                addUser(registeringUser)
                TagScreen.viewModel.currentUser=registeringUser
                TagScreen.viewModel.getLoginStatus().postValue(true)
                TagScreen.viewModel.progressBar!!.dismiss()
            }
            else{
                try {
                    throw task.exception!!
                }catch (e: FirebaseAuthException) {
                    TagScreen.viewModel.getLoginErrorMessage().postValue(e.errorCode)
                }

                TagScreen.viewModel.getLoginStatus().postValue(false)
                TagScreen.viewModel.progressBar!!.dismiss()
            }
        }
    }
    fun addTag(newTag:String){
        tagsRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                var Tags: MutableList<String> = p0.value as MutableList<String>
                Tags.add(newTag)
                tagsRef.setValue(Tags)
            }
        })
    }
    fun getTag(context: Context, gridLayout: ExpandableHeightGridView,continue_btn : ImageButton){
        tagsRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val tags: MutableList<String> = p0.value as MutableList<String>
                TagScreen.populateTag(tags,context,gridLayout,continue_btn)
            }

        })
    }
    fun getPost(){
        postRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val posts: MutableList<Post> = mutableListOf()
                for (snapshot in p0.children) {
                    val post = snapshot.getValue(Post::class.java)
                    posts.add(post!!)
                }
                viewModel!!.getPost().postValue(posts)
            }

        })
    }
    fun getAllUser(){
        userRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val users: MutableList<User> = mutableListOf()
                for (snapshot in p0.children) {
                    val user = snapshot.getValue(User::class.java)
                    users.add(user!!)
                }
                viewModel!!.getAllUser().postValue(users)
            }

        })
    }
    fun adjustUserScore(userUID: String,change:Int){
        val thisUserRef=userRef.child(userUID).child("totalScore")
        thisUserRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    thisUserRef.setValue(p0.value.toString().toInt()+change)
                }else{
                    thisUserRef.setValue(change)
                }
            }
        })
    }
    fun incrementScore(postUID:String, solutionUID:String, newScore:Int){
        val scoreRef=postRef.child(postUID).child("solutions").child(solutionUID).child("score")
        scoreRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                scoreRef.setValue(p0.value.toString().toInt()+newScore)
                adjustUserScore(solutionUID,1)
            }
        })
    }
    fun decrementScore(postUID:String, solutionUID:String, newScore:Int){
        val scoreRef=postRef.child(postUID).child("solutions").child(solutionUID).child("score")
        scoreRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                scoreRef.setValue(p0.value.toString().toInt()-newScore)
                adjustUserScore(solutionUID,-1)
            }

        })
    }
    fun getCurrentPost(uid: String){
        postRef.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    viewModel!!.getCurrentPost().postValue(p0.getValue(Post::class.java))
                }
            }
        })
    }
    fun getCurrentReplies(postUID:String, solutionUID: String){
        postRef.child(postUID).child("solutions").child(solutionUID).child("replies").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val replies: MutableList<Reply> = mutableListOf()
                    for (snapshot in p0.children) {
                        val reply = snapshot.getValue(Reply::class.java)
                        replies.add(reply!!)
                    }
                    viewModel!!.getReplies().postValue(replies.toList())
                }
            }
        })
    }


    fun addUser(newUser: User){
        val newUserRef =userRef.child(newUser.UID!!)
        newUserRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(dataSnapshot: DatabaseError) {
                //this value is never cancelled
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    newUserRef.setValue(newUser)
                }
                else{
                    //user already exist
                }
            }

        })
    }
    fun addPost(newPost: Post){
        val newPostRef=postRef.push()
        newPostRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                newPost.postUID=p0.key!!
                newPostRef.setValue(newPost)
                newPostRef.child("timestamp").setValue(ServerValue.TIMESTAMP)
                adjustUserScore(mAuth.currentUser!!.uid,10)
            }
        }
        )
    }
    fun deleteUser(newUser: User){
        val newUserRef=userRef.child(newUser.UID!!)
        newUserRef.setValue(null)
    }
    fun getUser(uid:String){
        val uidRefByUID = userRef.child(uid)
        var user:User?=null
        uidRefByUID.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    LoginScreen.viewModel.currentUser=p0!!.getValue(User::class.java)
                    LoginScreen.viewModel.progressBar!!.dismiss()
                }
            }
        })
    }

    fun postNewReply(postUID: String, solutionUID: String, reply: Reply) {
        val newReplyRef=postRef.child(postUID).child("solutions").child(solutionUID).child("replies").push()
        newReplyRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                newReplyRef.setValue(reply)
                newReplyRef.child("timestamp").setValue(ServerValue.TIMESTAMP)
                updatePopularity(postUID)

            }

        })
    }

    fun updateCurrentUser() {
        val currentUserRef=userRef.child(mAuth.currentUser!!.uid)
        currentUserRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                viewModel!!.currentUser=p0.getValue(User::class.java)
            }

        })
    }
    fun updateCurrentTags(){
        tagsRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val tags: MutableList<String> = mutableListOf()
                for (snapshot in p0.children) {
                    val tag = snapshot.value.toString()
                    tags.add(tag)
                }
                viewModel!!.currentTags=tags
            }

        })
    }

    fun addSolution(postUID:String, solution: Solution) {
        val currentSolutionRef=postRef.child(postUID).child("solutions").child(solution.ownerUID!!)
        currentSolutionRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(!p0.exists()){
                    updatePopularity(postUID)
                    adjustUserScore(postUID,3)
                }
                currentSolutionRef.setValue(solution)
                currentSolutionRef.child("timestamp").setValue(ServerValue.TIMESTAMP)
            }

        })

    }

    fun updatePopularity(postUID: String) {
        val popularityRef=postRef.child(postUID).child("popularity")
        popularityRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                popularityRef.setValue(p0.value.toString().toInt()+1)
            }

        })

    }

    fun updateSolution(postUID: String, solutionUID: String, newSolutionText: String) {
        val solutionRef=postRef.child(postUID).child("solutions").child(solutionUID).child("solution")
        solutionRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                solutionRef.setValue(newSolutionText)

            }

        })
        val timeStampRef=postRef.child(postUID).child("solutions").child(solutionUID).child("timestamp")
        timeStampRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                timeStampRef.setValue(ServerValue.TIMESTAMP)
            }

        })

    }

    fun getCurrentPoint() {
        val userPointRef=userRef.child(mAuth.currentUser!!.uid).child("totalScore")
        userPointRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                viewModel!!.getCurrentUserScore().value=p0.value.toString().toInt()
            }

        })
    }


}
