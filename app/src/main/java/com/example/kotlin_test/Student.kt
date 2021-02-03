package com.example.kotlin_test

class Student constructor(firstname:String, lastname:String, educationlevel: String, username : String , password : String , photo :String,email:String, changedpw : Boolean)
{
    var Firstname : String
    var Lastname : String
    var Educationlevel : String
    var Username : String
    var Password : String
    var Photo :String
    var Email : String
    var Changedpw : Boolean

    init {

        this.Firstname = firstname
        this.Lastname = lastname
        this.Educationlevel = educationlevel
        this.Username = username
        this.Password = password
        this.Photo = photo
        this.Email = email
        this.Changedpw = changedpw
    }





}