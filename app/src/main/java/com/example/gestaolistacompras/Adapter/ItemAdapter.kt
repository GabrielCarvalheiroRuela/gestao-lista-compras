package com.example.gestaolistacompras.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.databinding.ItemCardBinding

// vinculando a lista de itens a RecyclerView
class ItemAdapter(private val itemList: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        // Coloca o dado dos itens no layout
        fun bind(item: Item) {
            binding.itemName.text = item.name // Define o nome do item
            binding.itemQuantity.text = item.quantity.toString() // Define a quantidade
            binding.ItemUnit.text = item.unit // Define a unidade
            binding.itemImage.setImageResource(item.image) // Define a imagem de acordo com a categoria
            binding.itemCheckbox.isChecked = false // Checkbox se foi comprado ou nao
        }
    }

    // Cria o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // Vincula os dados de cada item no ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    // Retorna o tamanho da lista de itens
    override fun getItemCount(): Int {
        return itemList.size
    }
}
