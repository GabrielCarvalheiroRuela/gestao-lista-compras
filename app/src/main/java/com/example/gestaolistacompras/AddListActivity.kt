package com.example.gestaolistacompras

import android.app.Activity
import com.example.gestaolistacompras.Model.Usuario
import com.example.gestaolistacompras.Model.Lista
import android.content.Intent
import java.util.UUID
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.gestaolistacompras.MainActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.gestaolistacompras.databinding.ActivityAddListBinding

class AddListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddListBinding
    private var selectedImageUri: Uri? = null
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuario = intent.getSerializableExtra("usuario") as? Usuario ?: throw IllegalArgumentException("Usuário não fornecido")

        // Botão de voltar para tela principal
        binding.BackimageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK // Limpa a pilha de atividades
            startActivity(intent)
            finish()
        }

        // Botao para selecionar imagem da galeria
        binding.buttonSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 0)
        }

        // Botão para adicionar a lista
        binding.buttonAdicionar.setOnClickListener {
            val nomeLista = binding.editTextNomeLista.text.toString()

            if (nomeLista.isNotBlank()) {
                // Gera um ID único e define outros dados
                val novaLista = Lista(
                    id = UUID.randomUUID().toString(),
                    nome = nomeLista,
                    imagemUri = selectedImageUri
                )
                usuario.listaDeCompras.add(novaLista) // Adiciona a nova lista

                Toast.makeText(this, "Lista adicionada!", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os dados.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Salva uri da imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.imageView.setImageURI(selectedImageUri) // Atualiza a ImageView com a imagem selecionada
        }
    }
}