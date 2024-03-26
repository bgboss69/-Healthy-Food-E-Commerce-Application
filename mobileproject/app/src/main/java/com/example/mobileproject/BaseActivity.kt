package com.example.mobileproject

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar



//open is added due to need to call
open class BaseActivity : AppCompatActivity() {
//
//    above original code example:create on ... is deleted because it is inherit

    private lateinit var mProgressDialog :Dialog
    fun showErrorSnackBar(message: String, errorMessage: Boolean)
    {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if(errorMessage){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }

    fun showProgressDialog(){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
}