// Gabriel Carvalheiro Ruela - 837871
package com.example.gestaolistacompras.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.databinding.ItemCardBinding

class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.itemName.text = item.name // Define o nome do item
            binding.itemQuantity.text = item.quantity.toString() // Define a quantidade
            binding.ItemUnit.text = item.unit // Define a unidade
            binding.itemImage.setImageResource(item.image) // Define a imagem
            binding.itemCheckbox.isChecked = item.checked // Define se o item está marcado
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}
