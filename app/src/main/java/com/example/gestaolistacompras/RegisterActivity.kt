// Gabriel Carvalheiro Ruela - 837871
package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestaolistacompras.Model.Usuario
import com.example.gestaolistacompras.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ação do botão de cadastro
        binding.registerButton.setOnClickListener {
            val nome = binding.nomeEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val senha = binding.passwordEditText.text.toString().trim()
            val confirmarSenha = binding.passwordConfirmEditText.text.toString().trim()

            // Verifica campos vazios
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se o formato do e-mail é válido
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Por favor, insira um e-mail válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se as senhas coincidem
            if (senha != confirmarSenha) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se o e-mail já está cadastrado
            if (UsuarioBD.emailJaCadastrado(email)) {
                Toast.makeText(this, "Este e-mail já está cadastrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cadastra o usuário
            val novoUsuario = Usuario(nome, email, senha)
            UsuarioBD.adicionarUsuario(novoUsuario)

            Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()

            // Redireciona para a tela de login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Ação para ir à tela de Login
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Função para verificar se o e-mail é válido
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
