package com.example.gestaolistacompras

// Criação do objeto usuario
data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    val listaDeCompras: MutableList<String> = mutableListOf()
)