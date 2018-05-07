package com.gmail.reebrando.myinbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.util.Log
import android.text.TextUtils
import android.content.Intent
import android.view.WindowManager
import com.gmail.reebrando.myinbox.model.Usuario
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "LoginActivity"
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        btn_email_sign_in.setOnClickListener(this)
        btn_email_create_account.setOnClickListener(this)

//        btn_sign_out.setOnClickListener(this)
//        btn_test_message.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        //val currentUser = mAuth!!.currentUser
//        atualizaTela(currentUser)
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        when (i) {
            R.id.btn_email_sign_in -> conectarUsuario(edtEmail.text.toString(), edtPassword.text.toString())
            R.id.btn_email_create_account -> criarConta(edtEmail.text.toString(), edtPassword.text.toString())
//            R.id.btn_sign_out -> desconectarUsuario()
//            R.id.btn_test_message -> abrirApp()
        }
    }

    private fun criarConta(email: String, password: String) {
        Log.e(TAG, "criarConta: " + email)

        if (!validarInformacoes(email, password)) {
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.e(TAG, "criarConta: Sucesso!")

                        // atualiza tela com informações do usuário conectado.
                        val usuario = mAuth!!.currentUser
                        //atualizaTela(usuario)
                        adicionarNovoUsuario(usuario!!.uid, obterUsernamePeloEmail(usuario.email), usuario.email)

                    } else {

                        Log.e(TAG, "criarConta: Falhou!", task.exception)
                        Toast.makeText(applicationContext, "Falha na autenticação!", Toast.LENGTH_SHORT).show()
                        //atualizaTela(null)

                    }
        }
    }

    private fun conectarUsuario(email: String, password: String) {

        Log.e(TAG, "conectarUsuario: " + email)
        if (!validarInformacoes(email, password)) {
            return
        }

        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.e(TAG, "conectarUsuario: Sucesso!")

                        // atualiza tela com informações do usuário conectado
                        val usuario = mAuth!!.currentUser
                        abrirApp()
//                        atualizaTela(usuario)

                    } else {

                        Log.e(TAG, "conectarUsuario: Falhou!", task.exception)
                        Toast.makeText(applicationContext, "Falha na autenticação!", Toast.LENGTH_SHORT).show()
//                        atualizaTela(null)
                    }

//                    if (!task.isSuccessful) {
//                        tvStatus.text = "Falha na autenticação!"
//                    }
                }
    }

    private fun desconectarUsuario() {
        mAuth!!.signOut()
//        atualizaTela(null)
    }

    private fun validarInformacoes(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Favor informar um e-mail válido.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Informe uma senha!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(applicationContext, "Senha muito curta. Deve ter no mínimo 6 caracteres.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

//    private fun atualizaTela(user: FirebaseUser?) {
//        if (user != null) {
//            tvStatus.text = "Email: " + user.email
//            tvDetail.text = "Firebase User ID: " + user.uid
//
//            email_password_buttons.visibility = View.GONE
//            email_password_fields.visibility = View.GONE
//            layout_signed_in_buttons.visibility = View.VISIBLE
//
//        } else {
//
//            tvStatus.text = "Desconectado"
//            tvDetail.text = null
//
//            email_password_buttons.visibility = View.VISIBLE
//            email_password_fields.visibility = View.VISIBLE
//            layout_signed_in_buttons.visibility = View.GONE
//        }
//    }

    private fun adicionarNovoUsuario(id: String, nome: String?, email: String?) {
        val usuario = Usuario(id, nome, email, null)
        FirebaseDatabase.getInstance().reference.child("usuario").child(id).setValue(usuario)
    }

    private fun obterUsernamePeloEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    private fun abrirApp() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
