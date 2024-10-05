package com.example.gestaolistacompras.Model

import android.os.Parcel
import android.os.Parcelable

data class Lista(
    val id: String,
    val nome: String,
    val imagemUri: String?,
    val listaDeItens: MutableList<Item> = mutableListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        mutableListOf<Item>().apply {
            parcel.readList(this, Item::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nome)
        parcel.writeString(imagemUri)
        parcel.writeList(listaDeItens)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lista> {
        override fun createFromParcel(parcel: Parcel): Lista {
            return Lista(parcel)
        }

        override fun newArray(size: Int): Array<Lista?> {
            return arrayOfNulls(size)
        }
    }
}