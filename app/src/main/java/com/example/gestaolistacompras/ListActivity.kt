package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.example.gestaolistacompras.Model.Lista
import com.example.gestaolistacompras.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var listAdapter: ListAdapter
    private val listas = mutableListOf<Lista>()
    private val listasFiltradas = mutableListOf<Lista>()
    private var email: String? = null

    private val REQUEST_CODE_ADD_LIST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o adapter do RecyclerView
        listAdapter = ListAdapter(listasFiltradas) { lista ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lista_id", lista.id)
            intent.putExtra("email", email)
            startActivityForResult(intent, REQUEST_CODE_ADD_LIST)
        }
        binding.ListRecyclerView.adapter = listAdapter
        binding.ListRecyclerView.layoutManager = LinearLayoutManager(this)

        // Captura o email do usuário logado
        email = intent.getStringExtra("email")

        // Carrega as listas do usuário
        loadUserLists()

        // Configura o campo de busca
        setupSearchView()

        // Botão de adicionar lista
        binding.AddButton.setOnClickListener {
            val intent = Intent(this, AddListActivity::class.java)
            intent.putExtra("email", email)
            startActivityForResult(intent, REQUEST_CODE_ADD_LIST)
        }

        // Botão para sair do sistema
        binding.LogoutimageButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterLists(newText?.lowercase() ?: "")
                return true
            }
        })
    }

    private fun filterLists(query: String) {
        listasFiltradas.clear()
        if (query.isEmpty()) {
            listasFiltradas.addAll(listas)
        } else {
            listas.forEach { lista ->
                if (lista.nome.lowercase().contains(query)) {
                    listasFiltradas.add(lista)
                }
            }
        }
        listAdapter.notifyDataSetChanged()
    }

    private fun loadUserLists() {
        email?.let { userEmail ->
            val usuario = UsuarioBD.usuariosCadastrados.find { it.email == userEmail }

            if (usuario != null) {
                listas.clear()
                listasFiltradas.clear()

                listas.addAll(usuario.listaDeCompras)
                listasFiltradas.addAll(listas)

                listAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserLists()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_LIST && resultCode == Activity.RESULT_OK) {
            loadUserLists()
        }
    }
}
