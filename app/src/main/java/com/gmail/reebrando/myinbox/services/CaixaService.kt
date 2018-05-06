package com.gmail.reebrando.myinbox.services

/**
 * Created by Rodrigo on 05/05/2018.
 */

import com.gmail.reebrando.myinbox.model.Caixa
import com.google.firebase.database.*
import java.util.*

class CaixaService {

    private var mDB: DatabaseReference? = null
    private var mMensagemReferencia: DatabaseReference? = null

    fun CaixaService(){
        instanciaDB()
    }

    fun instanciaDB(){
        mDB = FirebaseDatabase.getInstance().reference
        mMensagemReferencia = FirebaseDatabase.getInstance().getReference("message")
    }

//    fun buscar() : List<Caixa>? {
//
//        val resultadoListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                if (dataSnapshot.exists()) {
//                    val data = dataSnapshot.child("caixa")
//                    //Log.e(TAG, "onDataChange: Message data is updated: " + message!!.author + ", " + message.time + ", " + message.body)
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Failed to read value
//                //Log.e(TAG, "onCancelled: Failed to read message")
//
////                tvAuthor.text = ""
////                tvTime.text = ""
////                tvBody.text = "onCancelled: Failed to read message!"
//            }
//        }
//
//        mMensagemReferencia!!.addValueEventListener(resultadoListener)
//
////        raiz.child("usuarios/8").addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot)
////            {
////                Utilizador user = dataSnapshot.getValue(Utilizador.class);
////            }
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////                //Se ocorrer um erro}
////            });
//
//        return null
//    }
//
//    fun buscarPorData(dataChegada: Date?): List<Caixa>? {
//        return null
//    }
//
//    fun salvar(caixa: Caixa) {
//
//        var registro = buscarPorData(caixa.dataChegada)
//
//        if (registro == null) {
//
////            var ts = ServerValue.TIMESTAMP
//
//            caixa.quantidade = 1
////            val current = LocalDateTime.now()
////            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
////            val formatted = current.format(formatter)
////            caixa.dataChegada = formatted
//
//            // Push permite criar ID aleatório (usual no insert).
//            mDB?.child("caixa")?.push()?.setValue(caixa)
//
//        }
//        else {
//
//            // Update
//            mDB?.child("caixa")?.setValue(caixa)
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
//    fun excluir(caixa: Caixa){
//        mDB?.child("caixa")?.setValue(null)
//    }

}