package com.intern.xing.sideproject.util

import com.google.firebase.database.*
import com.intern.xing.sideproject.objects.Post
import com.intern.xing.sideproject.objects.User

class FirebaseUtils{

    private val fireBaseDataBase=FirebaseDatabase.getInstance()
    var rootRef=fireBaseDataBase.reference
    var postRef=rootRef.child("posts")
    var tagsRef=rootRef.child("tags")
    var userRef=rootRef.child("users")
    fun addUser(newUser: User){
        val newUserRef =userRef.child(newUser.uid)
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
                newPost.uniqueID=p0.key
                newPostRef.setValue(newPost)
            }
        }
        )
    }




}
