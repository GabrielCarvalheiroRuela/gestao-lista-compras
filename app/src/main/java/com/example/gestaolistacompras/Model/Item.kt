package com.example.gestaolistacompras.Model

import java.io.Serializable

data class Item(
    val image: Int,
    val name: String,
    var quantity: Int,
    val unit: String,
    val category: String,
    var isPurchased: Boolean
) : Serializable