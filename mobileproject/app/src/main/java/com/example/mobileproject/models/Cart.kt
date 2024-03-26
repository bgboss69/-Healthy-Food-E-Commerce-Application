package com.example.mobileproject.models

data class Cart (
    val cartName: String ?= null,
    val cartImage:Int ?= null,
    val cartPrice: Int ?= null,
    var cartQuantity: Int ?= null,
)
