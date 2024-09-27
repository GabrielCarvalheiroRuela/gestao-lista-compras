package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.Model.ItemBD
import com.example.gestaolistacompras.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private var selectedUnit: String? = null
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUnitButton()
        setupCategoryButton()

        // Ação do botão de adicionar item
        binding.buttonAdd.setOnClickListener {
            addItem()
        }

        // Ação do botão de voltar para a tela principal
        binding.BackimageButton.setOnClickListener {
            // Aqui você apenas volta para a MainActivity sem passar um resultado
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Remove startActivityForResult
            finish() // Encerra a AddItemActivity
        }
    }

    // Função de seleção do tipo de unidade na tela de adicionar item
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

    // Função de seleção da categoria na tela de adicionar item
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

    // Função para adicionar item ao clicar no botão
    private fun addItem() {
        val name = binding.editTextName.text.toString()
        val quantity = binding.editTextQuantity.text.toString().toIntOrNull()
        val unit = selectedUnit
        val category = selectedCategory

        if (name.isNotEmpty() && quantity != null && unit != null && category != null) {
            val imageResId = getImageForCategory(category)

            // Cria o novo item
            val newItem = Item(imageResId, name, quantity, unit, category, false)

            // Retorna o resultado para a MainActivity
            val intent = Intent()
            intent.putExtra("newItem", newItem)
            setResult(RESULT_OK, intent) // Mantém o retorno do resultado
            finish() // Encerra a AddItemActivity
        } else {
            // Exibe uma mensagem de erro se os campos não forem preenchidos corretamente
            AlertDialog.Builder(this)
                .setTitle("Erro")
                .setMessage("Preencha todos os campos corretamente.")
                .setPositiveButton("OK", null)
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
