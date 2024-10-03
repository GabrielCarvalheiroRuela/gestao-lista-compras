package com.example.gestaolistacompras.Model

object ItemBD {
    private val itemList: MutableList<Item> = mutableListOf()

    fun getItemList(): MutableList<Item> {
        return itemList
    }
}
