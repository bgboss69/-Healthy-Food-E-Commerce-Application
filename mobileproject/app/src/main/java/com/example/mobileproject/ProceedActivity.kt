package com.example.mobileproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
//, PaymentResultListener
class ProceedActivity : AppCompatActivity()  {

    private var database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceed)

//        Checkout.preload(applicationContext)
//        val co = Checkout()
//        // apart from setting it in AndroidManifest.xml, keyId can also be set
//        // programmatically during runtime
//        co.setKeyID("rzp_live_XXXXXXXXXXXXXX")

//        Key Id rzp_test_0N8OCe2J5uJ8aG
//        Key Secret tqY8sPCXyKodICxhQheC9nBj
        val intent = intent

        val btnBack: TextView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this@ProceedActivity, CartActivity::class.java)
            //above code like send data
            startActivity(intent)
        }


        val total: TextView = findViewById(R.id.total)
        total.setText(calculateTotalAmount().toString())
        val summary: TextView = findViewById(R.id.summary)
        summary.setText(showOrderSummary())


        val btnSubmit: Button = findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener {

//        removeItemFromCart()
//        val order = Order(showOrderSummary(),calculateTotalAmount() )
//        FirestoreClass().addItemToOrder(this@ProceedActivity, order)
            val intent = Intent(this@ProceedActivity,PaymentActivity::class.java)
            intent.putExtra("summary",showOrderSummary())
            intent.putExtra("total",calculateTotalAmount().toString())
            startActivity(intent)
        }

    }

    private fun removeItemFromCart() {
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid?:""
        var cartReference = database.child("user").child(userId).child("cart")
        cartReference.removeValue()
    }

    private fun showOrderSummary():String {
        val orderName = intent.getStringArrayListExtra("orderName")
        val orderPrice = intent.getIntegerArrayListExtra("orderPrice")
        val orderQuantities = intent.getIntegerArrayListExtra("orderQuantities")
        var summary = ""
        if (orderPrice != null) {
            for (i in 0 until orderPrice.size){
                var Name = orderName?.get(i)
                var quantity = orderQuantities?.get(i).toString()
                summary += "$Name X$quantity\n"
            }
        }
        return summary
    }
    private fun calculateTotalAmount():Int {

        val orderPrice = intent.getIntegerArrayListExtra("orderPrice")
        val orderQuantities = intent.getIntegerArrayListExtra("orderQuantities")
        var totalAmount = 0
        if (orderPrice != null) {
            for (i in 0 until orderPrice.size){
                var price = orderPrice?.get(i)
                var quantity = orderQuantities?.get(i)
                totalAmount += price?.times(quantity!!) ?: 0
            }
        }
        return totalAmount
    }

//    override fun onPaymentSuccess(p0: String?) {
//        removeItemFromCart()
//        val order = Order(showOrderSummary(),calculateTotalAmount() )
//        FirestoreClass().addItemToOrder(this@ProceedActivity, order)
//        Toast.makeText(this@ProceedActivity, "payment success", Toast.LENGTH_LONG).show()
//    }
//
//    override fun onPaymentError(p0: Int, p1: String?) {
//        Toast.makeText(this@ProceedActivity, "payment fail", Toast.LENGTH_LONG).show()
//    }
//
//
//    private fun startPayment() {
//        /*
//        *  You need to pass the current activity to let Razorpay create CheckoutActivity
//        * */
//        val activity: Activity = this
//        val co = Checkout()
//
//        try {
//            val options = JSONObject()
//            options.put("name", "Razorpay Corp")
//            options.put("description", "Demoing Charges")
//            //You can omit the image option to fetch the image from the dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
//            options.put("theme.color", "#3399cc");
//            options.put("currency", "MYR");
////            options.put("order_id", "order_DBJOWzybf0sJbb");
//            options.put("amount", "50000")//pass amount in currency subunits
//
////            val retryObj = JSONObject();
////            retryObj.put("enabled", true);
////            retryObj.put("max_count", 4);
////            options.put("retry", retryObj);
//
////            val prefill = JSONObject()
////            prefill.put("email","gaurav.kumar@example.com")
////            prefill.put("contact","9876543210")
//
//            val prefill = JSONObject()
//            prefill.put("email", "")
//            prefill.put("contact", "")
//
//            options.put("prefill", prefill)
//            co.open(activity, options)
//        } catch (e: Exception) {
//            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
//            e.printStackTrace()
//        }
//    }

}