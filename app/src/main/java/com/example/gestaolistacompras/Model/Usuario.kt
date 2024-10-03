package com.example.gestaolistacompras.Model

// Cria um objeto Usuario
data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    val listaDeCompras: MutableList<Lista> = mutableListOf()
)
