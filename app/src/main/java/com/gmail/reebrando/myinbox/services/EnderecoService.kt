package com.gmail.reebrando.myinbox.services

import com.gmail.reebrando.myinbox.model.Endereco
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Rodrigo on 05/05/2018.
 */
class EnderecoService {

    private var mDB: DatabaseReference? = null
    private var mMensagemReferencia: DatabaseReference? = null

    fun DispositivoService(){
        instanciaDB()
    }

    fun instanciaDB(){
        mDB = FirebaseDatabase.getInstance().reference
        mMensagemReferencia = FirebaseDatabase.getInstance().getReference("message")
    }

//    fun buscarPorId(id: Number): List<Endereco>? {
//        return null
//    }
//
//    fun salvar(endereco: Endereco) {
//
//        var registro = buscarPorId(endereco.id)
//
//        if (registro == null) {
//
//            // Push permite criar ID aleatório (usual no insert).
//            mDB?.child("endereco")?.push()?.setValue(endereco)
//
//        }
//        else {
//
//            // Update
//            mDB?.child("endereco")?.setValue(endereco)
//
//        }
//    }
//
//    fun excluir(endereco: Endereco){
//        mDB?.child("endereco")?.setValue(null)
//    }
}