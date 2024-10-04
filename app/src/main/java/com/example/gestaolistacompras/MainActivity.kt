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
    private var itemList = mutableListOf<Item>()

    // código de requisição para adicionar item
    private val REQUEST_CODE_ADD_ITEM = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pega Id da lista clicada para filtrar itens corretos
        val listaId = intent.getStringExtra("lista_id")
        val email = intent.getStringExtra("email")

        // Carrega os itens da lista clicada
        listaId?.let {
            itemList = ItemBD.getItensDaLista(it)
        }

        // Configuração RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(itemList)
        binding.recyclerView.adapter = itemAdapter

        // Botão de adicionar
        binding.AddButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("lista_id", listaId) // Passar também o listaId
            startActivityForResult(intent, REQUEST_CODE_ADD_ITEM)
        }

        // Ação do botão de voltar para a tela de lista
        binding.BackimageButton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("email", email)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    // Método executa quando a tela é carregada novamente
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            val newItem = data?.getSerializableExtra("newItem") as? Item
            val listaId = intent.getStringExtra("lista_id")

            newItem?.let {
                // Adicionar item na lista correta
                listaId?.let { id ->
                    ItemBD.addItemNaLista(id, it)
                }

                // Atualizar a lista e a RecyclerView
                itemList.add(it)
                itemAdapter.notifyDataSetChanged()
            }
        }
    }
}