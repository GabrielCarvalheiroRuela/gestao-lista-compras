package com.example.gestaolistacompras.Model

object ItemBD {
    // Armazena os itens por listaId
    private val itemMap: MutableMap<String, MutableList<Item>> = mutableMapOf()

    // Retorna a lista de itens da lista clicada
    fun getItensDaLista(listaId: String): MutableList<Item> {
        return itemMap[listaId] ?: mutableListOf()
    }

    // Adiciona um novo item na lista aberta
    fun addItemNaLista(listaId: String, item: Item) {
        if (itemMap[listaId] == null) {
            itemMap[listaId] = mutableListOf()
        }
        itemMap[listaId]?.add(item)
    }
}