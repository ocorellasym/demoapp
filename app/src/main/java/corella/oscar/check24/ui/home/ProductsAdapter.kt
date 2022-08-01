package corella.oscar.check24.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import corella.oscar.check24.R
import corella.oscar.check24.data.model.Product
import corella.oscar.check24.databinding.DisabledItemProductBinding
import corella.oscar.check24.databinding.ItemProductBinding

class ProductsAdapter(
    products: List<Product>,
    private val onClickListener: OnClickListener<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productList: List<Product> = products

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if(viewType == 1) {
            val binding = DataBindingUtil.inflate<ItemProductBinding>(layoutInflater, R.layout.item_product, parent, false)
            ProductCardHolder(binding)
        } else{
            val binding = DataBindingUtil.inflate<DisabledItemProductBinding>(layoutInflater, R.layout.disabled_item_product, parent, false)
            DisabledProductHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (productList[position].available) 1 else 0
    }

    //TODO Refactor
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = productList[position]
        if(holder is ProductCardHolder) {
            holder.binding.product = product
            holder.binding.productLayout.setOnClickListener {
                onClickListener.onClick(product)
            }
            Picasso.get().load(product.imageURL).into(holder.binding.image)
        }
        else {
            holder as DisabledProductHolder
            holder.binding.product = product
            holder.binding.productLayout.setOnClickListener {
                onClickListener.onClick(product)
            }
            Picasso.get().load(product.imageURL).into(holder.binding.image)
        }
    }

    internal fun setProducts(newEvents: List<Product>){
        val oldList = productList
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ProductsDiffCallback(oldList, newEvents)
        )
        productList = newEvents
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ProductCardHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DisabledProductHolder(val binding: DisabledItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    inner class ProductsDiffCallback(private var oldList: List<Product>, private var newList: List<Product>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}

class OnClickListener<T>(val clickListener: (model : T) -> Unit) {
    fun onClick(model : T) = clickListener(model)
}