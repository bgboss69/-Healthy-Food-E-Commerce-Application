package com.example.mobileproject

import adapter.CartAdapter
import adapter.ProductAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.Cart
import com.example.mobileproject.models.User
import com.example.mobileproject.utils.Constants
import com.google.android.gms.common.util.CollectionUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class CartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var database =Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    private lateinit var Item: MutableList<String>
    private lateinit var Price: MutableList<Int>
    private lateinit var Image: MutableList<Int>
    private lateinit var Quantities: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

//        val item = listOf("Burger", "sandwich", "momo", "item")
//        val price = listOf(10, 20, 30, 40)
//        val image = listOf(
//            R.drawable.appleorangecarrot,
//            R.drawable.avogado_hotdog,
//            R.drawable.crab_willow_sandwich,
//            R.drawable.cheese_floss_sandwich
//        )


        auth = FirebaseAuth.getInstance()
        reteriveCartItems()

//        val adapter = CartAdapter(ArrayList(item),ArrayList(price),ArrayList(image),ArrayList(image))
//        val recyclerView = findViewById<RecyclerView>(R.id.CartRecyclerView)
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter


        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@CartActivity, nav::class.java)
            //above code like send data
            startActivity(intent)
        }
        val btn: TextView = findViewById(R.id.btn)
        btn.setOnClickListener {
//            val intent = Intent(this@CartActivity, nav::class.java)
//            //above code like send data
//            FirestoreClass().getUserDetails(this@CartActivity)
//            startActivity(intent)
            getOrderItemsDetail()
        }

    }

    private fun getOrderItemsDetail(){
        userId = auth.currentUser?.uid?:""
        var orderIdReference = database.child("user").child(userId).child("cart")
        var orderItem :MutableList<String> = mutableListOf()
        var orderPrice :MutableList<Int> = mutableListOf()
        var orderImage :MutableList<Int> = mutableListOf()
        var orderQuantities :MutableList<Int> = cartAdapter.getUpdatedItemsQuantities()

        orderIdReference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children){
                    //get cart object from the child node
                    val order = orderSnapshot.getValue(Cart::class.java)
                    order?.let {
                        it.cartImage?.let { orderImage.add(it) }
                        it.cartName?.let { orderItem.add(it) }
                        it.cartPrice?.let { orderPrice.add(it) }
                        it.cartQuantity?.let { orderQuantities.add(it) }
                    }
                }
                val intent = Intent(this@CartActivity,ProceedActivity::class.java)
                intent.putExtra("orderName",orderItem as ArrayList<String>)
                intent.putExtra("orderImage",orderImage as ArrayList<Int>)
                intent.putExtra("orderPrice",orderPrice as ArrayList<Int>)
                intent.putExtra("orderQuantities",orderQuantities as ArrayList<Int>)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CartActivity, "Order making Fail. Please try again", Toast.LENGTH_SHORT).show()
            }

        })
    }



    private fun reteriveCartItems(){
            userId = auth.currentUser?.uid?:""
            val cartReference= database.child("user").child(userId).child("cart")
            Image = mutableListOf()
            Item = mutableListOf()
            Price = mutableListOf()
            Quantities = mutableListOf()


            cartReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children){
                        //get cart object from the child node
                        val cartItems = cartSnapshot.getValue(Cart::class.java)
                        cartItems?.let {
                            it.cartImage?.let { Image.add(it) }
                            it.cartName?.let { Item.add(it) }
                            it.cartPrice?.let { Price.add(it) }
                            it.cartQuantity?.let { Quantities.add(it) }
                        }
                    }
                    setAdapter()
                }
                private fun setAdapter(){
                    cartAdapter = CartAdapter(Item,Price,Image,Quantities)
                    val recyclerView = findViewById<RecyclerView>(R.id.CartRecyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@CartActivity)
                    recyclerView.adapter = cartAdapter
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error fetching data: ${error.message}")
                    Toast.makeText(this@CartActivity, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

//        fun navToProceed(user : User){
//            Log.i("fullName:", user.fullName)
//            Log.i("email:", user.email)
//
//            val intent = Intent(this, UserProfileActivity::class.java)
//            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
//            startActivity(intent)
//
//            finish()
//        }



}