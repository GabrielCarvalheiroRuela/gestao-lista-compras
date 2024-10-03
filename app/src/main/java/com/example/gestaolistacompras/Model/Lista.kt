package com.example.gestaolistacompras

import android.net.Uri


// Cria objeto Lista
data class Lista(
    val nome: String,
    val imagemUri: Uri?
)