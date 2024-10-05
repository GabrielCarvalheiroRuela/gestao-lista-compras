// Gabriel Carvalheiro Ruela - 837871
package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.example.gestaolistacompras.Adapter.ItemAdapter
import com.example.gestaolistacompras.Model.Item
import com.example.gestaolistacompras.Model.ItemBD
import com.example.gestaolistacompras.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private var itemList = mutableListOf<Item>()
    private val filteredItemList = mutableListOf<Item>() // Lista para itens filtrados

    // Código de requisição para adicionar item
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
            // Inicializa a lista filtrada
            filteredItemList.addAll(itemList)
        }

        // Configuração RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(filteredItemList)
        binding.recyclerView.adapter = itemAdapter

        // Configura o campo de busca
        setupSearchView()

        // Botão de adicionar
        binding.AddButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("lista_id", listaId)
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

    // Configura o campo de busca
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText?.lowercase() ?: "")
                return true
            }
        })
    }

    // Filtra os itens de acordo com o texto inserido
    private fun filterItems(query: String) {
        filteredItemList.clear()
        if (query.isEmpty()) {
            // Se tiver busca mostra todos os itens
            filteredItemList.addAll(itemList)
        } else {
            itemList.forEach { item ->
                if (item.name.lowercase().contains(query)) {
                    filteredItemList.add(item)
                }
            }
        }
        itemAdapter.notifyDataSetChanged() // Atualiza a RecyclerView
    }

    // Executa quando carrega a tela
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_ITEM) {
            if (resultCode == RESULT_OK) {
                val newItem = data?.getSerializableExtra("newItem") as? Item
                val listaId = intent.getStringExtra("lista_id")

                newItem?.let {
                    // Adicionar item na lista correta do usuario logado
                    listaId?.let { id ->
                        ItemBD.addItemNaLista(id, it)
                    }

                    // atualiza a lista e a RecyclerView
                    itemList.add(it)
                    filterItems("")
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Recarrega os itens da lista
                val listaId = intent.getStringExtra("lista_id")
                listaId?.let {
                    itemList = ItemBD.getItensDaLista(it)
                    filteredItemList.clear()
                    filteredItemList.addAll(itemList)
                    // Atualiza a RecyclerView
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}