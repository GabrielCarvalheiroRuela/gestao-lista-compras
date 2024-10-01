package com.example.gestaolistacompras

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestaolistacompras.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.passwordConfirmEditText.text.toString().trim()

            // Verifica campos vazios
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) { // Verifica se as senhas são iguais
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            } else if (sharedPreferences.contains(email)) { // Verifica se o e-mail já está cadastrado
                Toast.makeText(this, "Este e-mail já está cadastrado", Toast.LENGTH_SHORT).show()
            } else {
                // Cadastra o usuário se todas as verificações passarem
                with(sharedPreferences.edit()) {
                    putString(email, password)
                    apply()
                }

                Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()

                // Redireciona para a tela de login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Ação para ir à tela de Login
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}