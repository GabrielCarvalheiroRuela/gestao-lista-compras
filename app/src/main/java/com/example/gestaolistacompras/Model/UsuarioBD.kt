package com.example.gestaolistacompras

object UsuarioBD {
    // Lista de usuários cadastrados
    private val usuariosCadastrados = mutableListOf<Usuario>()

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

    // Cria um usuário admin para facilitar testes
    init {
        usuariosCadastrados.add(Usuario("Admin", "admin@gmail.com", "admin"))
    }
}