package com.example.mobileproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.Cart

class SpecialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_special)

        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@SpecialActivity, nav::class.java)
            //above code like send data
            startActivity(intent)
        }
        val pasta: LinearLayout = findViewById(R.id.pasta)
        pasta.setOnClickListener {
            val intent = Intent(this@SpecialActivity, PastaActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val bread: LinearLayout = findViewById(R.id.bread)
        bread.setOnClickListener {
            val intent = Intent(this@SpecialActivity, BreadActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val drink: LinearLayout = findViewById(R.id.Drink)
        drink.setOnClickListener {
            val intent = Intent(this@SpecialActivity, DrinkActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val special: LinearLayout = findViewById(R.id.special)
        special.setOnClickListener {
            val intent = Intent(this@SpecialActivity, SpecialActivity::class.java)
            //above code like send data
            startActivity(intent)
        }

        val special1: Button = findViewById(R.id.special1)
        special1.setOnClickListener {
            val cart = Cart("Avogado Hotdog Set", R.drawable.avogado_hotdog, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val special2: Button = findViewById(R.id.special2)
        special2.setOnClickListener {
            val cart = Cart("Avogado Shrimp Set", R.drawable.avogado_shrimp_, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val special3: Button = findViewById(R.id.special3)
        special3.setOnClickListener {
            val cart = Cart("Grill Salmon Set", R.drawable.grill_salmon_set, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
        val special4: Button = findViewById(R.id.special4)
        special4.setOnClickListener {
            val cart = Cart("Potato Egg Set", R.drawable.potato_egg, 15, 1)
            FirestoreClass().addItemToCart(this,cart)
        }
    }
}