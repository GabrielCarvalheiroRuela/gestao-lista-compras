package com.example.gestaolistacompras

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestaolistacompras.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var listAdapter: ListAdapter
    private val listas = mutableListOf<Lista>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o View Binding
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura o RecyclerView
        listAdapter = ListAdapter(listas)
        binding.ListRecyclerView.adapter = listAdapter
        binding.ListRecyclerView.layoutManager = LinearLayoutManager(this)

        // Obtém o email passado pela LoginActivity
        val email = intent.getStringExtra("email")

        // Carrega as listas do usuário correspondente
        email?.let {
            val usuario = UsuarioBD.usuariosCadastrados.find { it.email == email }
            usuario?.listaDeCompras?.let { listas.addAll(it) }
            listAdapter.notifyDataSetChanged() // Atualiza o adapter
        }

        // Redireciona para a tela de adicionar lista
        binding.AddButton.setOnClickListener {
            val intent = Intent(this, AddListActivity::class.java)
            intent.putExtra("email", email) // Passa o email do usuário logado
            startActivity(intent)
        }

        // Redirecionar para a tela de login
        binding.LogoutimageButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Limpa a pilha de atividades
            startActivity(intent)
            finish()
        }
    }

    // Ao carregar a tela esse metódo atualizará a recyclerView mostrando novos itens da lista
    override fun onResume() {
        super.onResume()
        val email = intent.getStringExtra("email")

        // Recarrega as listas do usuário correspondente
        email?.let {
            val usuario = UsuarioBD.usuariosCadastrados.find { it.email == email }
            listas.clear() // Limpa as listas atuais para evitar duplicação
            usuario?.listaDeCompras?.let { listas.addAll(it) }
            listAdapter.notifyDataSetChanged() // Atualiza o adapter
        }
    }
}