package com.example.vk_test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vk_test.databinding.ProductItemBinding

class ProductAdapter(private var productList: List<ProductClass>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var onClickListener: OnClickListener? = null

    class ProductViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val item = productList[position]
        with(holder.binding){
            title.text = item.title
            description.text = item.description
            price.text = item.price.toString()

            Glide.with(thumbnail.context).load(item.thumbnail).into(thumbnail)


        }
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: ProductClass)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

}