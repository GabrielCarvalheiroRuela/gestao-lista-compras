package com.example.gestaolistacompras

import java.util.UUID
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.Model.Usuario
import com.example.gestaolistacompras.Model.Lista

object UsuarioBD {
    // Lista de usuários cadastrados
    public val usuariosCadastrados = mutableListOf<Usuario>()

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

    // Cria um usuários para facilitar testes
    init {
        val admin = Usuario("admin", "admin", "admin")
        val adminLista = Lista(UUID.randomUUID().toString(), "Lista do Admin", null)

        // Adiciona 3 itens diferentes à lista do admin
        adminLista.listaDeItens.add(Item(1, "Arroz", 2, "kg", "Grãos", false))
        adminLista.listaDeItens.add(Item(2, "Feijão", 1, "kg", "Grãos", false))
        adminLista.listaDeItens.add(Item(3, "Frango", 1, "kg", "Carnes", false))

        admin.listaDeCompras.add(adminLista)
        usuariosCadastrados.add(admin)

        val gab = Usuario("gab", "gab", "gab")
        val gabLista = Lista(UUID.randomUUID().toString(), "Lista do Gab", null)

        // Adiciona 3 itens diferentes à lista do gab
        gabLista.listaDeItens.add(Item(4, "Tomate", 3, "unidades", "Vegetais", false))
        gabLista.listaDeItens.add(Item(5, "Banana", 6, "unidades", "Frutas", false))
        gabLista.listaDeItens.add(Item(6, "Leite", 2, "Litros", "Laticínios", false))

        gab.listaDeCompras.add(gabLista)
        usuariosCadastrados.add(gab)
    }
}