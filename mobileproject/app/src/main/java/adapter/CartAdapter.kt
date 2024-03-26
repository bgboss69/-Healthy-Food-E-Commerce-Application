package adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileproject.R
import com.example.mobileproject.databinding.CartItemBinding
import com.example.mobileproject.models.Cart
import com.example.mobileproject.nav
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context
import com.google.firebase.database.database

class CartAdapter(
    private val cartItems:MutableList<String>,
    private val cartPrices:MutableList<Int>,
    private val cartImages:MutableList<Int>,
    private val cartQuantities:MutableList<Int>,
): RecyclerView.Adapter<CartAdapter.CartViewHolder>(){
    private val auth = FirebaseAuth.getInstance()
    init{

        val database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        val userId = auth.currentUser?.uid?:""
        val cartItemNumber = cartItems.size
        cartReference = database.child("user").child(userId).child("cart")
        quantities =IntArray(cartItemNumber){1}
    }

    companion object{
        private var quantities : IntArray = intArrayOf()
        private lateinit var cartReference :DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    fun getUpdatedItemsQuantities() : MutableList<Int>{
        val quantity = mutableListOf<Int>()
        quantity.addAll(cartQuantities)
        return quantity
    }


    inner class CartViewHolder (private val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val quantity = quantities[position]
            binding.Name.text = cartItems[position]
            binding.Image.setImageResource(cartImages[position])
            binding.Price.text = cartPrices[position].toString()
            binding.Num.text = quantity.toString()

            binding.btnMinus.setOnClickListener { decreaseQuantity(position) }
            binding.btnAdd.setOnClickListener { increaseQuantity(position) }
            binding.btnDelete.setOnClickListener {
                val itemPosition = adapterPosition
                if (itemPosition != RecyclerView.NO_POSITION){
                    deleteItem(itemPosition)
                }
            }
        }
        private fun decreaseQuantity(position: Int){
            if (quantities[position]>1 ){
                quantities[position]--
                cartQuantities[position] = quantities[position]
                binding.Num.text = quantities[position].toString()
            }
        }
        private fun increaseQuantity(position: Int){
            if (quantities[position]<10 ){
                quantities[position]++
                cartQuantities[position] = quantities[position]
                binding.Num.text = quantities[position].toString()
            }
        }
        private fun deleteItem(position: Int){
//            cartItems.removeAt(position)
//            cartImages.removeAt(position)
//            cartPrices.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position,cartItems.size)
            val positionRetrive = position
            getUniqueKeyAtPosition(positionRetrive){uniqueKey ->
                if (uniqueKey != null){
                    removeItem(position,uniqueKey)
                }
            }
        }
        private fun removeItem(position: Int ,uniqueKey: String){
            if (uniqueKey != null){
                cartReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartImages.removeAt(position)
                    cartPrices.removeAt(position)
                    cartQuantities.removeAt(position)
                    quantities = quantities.filterIndexed{ index, i -> index!=position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,cartItems.size)
                }
            }
        }



        private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete:(String?) -> Unit) {
            cartReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    var uniqueKey:String? = null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == positionRetrieve){
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }
                override fun onCancelled(error: DatabaseError){

                }
            })
        }
    }
}
