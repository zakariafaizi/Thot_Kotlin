package com.example.kotlin_test.models

class Student constructor(lastname:String,firstname:String,age :Int, educationlevel: String, username : String , password : String , photo :String,email:String, changedpw : Boolean)
{
    var Lastname : String
    var Firstname : String
    var Age : Int
    var Educationlevel : String
    var Username : String
    var Password : String
    var Photo :String
    var Email : String
    var Changedpw : Boolean

    init {

        this.Lastname = lastname
        this.Firstname = firstname
        this.Age = age
        this.Educationlevel = educationlevel
        this.Username = username
        this.Password = password
        this.Photo = photo
        this.Email = email
        this.Changedpw = changedpw
    }





}