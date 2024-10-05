package com.example.gestaolistacompras

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestaolistacompras.Model.Lista
import com.example.gestaolistacompras.Model.ListaBD
import com.example.gestaolistacompras.databinding.ActivityAddListBinding
import java.util.UUID

class AddListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddListBinding
    private var selectedImageUri: Uri? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Salva email que logou
        email = intent.getStringExtra("email") ?: throw IllegalArgumentException("Usuário não fornecido")

        // Botão de voltar para tela principal
        binding.BackimageButton.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_CANCELED, resultIntent)
            finish()
        }

        // Botão para selecionar imagem da galeria
        binding.buttonSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 0)
        }

        // Botão para adicionar a lista
        binding.buttonAdicionar.setOnClickListener {
            val nomeLista = binding.editTextNomeLista.text.toString()

            if (nomeLista.isNotBlank()) {
                val novaLista = Lista(
                    id = UUID.randomUUID().toString(),
                    nome = nomeLista,
                    imagemUri = selectedImageUri?.toString()
                )

                ListaBD.addLista(novaLista)

                // Adiciona a lista ao Usuário logado
                val usuario = UsuarioBD.usuariosCadastrados.find { it.email == email }
                usuario?.listaDeCompras?.add(novaLista)

                // Atualiza a lista para a ListActivity
                val resultIntent = Intent().apply {
                    putExtra("novaLista", novaLista)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, preencha todos os dados.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Salva URI da imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            // Atualiza a ImageView com a imagem selecionada
            binding.imageView.setImageURI(selectedImageUri)
        }
    }
}