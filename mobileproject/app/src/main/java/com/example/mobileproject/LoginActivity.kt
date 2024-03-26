package com.example.mobileproject

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.User
import com.example.mobileproject.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
        // Accessing Views
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvForgetPassword = findViewById<TextView>(R.id.tv_forgetPassword)
        val tvRegister = findViewById<TextView>(R.id.tv_register)

        // Set onClickListener for your views or perform any other logic here

        // Example: Set an onClickListener for the login button
        btnLogin.setOnClickListener {
            LogInRegisteredUser()
        }

        // Example: Set an onClickListener for the "Forget Password?" TextView
        tvForgetPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            //above code like send data
            startActivity(intent)
        }

//         Example: Set an onClickListener for the "Click Here" TextView
        tvRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            //above code like send data
            startActivity(intent)
        }



    }
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
    private fun validateLoginDetails(): Boolean{
        val et_email:EditText = findViewById<EditText>(R.id.et_email)
        val et_password:EditText = findViewById<EditText>(R.id.et_password)
        return when{
            TextUtils.isEmpty(et_email.text.toString().trim {it <= ' '}) || !isValidEmail(et_email.text.toString().trim())->{
                showErrorSnackBar("Please enter valid email",true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim {it <= ' '}) || et_email.length() <= 3 ->{
                showErrorSnackBar("Please enter your password",true)
                false
            }else -> {
                true
            }
        }
    }
    private fun LogInRegisteredUser(){

        val et_email:EditText = findViewById<EditText>(R.id.et_email)
        val et_password:EditText = findViewById<EditText>(R.id.et_password)
        if(validateLoginDetails()){
            showProgressDialog()
            val email: String = et_email.text.toString().trim{it <= ' '}
            val password: String = et_password.text.toString().trim{it <= ' '}

            //this is check sign in of firebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                    OnCompleteListener <AuthResult>{ task ->
//                        hideProgressDialog()
                        // if the register is successfully done
                        if (task.isSuccessful){

                            //firebase registered user
//                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            FirestoreClass().getUserDetails(this@LoginActivity)
//                            showErrorSnackBar("You are login successfully.", false)

                        }else{
                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
                )
        }
    }

    fun userLoggedInSuccess(user : User){
        hideProgressDialog()

        Log.i("fullName:", user.fullName)
        Log.i("email:", user.email)

        if( user.profileCompleted == 0){
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else{
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

        finish()
    }
}