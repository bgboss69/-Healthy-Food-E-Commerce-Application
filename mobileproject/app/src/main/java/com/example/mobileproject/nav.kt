package com.example.mobileproject

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.User
import com.example.mobileproject.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class nav : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val sharedPreferences =
            //get data from share preferences (everytime should call) like make connection to database
            getSharedPreferences(
                //below like table name
                Constants.MOBILEPROJECT_PREFERENCES,
                Context.MODE_PRIVATE
            )
        val fullname = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!
        val email = sharedPreferences.getString(Constants.LOGGED_IN_EMAIL,"")!!
        val tv_name = findViewById<TextView>(R.id.tv_name)
        val tv_email = findViewById<TextView>(R.id.tv_email)
        tv_name.text = "$fullname"
        tv_email.text = "$email"


        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@nav, MainActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val btnHome: LinearLayout = findViewById(R.id.home)
        btnHome.setOnClickListener {
            val intent = Intent(this@nav, MainActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val btnProfile: LinearLayout = findViewById(R.id.profile)
        btnProfile.setOnClickListener {
            FirestoreClass().getUserDetails(this@nav)
        }
        val btnCart: LinearLayout = findViewById(R.id.cart)
        btnCart.setOnClickListener {
            val intent = Intent(this@nav, CartActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        val btnHistory: LinearLayout = findViewById(R.id.history)
        btnHistory.setOnClickListener {
            val intent = Intent(this@nav, HistoryActivity::class.java)
            startActivity(intent)
        }
        val btnLogout: Button = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@nav, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }

    fun navToProfile(user : User){
        Log.i("fullName:", user.fullName)
        Log.i("email:", user.email)

        val intent = Intent(this@nav, UserProfileActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intent)

        finish()
    }
}