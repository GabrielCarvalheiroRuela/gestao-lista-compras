package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestaolistacompras.Adapter.ItemAdapter
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.Model.ItemBD
import com.example.gestaolistacompras.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private val itemList = ItemBD.getItemList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(itemList)
        binding.recyclerView.adapter = itemAdapter

        // Ação do botão de voltar para tela principal
        binding.BackimageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_ITEM)
        }

        // Ação do botão de logout
        binding.LogoutimageButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() // Remove todos os dados salvos
            editor.apply()

            // Redirecionar para a tela de login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpa a pilha de atividades
            startActivity(intent)
            finish()
        }

        // Botão de adicionar
        binding.AddButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_ITEM)
        }
    }

    // Método chamado quando a AddItemActivity retornar um resultado
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            val newItem = data?.getSerializableExtra("newItem") as? Item
            newItem?.let {
                itemList.add(it)
                itemAdapter.notifyDataSetChanged() // Atualiza o RecyclerView
            }
        }
    }

    // Código da requisição para adicionar um item
    companion object {
        const val REQUEST_CODE_ADD_ITEM = 1
    }
}