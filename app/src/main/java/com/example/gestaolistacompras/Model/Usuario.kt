package com.example.gestaolistacompras

// Cria o objeto Usuario
data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    val listaDeCompras: MutableList<Lista> = mutableListOf()
)
