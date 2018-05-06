package com.gmail.reebrando.myinbox.services

import com.gmail.reebrando.myinbox.model.Caixa
import com.gmail.reebrando.myinbox.model.Dispositivo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import java.util.*

/**
 * Created by Rodrigo on 05/05/2018.
 */
class DispositivoService {

    private var mDB: DatabaseReference? = null
    private var mMensagemReferencia: DatabaseReference? = null

    fun DispositivoService(){
        instanciaDB()
    }

    fun instanciaDB(){
        mDB = FirebaseDatabase.getInstance().reference
        mMensagemReferencia = FirebaseDatabase.getInstance().getReference("message")
    }

//    fun buscarPorId(id: Number): List<Dispositivo>? {
//        return null
//    }
//
//    fun salvar(dispositivo: Dispositivo) {
//
//        var registro = buscarPorId(dispositivo.id)
//
//        if (registro == null) {
//
//            // Push permite criar ID aleatório (usual no insert).
//            mDB?.child("dispositivo")?.push()?.setValue(dispositivo)
//
//        }
//        else {
//
//            // Update
//            mDB?.child("dispositivo")?.setValue(dispositivo)
//
//        }
////        mDatabase!!.child("title").setValue("JavaSampleApproach")
////
////        val user = User(username, email)
////        mDatabase!!.child("users").child(userId).setValue(user)
////
////        val message = Message(author, body, time)
////        mMessageReference!!.setValue(message)
//    }
//
//    fun excluir(dispositivo: Dispositivo){
//        mDB?.child("dispositivo")?.setValue(null)
//    }

}