package com.example.mobileproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.Cart

class BreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bread)

        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@BreadActivity, nav::class.java)
            //above code like send data
            startActivity(intent)
        }
        val pasta: LinearLayout = findViewById(R.id.pasta)
        pasta.setOnClickListener {
            val intent = Intent(this@BreadActivity, PastaActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val bread: LinearLayout = findViewById(R.id.bread)
        bread.setOnClickListener {
            val intent = Intent(this@BreadActivity, BreadActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val drink: LinearLayout = findViewById(R.id.Drink)
        drink.setOnClickListener {
            val intent = Intent(this@BreadActivity, DrinkActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val special: LinearLayout = findViewById(R.id.special)
        special.setOnClickListener {
            val intent = Intent(this@BreadActivity, SpecialActivity::class.java)
            //above code like send data
            startActivity(intent)
        }

        val bread1:Button = findViewById(R.id.bread1)
        bread1.setOnClickListener {
            val cart = Cart("Scramble Egg Sandwich", R.drawable.scramble_egg_sandwich, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
            }
        val bread2:Button = findViewById(R.id.bread2)
        bread2.setOnClickListener {
            val cart = Cart("Floss Egg Sandwich", R.drawable.floss_egg_sandwich, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val bread3:Button = findViewById(R.id.bread3)
        bread3.setOnClickListener {
            val cart = Cart("Crab Willow Sandwich", R.drawable.crab_willow_sandwich, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val bread4:Button = findViewById(R.id.bread4)
        bread4.setOnClickListener {
            val cart = Cart("Cheese Floss Sandwich", R.drawable.cheese_floss_sandwich, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }

    }
}