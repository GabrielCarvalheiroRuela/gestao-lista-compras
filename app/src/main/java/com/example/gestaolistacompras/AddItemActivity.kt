package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private var selectedUnit: String? = null
    private var selectedCategory: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // pega o email do user
        email = intent.getStringExtra("email")

        if (email == null) {
            AlertDialog.Builder(this)
                .setTitle("Erro")
                .setMessage("Usuário não fornecido.")
                .setPositiveButton("OK") { _, _ -> finish() }
                .show()
            return
        }

        setupUnitButton()
        setupCategoryButton()

        // Botão para adicionar item
        binding.buttonAdd.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val quantity = binding.editTextQuantity.text.toString().toIntOrNull()
            val unit = selectedUnit
            val category = selectedCategory

            if (name.isNotEmpty() && quantity != null && unit != null && category != null) {
                val imageResId = getImageForCategory(category)

                // Cria o novo item
                val newItem = Item(imageResId, name, quantity, unit, category, false)

                // Retorna se foi adicionado
                val intent = Intent()
                intent.putExtra("newItem", newItem)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                // Exibe uma mensagem de erro se os campos não forem preenchidos corretamente
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Preencha todos os campos corretamente.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Botão para voltar para a tela principal
        binding.BackimageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK // Limpa a pilha de atividades
            startActivity(intent)
            finish()
        }
    }

    // Mini tela de seleção de unidade de contagem
    private fun setupUnitButton() {
        val units = arrayOf("Unidades", "KG", "g", "Litros")
        binding.buttonUnit.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Escolha a Unidade")
                .setItems(units) { _, which ->
                    selectedUnit = units[which]
                    binding.buttonUnit.text = selectedUnit
                }
                .show()
        }
    }

    // Mini tela de seleção de categoria
    private fun setupCategoryButton() {
        val categories = arrayOf("Carnes", "Vegetais", "Frutas", "Outros")
        binding.buttonCategory.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Escolha a Categoria")
                .setItems(categories) { _, which ->
                    selectedCategory = categories[which]
                    binding.buttonCategory.text = selectedCategory
                }
                .show()
        }
    }

    // Função para pegar a imagem de acordo com a categoria do item
    private fun getImageForCategory(category: String): Int {
        return when (category) {
            "Carnes" -> R.drawable.carnes
            "Vegetais" -> R.drawable.vegetais
            "Frutas" -> R.drawable.frutas
            else -> R.drawable.outros
        }
    }
}