package com.vandan.tripsheep.data.local

data class User(
    val _id:String,
    val name:String,
    val email:String,
    val password:String,
){
    constructor() : this("","", "", "")
}