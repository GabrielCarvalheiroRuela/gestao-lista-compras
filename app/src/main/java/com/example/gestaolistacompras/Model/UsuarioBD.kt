package com.example.gestaolistacompras

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
        admin.listaDeCompras.add(Lista("admin", null))
        usuariosCadastrados.add(admin)

        val gab = Usuario("gab", "gab", "gab")
        gab.listaDeCompras.add(Lista("gab", null))
        usuariosCadastrados.add(gab)
    }
}