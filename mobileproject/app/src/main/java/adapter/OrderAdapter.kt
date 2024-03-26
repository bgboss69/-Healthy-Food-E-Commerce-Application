package adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileproject.databinding.OrderItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class OrderAdapter(private val orderSummary:MutableList<String>,private val total:MutableList<Int>,): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val auth = FirebaseAuth.getInstance()
    init{
        val database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        val userId = auth.currentUser?.uid?:""
        orderReference = database.child("user").child(userId).child("Order")
    }
    companion object{
        private lateinit var orderReference : DatabaseReference
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = total.size

    inner class OrderViewHolder(private val binding: OrderItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.summary.text = orderSummary[position]
            binding.total.text = "Total : RM "+total[position].toString()
            binding.btnDelete.setOnClickListener {
                val itemPosition = adapterPosition
                if (itemPosition != RecyclerView.NO_POSITION) {
                    deleteItem(itemPosition)
                }
            }
        }
        private fun deleteItem(position: Int){
            val positionRetrive = position
            getUniqueKeyAtPosition(positionRetrive){uniqueKey ->
                if (uniqueKey != null){
                    removeItem(position,uniqueKey)
                }
            }
        }
        private fun removeItem(position: Int ,uniqueKey: String){
            if (uniqueKey != null){
                orderReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    orderSummary.removeAt(position)
                    total.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,total.size)
                }
            }
        }
        private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete:(String?) -> Unit) {
            orderReference.addListenerForSingleValueEvent(object : ValueEventListener {
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

//private val auth = FirebaseAuth.getInstance()
//init{
//    val database = Firebase.database("https://mobileproject-724df-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
//    val userId = auth.currentUser?.uid?:""
//    val oderItemNumber = total.size
//    orderReference = database.child("user").child(userId).child("Order")
//}
//
//companion object{
//    private lateinit var orderReference : DatabaseReference
//}
//
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
//    val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//    return OrderViewHolder(binding)
//}
//
//override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
//    holder.bind(position)
//}
//
//override fun getItemCount(): Int = total.size
//
//
//inner class OrderViewHolder (private val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root){
//    fun bind(position: Int){
//        binding.summary.text = orderSummary[position]
//        binding.total.text = total[position].toString()
//    }
//}
//
//
//private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete:(String?) -> Unit) {
//    orderReference.addListenerForSingleValueEvent(object : ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot){
//            var uniqueKey:String? = null
//            snapshot.children.forEachIndexed { index, dataSnapshot ->
//                if (index == positionRetrieve){
//                    uniqueKey = dataSnapshot.key
//                    return@forEachIndexed
//                }
//            }
//            onComplete(uniqueKey)
//        }
//        override fun onCancelled(error: DatabaseError){
//
//        }
//    })
//}