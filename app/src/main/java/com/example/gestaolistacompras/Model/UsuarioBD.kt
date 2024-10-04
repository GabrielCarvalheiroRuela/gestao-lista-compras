package com.example.gestaolistacompras

import java.util.UUID
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.Model.Usuario
import com.example.gestaolistacompras.Model.Lista

object UsuarioBD {
    // Lista de usuários cadastrados
    val usuariosCadastrados = mutableListOf<Usuario>()

    // Função para adicionar usuário cadastrado na lista
    fun adicionarUsuario(usuario: Usuario) {
        usuariosCadastrados.add(usuario)
    }

    // Função para validação do login
    fun verificarUsuario(email: String, senha: String): Usuario? {
        return usuariosCadastrados.find { it.email == email && it.senha == senha }
    }

    // Função para verificar se já existe esse email cadastrado
    fun emailJaCadastrado(email: String): Boolean {
        return usuariosCadastrados.any { it.email == email }
    }

    // Cria usuários com duas listas, para facilitar testes
    init {
        // Usuário admin
        val admin = Usuario("admin", "admin", "admin")
        // Usuário Gab
        val gab = Usuario("gab", "gab", "gab")

        // Listas admin
        val adminLista1 = Lista(UUID.randomUUID().toString(), "Lista do Admin 1", null, mutableListOf())
        val adminLista2 = Lista(UUID.randomUUID().toString(), "Lista do Admin 2", null, mutableListOf())

        admin.listaDeCompras.addAll(listOf(adminLista1, adminLista2))
        usuariosCadastrados.add(admin)



        // Listas gab
        val gabLista1 = Lista(UUID.randomUUID().toString(), "Lista do Gab 1", null, mutableListOf())
        val gabLista2 = Lista(UUID.randomUUID().toString(), "Lista do Gab 2", null, mutableListOf())

        gab.listaDeCompras.addAll(listOf(gabLista1, gabLista2))
        usuariosCadastrados.add(gab)
    }
}