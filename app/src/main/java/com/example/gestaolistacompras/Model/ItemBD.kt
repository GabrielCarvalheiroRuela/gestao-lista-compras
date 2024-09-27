package com.example.gestaolistacompras.Model

object ItemBD {
    private val itemList = mutableListOf<Item>()

    // Função de adicionar item na lista
    fun addItem(item: Item) {
        itemList.add(item)
    }

    // Função Get Item
    fun getItemList(): MutableList<Item> {
        return itemList
    }
}