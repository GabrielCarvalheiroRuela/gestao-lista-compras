package com.example.gestaolistacompras

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaolistacompras.Model.Lista
import com.example.gestaolistacompras.databinding.ListCardBinding

class ListAdapter(
    private val listas: MutableList<Lista>,
    private val onItemClick: (Lista) -> Unit // Adiciona um lambda para o clique
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ListCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Configura o clique no item
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Chama a lista clicada
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

        // Coloca o nome da lista no card
        binding.listNameTextView.text = lista.nome

        // Verifica se possui imagem e coloca ela no card
        lista.imagemUri?.let { uri ->
            try {
                val inputStream = holder.itemView.context.contentResolver.openInputStream(uri)
                val drawable = Drawable.createFromStream(inputStream, uri.toString())
                binding.listImageView.setImageDrawable(drawable)
            } catch (e: Exception) {
                // Exibe uma imagem padrão se der erro
                binding.listImageView.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        } ?: run {
            // Define a imagem padrão se não tiver imagem
            binding.listImageView.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    override fun getItemCount() = listas.size

    // Função de adicionar lista no RecyclerView
    fun addLista(lista: Lista) {
        listas.add(lista)
        notifyItemInserted(listas.size - 1)
    }
}