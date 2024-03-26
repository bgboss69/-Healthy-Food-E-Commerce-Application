package com.example.mobileproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.Cart
import com.example.mobileproject.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences =
            //get data from share preferences (everytime should call) like make connection to database
            getSharedPreferences(
                //below like table name
                Constants.MOBILEPROJECT_PREFERENCES,
                Context.MODE_PRIVATE
            )
        val username= sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!

        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@MainActivity, nav::class.java)
            //above code like send data
            startActivity(intent)
        }
        val pasta: LinearLayout = findViewById(R.id.pasta)
        pasta.setOnClickListener {
            val intent = Intent(this@MainActivity, PastaActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val bread: LinearLayout = findViewById(R.id.bread)
        bread.setOnClickListener {
            val intent = Intent(this@MainActivity, BreadActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val drink: LinearLayout = findViewById(R.id.Drink)
        drink.setOnClickListener {
            val intent = Intent(this@MainActivity, DrinkActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val special: LinearLayout = findViewById(R.id.special)
        special.setOnClickListener {
            val intent = Intent(this@MainActivity, SpecialActivity::class.java)
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