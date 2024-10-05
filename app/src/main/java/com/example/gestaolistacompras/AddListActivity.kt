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
    private var email: String? = null // Armazenará o e-mail do usuário

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Captura o email da Intent
        email = intent.getStringExtra("email") ?: throw IllegalArgumentException("Usuário não fornecido")

        // Botão de voltar para tela principal
        binding.BackimageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK // Limpa a pilha de atividades
            startActivity(intent)
            finish()
        }

        // Botão para selecionar imagem da galeria
        binding.buttonSelecionarImagem.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

        // Botão para adicionar a lista
        binding.buttonAdicionar.setOnClickListener {
            val nomeLista = binding.editTextNomeLista.text.toString()

            if (nomeLista.isNotBlank()) {
                val novaLista = Lista(
                    id = UUID.randomUUID().toString(),
                    nome = nomeLista,
                    imagemUri = selectedImageUri
                )

                ListaBD.addLista(novaLista) // Adiciona a lista no banco de dados ListaBD

                // Adiciona a lista ao Usuario logado
                val usuario = UsuarioBD.usuariosCadastrados.find { it.email == email }
                usuario?.listaDeCompras?.add(novaLista)

                // Passa a nova lista de volta para a ListActivity
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
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            // Atualiza a ImageView com a imagem selecionada
            binding.imageView.setImageURI(selectedImageUri)
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 0
    }
}