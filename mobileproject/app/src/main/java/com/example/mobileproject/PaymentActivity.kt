package com.example.mobileproject

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileproject.firestore.FirestoreClass
import com.example.mobileproject.models.Order
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

    class PaymentActivity: AppCompatActivity(), PaymentResultWithDataListener,
        ExternalWalletListener, DialogInterface.OnClickListener {
        private var database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        private lateinit var auth: FirebaseAuth
        val TAG:String = PaymentActivity::class.toString()
        private lateinit var alertDialogBuilder: AlertDialog.Builder

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_payment)
            /*
            * To ensure faster loading of the Checkout form,
            * call this method as early as possible in your checkout flow
            * */
            Checkout.preload(applicationContext)
            alertDialogBuilder = AlertDialog.Builder(this@PaymentActivity)
            alertDialogBuilder.setTitle("Payment Result")
            alertDialogBuilder.setCancelable(true)
            alertDialogBuilder.setPositiveButton("Ok",this)
            val button: Button = findViewById(R.id.btn_pay)
            button.setOnClickListener {
                startPayment()
            }

            val summary = intent.getStringExtra("summary")
            val total = intent.getStringExtra("total")
            val textTotal: TextView = findViewById(R.id.total)
            textTotal.setText("MYR "+total)
            val textsummary: TextView = findViewById(R.id.summary)
            textsummary.setText(summary)
        }

        private fun startPayment() {
            /*
            *  You need to pass current activity in order to let Razorpay create CheckoutActivity
            * */
            val summary = intent.getStringExtra("summary")
            val total = intent.getStringExtra("total")
            val activity: Activity = this
            val co = Checkout()
            co.setKeyID("rzp_test_swnNqUFW65XpiM")
//            val etApiKey = findViewById<EditText>(R.id.et_api_key)
//            val etCustomOptions = findViewById<EditText>(R.id.et_custom_options)
//            if (!TextUtils.isEmpty(etApiKey.text.toString())){
//                co.setKeyID(etApiKey.text.toString())
//            }

            //                if (!TextUtils.isEmpty(etCustomOptions.text.toString())){
//                    options = JSONObject(etCustomOptions.text.toString())
//                }else{
//                    options.put("name",summary)
//                    options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
//                    options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
//                    options.put("currency","MYR")
//                    options.put("amount",total)
//                    options.put("send_sms_hash",true);




//                }
            try {
                var options = JSONObject()
                options.put("name", summary)
                options.put("description", "Demoing Charges")
                options.put("currency", "MYR")
                options.put("amount", total+"00")

                val prefill = JSONObject()
                prefill.put("email", "")
                prefill.put("contact", "")
                options.put("prefill", prefill)
                co.open(activity,options)
            }catch (e: Exception){
                Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }

        override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
            try{
                alertDialogBuilder.setMessage("Payment Successful : Payment ID: $p0\nPayment Data: ${p1?.data}")
                alertDialogBuilder.show()
                val summary = intent.getStringExtra("summary")
                val total = intent.getStringExtra("total")?.toInt()
                val order = Order(summary,total)
                FirestoreClass().addItemToOrder(this@PaymentActivity, order)
                val intent = Intent(this@PaymentActivity,nav::class.java)
                startActivity(intent)
                removeItemFromCart()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
            try {
                alertDialogBuilder.setMessage("Payment Failed : Payment Data: ${p2?.data}")
                alertDialogBuilder.show()
                val intent = Intent(this@PaymentActivity, CartActivity::class.java)
                startActivity(intent)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
            try{
                alertDialogBuilder.setMessage("External wallet was selected : Payment Data: ${p1?.data}")
                alertDialogBuilder.show()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        override fun onClick(dialog: DialogInterface?, which: Int) {
        }
        private fun removeItemFromCart() {
            auth = FirebaseAuth.getInstance()
            val userId = auth.currentUser?.uid?:""
            var cartReference = database.child("user").child(userId).child("cart")
            cartReference.removeValue()
        }
    }