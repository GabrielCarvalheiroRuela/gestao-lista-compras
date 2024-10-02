package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestaolistacompras.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ação do botão de login
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val senha = binding.passwordEditText.text.toString()

            // Verifica se há campos vazios
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica o usuário na 'base de dados'
            val usuario = UsuarioBD.verificarUsuario(email, senha)

            if (usuario != null) {
                Toast.makeText(this, "Login realizado com sucesso ${usuario.nome}", Toast.LENGTH_SHORT).show()
                // Redireciona para a tela principal
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação do botão para ir ao cadastro
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}