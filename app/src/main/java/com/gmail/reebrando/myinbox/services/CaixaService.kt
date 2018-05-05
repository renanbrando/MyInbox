package com.gmail.reebrando.myinbox.services

/**
 * Created by Rodrigo on 05/05/2018.
 */
import com.gmail.reebrando.myinbox.model.Caixa
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter

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

    fun buscar() : List<Caixa>? {

//        raiz.child("usuarios/8").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                Utilizador user = dataSnapshot.getValue(Utilizador.class);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                //Se ocorrer um erro}
//            });

        return null
    }

    fun buscarPorId(dataChegada: Date?): List<Caixa>? {
        return null
    }

    fun salvar(caixa: Caixa) {

        var correspondencia = buscarPorId(caixa.dataChegada)

        if (correspondencia == null) {

//            val current = LocalDateTime.now()
//
//            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
//            val formatted = current.format(formatter)
//
//            caixa.dataChegada = formatted

            // Push permite criar ID aleatório (usual no insert).
            mDB?.child("caixa")?.push()?.setValue(caixa)

        }
        else {

            // Update
            mDB?.child("caixa")?.setValue(caixa)

        }
//        mDatabase!!.child("title").setValue("JavaSampleApproach")
//
//        val user = User(username, email)
//        mDatabase!!.child("users").child(userId).setValue(user)
//
//        val message = Message(author, body, time)
//        mMessageReference!!.setValue(message)
    }

    fun excluir(id: String){

    }

    fun excluirTodos(){

    }
}