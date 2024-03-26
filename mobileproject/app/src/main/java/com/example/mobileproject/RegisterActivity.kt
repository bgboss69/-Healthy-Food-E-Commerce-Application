package com.example.mobileproject

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

//AppCompatActivity() is replaced by BaseActivity()
//because it also inherit by AppCompatActivity()
// and we need call baseActivity()
//original : class RegisterActivity : AppCompatActivity() {
    class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


//        setupActionBar()
        val tv_signIn:TextView = findViewById<TextView>(R.id.tv_signIn)
        tv_signIn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            //above code like send data
            startActivity(intent)
        }

        val btnRegister: Button = findViewById(R.id.btn_register)
        btnRegister.setOnClickListener {
            registerUser()
        }




    }

    // if u want to create action bar
//    private fun setupActionBar(){
//        val toolbar_register_activity:Toolbar = findViewById<Toolbar>(R.id.)
//        //toolbar_register_activity id of toolbar
//        setSupportActionBar(toolbar_register_activity)
//        val actionBar = supportActionBar
//        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
//        }
//        toolbar_register_activity.setNavigationOnClickListener{ onBackPressed() }
//
//    }

    private fun validateRegisterDetails(): Boolean{
        val et_name:EditText = findViewById<EditText>(R.id.et_name)
        val et_email:EditText = findViewById<EditText>(R.id.et_email)
        val et_password:EditText = findViewById<EditText>(R.id.et_password)
        return when{
            TextUtils.isEmpty(et_name.text.toString().trim {it <= ' '}) || et_name.length() <= 3 ->{
                showErrorSnackBar("Please enter your full name",true)
                false
            }
            TextUtils.isEmpty(et_email.text.toString().trim {it <= ' '}) || !isValidEmail(et_email.text.toString().trim())->{
                showErrorSnackBar("Please enter valid email",true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim {it <= ' '})->{
                showErrorSnackBar("Please enter your password",true)
                false
            }

//            !checkBox.isChecked ......
//            et_password.text.toString().trim{ it <= ' '} != et_confirm_password.text.toString().trim{ it <= ' '} -> {
//                showErrorSnackBar("password is not same as confirm password",true)
//                false
//            }
            else -> {
//                showErrorSnackBar("Thank fot registering",false)
                true
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun registerUser(){
        val et_name:EditText = findViewById<EditText>(R.id.et_name)
        val et_email:EditText = findViewById<EditText>(R.id.et_email)
        val et_password:EditText = findViewById<EditText>(R.id.et_password)
        if(validateRegisterDetails()){
            showProgressDialog()
            val email: String = et_email.text.toString().trim{it <= ' '}
            val password: String = et_password.text.toString().trim{it <= ' '}

            //this is create of the account of firebase
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                    OnCompleteListener <AuthResult>{task ->
                        // if the register is successfully done
                        if (task.isSuccessful){

                            //firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            //make User class assign in user
                            val user = User(
                                firebaseUser.uid,
                                et_name.text.toString().trim{it <= ' '},
                                et_email.text.toString().trim{it <= ' '}
                            )

                            //store data
                            FirestoreClass().registerUser(this@RegisterActivity,user)

//                            showErrorSnackBar(
//                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
//                                false
//                            )

                            //make sign out and close the register page
//                            FirebaseAuth.getInstance().signOut()
//                            finish()
                        }else{
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                            hideProgressDialog()
                        }

                    }
                )
        }
    }

    fun userRegistrationSuccess(){
        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            "You are registered successfully",
            Toast.LENGTH_SHORT).show()
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        //above code like send data
        startActivity(intent)
    }

}