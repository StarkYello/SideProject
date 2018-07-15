package com.intern.xing.sideproject.objects

data class DataSheet(var posts:HashMap<String,Post>,var tags:List<String>,var users:HashMap<String,User>)
data class User(var commentedPosts:List<String>,var raisedPosts:List<String>,var tags:List<String>,var pointOfReward: Int,var listOfTags: List<String>, var email:String, var username:String, var uid: String)
data class Post(var owner:String,var postDescription:String, var postTags:List<String>?=null, var postTitle:String,var uniqueID:String?=null,var responses:List<Response>?=null)
data class Response(var ratedBy:List<String>,var rating:Int, var response:String,var user: String)