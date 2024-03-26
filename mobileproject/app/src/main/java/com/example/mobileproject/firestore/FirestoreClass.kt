package com.example.mobileproject.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.mobileproject.LoginActivity
import com.example.mobileproject.RegisterActivity
import com.example.mobileproject.UserProfileActivity
import com.example.mobileproject.models.Cart
import com.example.mobileproject.models.Order
import com.example.mobileproject.models.User
import com.example.mobileproject.nav
import com.example.mobileproject.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    //important
    private val mFireStore = FirebaseFirestore.getInstance()

//    fun addCart(activity: Activity, cart:Cart) {
//        // Get reference to the user's cart collection
//        mFireStore.collection(Constants.CARTS)
//            .document(getCurrentUserID())
//            .set(cart, SetOptions.merge())
//            .addOnSuccessListener {
//                Toast.makeText(
//                    activity,
//                    "Cart added successfully",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            .addOnFailureListener { e ->
//                Log.e(
//                    activity.javaClass.simpleName,
//                    "Error while adding the cart.",
//                    e
//                )
//            }
//    }


    fun registerUser(activity: RegisterActivity , userInfo: User){
        mFireStore.collection(Constants.USERS)
//        mFireStore.collection("users")
            // set like primary key in id
            .document(userInfo.id)
            //merge data by user class data
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener {e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "error while registering the user",
                    e
                )
            }
    }




    fun getCurrentUserID(): String {
        //An instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
    //pass the collection data from which we want data.
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())
                //Here we received the document snapshot which is converted into object
                val user = document.toObject(User::class.java)!!

                //store the small data to share preferences like (session storage)

                val sharedPreferences =
                    //get data from share preferences (everytime should call) like make connection to database
                    activity.getSharedPreferences(
                        //below like table name
                        Constants.MOBILEPROJECT_PREFERENCES,
                        Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                        //below like variable name
                        //key: LOGGED_IN_USERNAME
                        //value :
                        Constants.LOGGED_IN_USERNAME,
                        "${user.fullName}"

                )
                editor.putString(
                    //below like variable name
                    //key: LOGGED_IN_USERNAME
                    //value :
                    Constants.LOGGED_IN_EMAIL,
                    "${user.email}"
                )
                //after edit(above code) must apply
                editor.apply()
                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                    is nav -> {
                        activity.navToProfile(user)
                    }
                }
            }
//            .addOnFailureListener {e ->
//
//            }


    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String,Any>){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity){
                    is UserProfileActivity ->{
                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e->
                when (activity){
                    is UserProfileActivity ->{
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the user details.",
                    e
                )
            }
    }



    fun addItemToCart(activity: Activity,cart:Cart){
        val userId = FirebaseAuth.getInstance().currentUser?.uid?:""
        val database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        val cartReference = database.child("user").child(userId).child("cart")
        cartReference.push().setValue(cart).addOnSuccessListener {
            Toast.makeText(activity,"Item added into Cart Successfully",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(activity,"Item Not added",Toast.LENGTH_SHORT).show()
        }
    }

    fun addItemToOrder(activity: Activity,order: Order){
        val userId = FirebaseAuth.getInstance().currentUser?.uid?:""
        val database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        val cartReference = database.child("user").child(userId).child("Order")
        cartReference.push().setValue(order).addOnSuccessListener {
            Toast.makeText(activity,"Order Successfully",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(activity,"Order Failed",Toast.LENGTH_SHORT).show()
        }
    }

}