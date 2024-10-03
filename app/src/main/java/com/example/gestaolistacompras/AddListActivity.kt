package com.example.gestaolistacompras

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
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

        // Botao para selecionar imagem da galeria
        binding.buttonSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 0)
        }

        // Botao para adicionar a lista
        binding.buttonAdicionar.setOnClickListener {
            val nomeLista = binding.editTextNomeLista.text.toString()

            if (nomeLista.isNotBlank()) {
                val novaLista = Lista(nomeLista, selectedImageUri) // Cria um novo objeto Lista
                usuario.listaDeCompras.add(novaLista) // Adiciona a nova lista

                // Define o resultado da Activity
                Toast.makeText(this, "Lista adicionada!", Toast.LENGTH_SHORT).show()
                finish() // Finaliza a activity
            } else {
                Toast.makeText(this, "Por favor, preencha todos os dados.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para salvar a imagem e salvar sua uri
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            selectedImageUri = data.data
            binding.imageView.setImageURI(selectedImageUri) // Atualiza a ImageView com a imagem selecionada
        }
    }
}