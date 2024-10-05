package com.example.gestaolistacompras

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gestaolistacompras.Model.Lista
import com.example.gestaolistacompras.databinding.ListCardBinding

class ListAdapter(
    private val listas: MutableList<Lista>,
    private val onItemClick: (Lista) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ListCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(listas[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val lista = listas[position]
        val binding = holder.binding

        binding.listNameTextView.text = lista.nome

        lista.imagemUri?.let { uriString ->
            val uri = Uri.parse(uriString)
            Glide.with(holder.itemView.context)
                .load(uri)
                .placeholder(android.R.drawable.ic_menu_gallery) // Imagem padrão enquanto carrega
                .into(binding.listImageView)
        } ?: run {
            binding.listImageView.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    override fun getItemCount(): Int {
        return listas.size
    }
}
