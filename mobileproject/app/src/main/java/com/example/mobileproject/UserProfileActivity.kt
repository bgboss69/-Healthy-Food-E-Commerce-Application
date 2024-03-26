package com.example.mobileproject

// manifest import for READ_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.User
import com.example.mobileproject.utils.Constants


class UserProfileActivity : BaseActivity() {
    private lateinit var mUserDetails: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
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


        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }
        // Accessing Views
//        val iv_user_photo = findViewById<ImageView>(R.id.iv_user_photo)
        val etName = findViewById<EditText>(R.id.et_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etAddress1 = findViewById<EditText>(R.id.et_address1)
        val etAddress2 = findViewById<EditText>(R.id.et_address2)
        val etAddress3 = findViewById<EditText>(R.id.et_address3)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val rgGender = findViewById<RadioGroup>(R.id.rg_gender)
        val rbMale = findViewById<RadioButton>(R.id.rb_male)
        val rbFemale = findViewById<RadioButton>(R.id.rb_female)
        val btnBack: TextView = findViewById(R.id.back)

        etName.isEnabled = false
        etName.setText(mUserDetails.fullName)

        etEmail.isEnabled = false
        etEmail.setText(mUserDetails.email)
        etPhone.setText(mUserDetails.mobile.toString())
        etAddress1.setText(mUserDetails.address1)
        etAddress2.setText(mUserDetails.address2)
        etAddress3.setText(mUserDetails.address3)
        if(mUserDetails.gender == "male"){
            rbMale.setChecked(true)
        }else if(mUserDetails.gender == "female"){
            rbFemale.setChecked(true)
        }



//        // local storage permission
//        iv_user_photo.setOnClickListener {
//            // check permission for access data of phone local storage
//            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_GRANTED){
//                showErrorSnackBar("you already have storage permission", false)
//            }else{
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    Constants.READ_STORAGE_PERMISSION_CODE
//                )
//            }
//
//        }
        btnBack.setOnClickListener {
            val intent = Intent(this@UserProfileActivity, MainActivity::class.java)
            //above code like send data
            startActivity(intent)
        }
        btnSubmit.setOnClickListener {
            if(validateUserProfileDetails()){
                val etPhone = findViewById<EditText>(R.id.et_phone).text.toString().trim{ it <=  ' '}
                val rgGender = findViewById<RadioGroup>(R.id.rg_gender)
                val rbMale = findViewById<RadioButton>(R.id.rb_male)
                val rbFemale = findViewById<RadioButton>(R.id.rb_female)
                val etAddress1 = findViewById<EditText>(R.id.et_address1).text.toString()
                val etAddress2 = findViewById<EditText>(R.id.et_address2).text.toString()
                val etAddress3 = findViewById<EditText>(R.id.et_address3).text.toString()
                //key:"" value:""
                val userHashMap = HashMap<String,Any>()

                val gender = if(rbMale.isChecked){
                    "male"
                }else if(rbFemale.isChecked){
                    "female"
                }else{
                    ""
                }

                if (etPhone.isNotEmpty()){
                    userHashMap["mobile"] = etPhone.toLong()
                }
                userHashMap["gender"] = gender
//                showErrorSnackBar("your details are valid",false)

                if (etAddress1.isNotEmpty()) {
                    userHashMap["address1"] = etAddress1
                }
                if (etAddress2.isNotEmpty()) {
                    userHashMap["address2"] = etAddress2
                }
                if (etAddress3.isNotEmpty()) {
                    userHashMap["address3"] = etAddress3
                }
                userHashMap["profileCompleted"] = 1
                showProgressDialog()
                FirestoreClass().updateUserProfileData(this,userHashMap)
            }
        }
    }

    private fun validateUserProfileDetails():Boolean{
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etAddress1 = findViewById<EditText>(R.id.et_address1)
        val etAddress2 = findViewById<EditText>(R.id.et_address2)
        val etAddress3 = findViewById<EditText>(R.id.et_address3)
        return when {
            TextUtils.isEmpty(etPhone.text.toString().trim{ it <=  ' '})->{
                showErrorSnackBar("Please enter mobile number!",true)
                false
            }
            TextUtils.isEmpty(etAddress1.text.toString().trim{ it <=  ' '})->{
                showErrorSnackBar("Please enter address 1!",true)
                false
            }else -> {
                true
            }

        }
    }

    fun userProfileUpdateSuccess(){
        hideProgressDialog()

        Toast.makeText(
            this@UserProfileActivity,
            "update success",
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity,MainActivity::class.java))
        finish()
    }
//    // local storage permission
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
//            //if permission is granted
//            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                showErrorSnackBar("The storage permission is granted", false)
//            }else{
//                //Displaying another toast if permission is not granted
//                Toast.makeText(
//                    this,
//                    ("Oops,you just denied the permission for storage. You can also allow it from setting."),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }
}