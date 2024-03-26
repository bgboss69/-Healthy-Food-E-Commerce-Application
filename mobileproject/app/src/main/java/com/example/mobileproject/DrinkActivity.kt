package com.example.mobileproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.Cart

class DrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)


        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@DrinkActivity, nav::class.java)
            //above code like send data
            startActivity(intent)
        }
        val pasta: LinearLayout = findViewById(R.id.pasta)
        pasta.setOnClickListener {
            val intent = Intent(this@DrinkActivity, PastaActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val bread: LinearLayout = findViewById(R.id.bread)
        bread.setOnClickListener {
            val intent = Intent(this@DrinkActivity, BreadActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val drink: LinearLayout = findViewById(R.id.Drink)
        drink.setOnClickListener {
            val intent = Intent(this@DrinkActivity, DrinkActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val special: LinearLayout = findViewById(R.id.special)
        special.setOnClickListener {
            val intent = Intent(this@DrinkActivity, SpecialActivity::class.java)
            //above code like send data
            startActivity(intent)
        }

        val drink1: Button = findViewById(R.id.drink1)
        drink1.setOnClickListener {
            val cart = Cart("Apple & Orange & Carrot Milk Shake", R.drawable.appleorangecarrot, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val drink2: Button = findViewById(R.id.drink2)
        drink2.setOnClickListener {
            val cart = Cart("Dragon Fruit & Banana Milk Shake", R.drawable.dragonfruitbanana, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val drink3: Button = findViewById(R.id.drink3)
        drink3.setOnClickListener {
            val cart = Cart("Kale & Kiwi Banana Milk Shake", R.drawable.kalekiwibanana, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val drink4: Button = findViewById(R.id.drink4)
        drink4.setOnClickListener {
            val cart = Cart("Pineapple & Coconut & Berry Milk Shake", R.drawable.pineapplecoconutberry, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }

    }
}