package com.example.mobileproject

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //code to show full screen
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //call a function after delay
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            //navigation
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            //intent.putExtra("bmi", calculateBMI(weight,height))
            //above code like send data
            startActivity(intent)
            finish()
        }, 2500)


        //create a font with customise font family 52.24 (end)


    }
}