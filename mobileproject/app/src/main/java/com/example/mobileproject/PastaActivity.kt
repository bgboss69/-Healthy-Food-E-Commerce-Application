package com.example.mobileproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.Cart

class PastaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasta)

        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@PastaActivity, nav::class.java)
            //above code like send data
            startActivity(intent)
        }
        val pasta: LinearLayout = findViewById(R.id.pasta)
        pasta.setOnClickListener {
            val intent = Intent(this@PastaActivity, PastaActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val bread: LinearLayout = findViewById(R.id.bread)
        bread.setOnClickListener {
            val intent = Intent(this@PastaActivity, BreadActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val drink: LinearLayout = findViewById(R.id.Drink)
        drink.setOnClickListener {
            val intent = Intent(this@PastaActivity, DrinkActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val special: LinearLayout = findViewById(R.id.special)
        special.setOnClickListener {
            val intent = Intent(this@PastaActivity, SpecialActivity::class.java)
            //above code like send data
            startActivity(intent)
        }

        val pasta1: Button = findViewById(R.id.pasta1)
        pasta1.setOnClickListener {
            val cart = Cart("Carbonara", R.drawable.pasta1, 25, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val pasta2: Button = findViewById(R.id.pasta2)
        pasta2.setOnClickListener {
            val cart = Cart("Bolognese", R.drawable.pasta2, 25, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val pasta3: Button = findViewById(R.id.pasta3)
        pasta3.setOnClickListener {
            val cart = Cart("Black Pepper Pasta", R.drawable.pasta3, 25, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val pasta4: Button = findViewById(R.id.pasta4)
        pasta4.setOnClickListener {
            val cart = Cart("Curry Pasta", R.drawable.pasta4, 25, 1)
            FirestoreClass().addItemToCart(this,cart)
        }

    }
}