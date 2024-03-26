package com.example.mobileproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class testActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        val item = listOf("Burger", "sandwich","momo","item")
//        val price = listOf(10,20,30,40)
//        val image = listOf(R.drawable.appleorangecarrot,R.drawable.avogado_hotdog,R.drawable.crab_willow_sandwich,R.drawable.cheese_floss_sandwich)
//
//        val adapter = ProductAdapter(item,price,image)
//        val recyclerView = findViewById<RecyclerView>(R.id.ProductRecyclerView)
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter



        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@testActivity, nav::class.java)
            //above code like send data
            startActivity(intent)
        }
    }
}