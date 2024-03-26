package com.example.mobileproject

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
            //above code like send data
            startActivity(intent)
        }

        val btnSubmit: Button= findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener {

            val email:String = findViewById<EditText>(R.id.et_email).text.toString().trim()
            if (email.isEmpty()||!isValidEmail(email)){
                showErrorSnackBar("Please enter valid email",true)
            }else{
                showProgressDialog()
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        hideProgressDialog()
                        if(task.isSuccessful){
                            Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Email sent successfully to reset your password!",
                            Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    // if u want to create action bar
//    private fun setupActionBar() {
//        val toolbar_forgot_passowrd_activity: Toolbar = findViewById<Toolbar>(R.id.toolbar)
//        //toolbar_register_activity id of toolbar
//        setSupportActionBar(toolbar_forgot_passowrd_activity)
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
//        }
//        toolbar_forgot_passowrd_activity.setNavigationOnClickListener {
//            val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
//            //above code like send data
//            startActivity(intent)
//        }
//    }
}