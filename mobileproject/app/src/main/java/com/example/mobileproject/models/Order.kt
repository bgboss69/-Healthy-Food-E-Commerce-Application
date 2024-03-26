package com.example.mobileproject.models

data class Order (
    val orderSummary: String ?= null,
    val total:Int ?= null,
)