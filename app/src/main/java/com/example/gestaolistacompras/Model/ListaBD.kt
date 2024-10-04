package com.example.gestaolistacompras.Model

object ListaBD {
    private val listasCadastradas = mutableListOf<Lista>()

    // Adiciona uma nova lista
    fun addLista(lista: Lista) {
        listasCadastradas.add(lista)
    }

    // Remove uma lista por ID
    fun removeLista(listaId: String) {
        listasCadastradas.removeAll { it.id == listaId }
    }

    // Retorna todas as listas cadastradas
    fun getAllListas(): List<Lista> {
        return listasCadastradas
    }

    // Busca uma lista por ID
    fun getListaById(listaId: String): Lista? {
        return listasCadastradas.find { it.id == listaId }
    }

    // Atualiza uma lista existente
    fun updateLista(lista: Lista) {
        val index = listasCadastradas.indexOfFirst { it.id == lista.id }
        if (index >= 0) {
            listasCadastradas[index] = lista
        }
    }

    // Filtra listas por um termo de busca (usado no searchView)
    fun filterListas(query: String): List<Lista> {
        return if (query.isEmpty()) {
            listasCadastradas
        } else {
            listasCadastradas.filter { it.nome.lowercase().contains(query.lowercase()) }
        }
    }
}
