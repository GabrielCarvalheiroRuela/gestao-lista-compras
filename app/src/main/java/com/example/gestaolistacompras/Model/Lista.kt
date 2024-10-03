package com.example.gestaolistacompras.Model

import android.net.Uri
import java.io.Serializable

// Cria um objeto lista
data class Lista(
    val id: String,
    val nome: String,
    val imagemUri: Uri?,
    val listaDeItens: MutableList<Item> = mutableListOf()
) : Serializable