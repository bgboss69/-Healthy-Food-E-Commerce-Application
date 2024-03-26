package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileproject.databinding.ItemLayoutBinding

class ProductAdapter(private val items:List<String>,private val prices:List<Int> ,private val images:List<Int>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        val price = prices[position]
        val image = images[position]
        holder.bind(item,price,image)
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    class ProductViewHolder (private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String, price:Int, image:Int){
            binding.productName.text = item
            binding.ProductImage.setImageResource(image)
            binding.price.text = price.toString()
        }
    }
}
