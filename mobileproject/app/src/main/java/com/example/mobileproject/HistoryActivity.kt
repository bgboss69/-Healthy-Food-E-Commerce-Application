package com.example.mobileproject

import adapter.OrderAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileproject.models.Order
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class HistoryActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private var database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        private lateinit var orderSummary: MutableList<String>
        private lateinit var total: MutableList<Int>
        private lateinit var orderAdapter: OrderAdapter
        private lateinit var userId: String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_history)

            auth = FirebaseAuth.getInstance()
            reteriveCartItems()

            val btnBack: TextView = findViewById(R.id.back)
            btnBack.setOnClickListener {
                val intent = Intent(this@HistoryActivity, nav::class.java)
                //above code like send data
                startActivity(intent)
            }

        }

        private fun reteriveCartItems(){
            userId = auth.currentUser?.uid?:""
            val OrderReference= database.child("user").child(userId).child("Order")
            orderSummary = mutableListOf()
            total = mutableListOf()

            OrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (orderSnapshot in snapshot.children){
                        //get cart object from the child node
                        val orderItems = orderSnapshot.getValue(Order::class.java)
                        orderItems?.let {
                            it.orderSummary?.let { orderSummary.add(it) }
                            it.total?.let { total.add(it) }
                        }
                    }
                    setAdapter()
                }
                private fun setAdapter(){
                    orderAdapter = OrderAdapter(orderSummary,total)
                    val recyclerView = findViewById<RecyclerView>(R.id.CartRecyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    recyclerView.adapter = orderAdapter
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error fetching data: ${error.message}")
                    Toast.makeText(this@HistoryActivity, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }