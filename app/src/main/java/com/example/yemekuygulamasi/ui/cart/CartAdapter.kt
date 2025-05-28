import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yemekuygulamasi.data.model.CartFood
import com.example.yemekuygulamasi.databinding.ItemCartFoodBinding

class CartAdapter(
    private val onItemClick: (CartFood) -> Unit,
    private val onDeleteClick: (CartFood) -> Unit,

) : ListAdapter<CartFood, CartAdapter.CartViewHolder>(DiffCallback()) {

    inner class CartViewHolder(val binding: ItemCartFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartFood: CartFood) {
            binding.foodName.text = cartFood.yemek_adi
            binding.foodPrice.text = "${cartFood.yemek_fiyat} ₺"
            binding.foodQuantity.text = "Adet: ${cartFood.yemek_siparis_adet}"

            val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${cartFood.yemek_resim_adi}"
            Glide.with(binding.root.context).load(imageUrl).into(binding.foodImage)

            binding.root.setOnClickListener {
                onItemClick(cartFood)
            }

            binding.deleteButton.setOnClickListener {
                onDeleteClick(cartFood)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartFood = getItem(position)
        holder.bind(cartFood)
    }

    class DiffCallback : DiffUtil.ItemCallback<CartFood>() {
        override fun areItemsTheSame(oldItem: CartFood, newItem: CartFood): Boolean {
            return oldItem.sepet_yemek_id == newItem.sepet_yemek_id
        }

        override fun areContentsTheSame(oldItem: CartFood, newItem: CartFood): Boolean {
            return oldItem == newItem
        }
    }
}
