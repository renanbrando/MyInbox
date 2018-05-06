package com.gmail.reebrando.myinbox.services

import com.gmail.reebrando.myinbox.model.Usuario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Rodrigo on 05/05/2018.
 */
class UsuarioService {
    private var mDB: DatabaseReference? = null
    private var mMensagemReferencia: DatabaseReference? = null

    fun DispositivoService(){
        instanciaDB()
    }

    fun instanciaDB(){
        mDB = FirebaseDatabase.getInstance().reference
        mMensagemReferencia = FirebaseDatabase.getInstance().getReference("message")
    }

//    fun buscarPorId(id: Number): List<Usuario>? {
//        return null
//    }
//
//    fun salvar(usuario: Usuario) {
//
//        var registro = buscarPorId(usuario.id)
//
//        if (registro == null) {
//
//            // Push permite criar ID aleatório (usual no insert).
//            mDB?.child("usuario")?.push()?.setValue(usuario)
//
//        }
//        else {
//
//            // Update
//            mDB?.child("usuario")?.setValue(usuario)
//
//        }
//    }
//
//    fun excluir(usuario: Usuario){
//        mDB?.child("usuario")?.setValue(null)
//    }
}