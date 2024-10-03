package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestaolistacompras.Model.Usuario
import com.example.gestaolistacompras.Model.Lista
import com.example.gestaolistacompras.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var listAdapter: ListAdapter
    private val listas = mutableListOf<Lista>()
    private var email: String? = null

    // Tipo de request
    private val REQUEST_CODE_ADD_LIST = 1
    private val REQUEST_CODE_MAIN_ACTIVITY = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o adapter do RecyclerView
        listAdapter = ListAdapter(listas) { lista ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lista_id", lista.id)
            intent.putExtra("email", email)
            startActivityForResult(intent, REQUEST_CODE_MAIN_ACTIVITY)
        }
        binding.ListRecyclerView.adapter = listAdapter
        binding.ListRecyclerView.layoutManager = LinearLayoutManager(this)

        email = intent.getStringExtra("email")

        // Chama função para carregar lista do usuário
        loadUserLists()

        // Botão de adicionar lista
        binding.AddButton.setOnClickListener {
            val intent = Intent(this, AddListActivity::class.java)
            intent.putExtra("email", email)
            startActivityForResult(intent, REQUEST_CODE_ADD_LIST) // Mantenha o finish aqui, se necessário
        }

        // Botão para sair do sistema
        binding.LogoutimageButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    // Carrega as listas do usuário
    private fun loadUserLists() {
        email?.let { userEmail ->
            val usuario = UsuarioBD.usuariosCadastrados.find { it.email == userEmail }
            if (usuario != null) {
                listas.clear()
                listas.addAll(usuario.listaDeCompras)
                listAdapter.notifyDataSetChanged()
            }
        }
    }

    // Método que executa quando volta para tela
    override fun onResume() {
        super.onResume()
        // Carrega as listas do usuário
        loadUserLists()
    }

    // Quando usuario voltar vai carregar as listas novamente
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MAIN_ACTIVITY && resultCode == RESULT_OK) {
            val updatedList = data?.getSerializableExtra("updatedList") as? List<Lista> // Alterar para Lista
            updatedList?.let {
                // Obtém o usuário logado novamente
                val usuario = UsuarioBD.usuariosCadastrados.find { it.email == email }
                usuario?.listaDeCompras?.addAll(it)
                loadUserLists()
            }
        }
    }
}