package com.example.gestaolistacompras.Model

import java.io.Serializable

// Cria um objeto Item
data class Item(
    val image: Int,
    val name: String,
    val quantity: Int,
    val unit: String,
    val category: String,
    var checked: Boolean
) : Serializable