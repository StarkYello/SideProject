package com.intern.xing.sideproject.objects

import java.util.*

data class DataSheet(var posts:HashMap<String,Post>,var tags:List<String>,var users:HashMap<String,User>)
data class User(var totalNumPosts:Int=0, var totalNumSolution:Int=0,var commentedPosts:List<String>?=null,var raisedPosts:List<String>?=null,var tags:List<String>?=null,var totalScore: Int=0,var email:String?=null, var username:String?=null, var UID: String?=null)
data class Post(var acceptedSolution:String?=null, var ownerName:String?=null, var timestamp:Long?=null,var ownerUID:String?=null,var postDescription:String?=null, var postTags:List<String>?=null, var postTitle:String?=null,var postUID:String?=null,var solutions:HashMap<String,Solution>?=null,var popularity:Int?=null)
data class Solution(var scorers:HashMap<String,Int>?=null,var score:Int?=null, var solution:String?=null,var ownerName: String?=null, var ownerUID:String?=null,var timestamp:Long?=null, var replies:HashMap<String,Reply>?=null)

data class Reply(var timestamp:Long?=null,var username: String?=null, var userUID:String?=null, var replyText:String?=null) {

    fun compareTo(o: Reply): Int {
        return if (timestamp == null || o.timestamp == null) 0 else Date(timestamp!!).compareTo(Date(o.timestamp!!))
    }
}