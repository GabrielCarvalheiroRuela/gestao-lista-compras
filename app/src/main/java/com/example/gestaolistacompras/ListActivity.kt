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

    // Tipo de request
    private val REQUEST_CODE_ADD_LIST = 1
    private val REQUEST_CODE_MAIN_ACTIVITY = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o adapter do RecyclerView
        listAdapter = ListAdapter(listasFiltradas) { lista ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lista_id", lista.id)
            intent.putExtra("email", email)
            startActivityForResult(intent, REQUEST_CODE_MAIN_ACTIVITY)
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

    // Configura o campo de busca para filtrar as listas
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

    // Filtra as listas de acordo com o texto inserido
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

    // Carrega as listas do usuário logado
    private fun loadUserLists() {
        email?.let { userEmail ->
            // Busca o usuário na base de dados usando o email
            val usuario = UsuarioBD.usuariosCadastrados.find { it.email == userEmail }

            if (usuario != null) {
                // Limpa as listas anteriores para evitar duplicações
                listas.clear()
                listasFiltradas.clear()

                // Adiciona as listas do usuário
                listas.addAll(usuario.listaDeCompras)

                // Atualiza as listas filtradas
                listasFiltradas.addAll(listas)

                // Notifica o adapter sobre mudanças
                listAdapter.notifyDataSetChanged()
            }
        }
    }

    // Método que executa quando volta para tela
    override fun onResume() {
        super.onResume()
        loadUserLists()
    }

    // Lida com o resultado da atividade de adicionar lista
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_LIST && resultCode == Activity.RESULT_OK) {
            // A lista será carregada novamente em onResume através de loadUserLists()
            loadUserLists()
        }
    }
}
