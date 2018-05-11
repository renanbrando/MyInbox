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
import com.gmail.reebrando.myinbox.Model.Usuario
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "LoginActivity"
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)

        edtConfirmPassword.visibility = View.GONE

        btnEmailSignIn.setOnClickListener(this)
        btnEmailCreateAccount.setOnClickListener(this)

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
            R.id.btnEmailSignIn -> conectarUsuario(edtEmail.text.toString(), edtPassword.text.toString())
            R.id.btnEmailCreateAccount -> {
                if (edtConfirmPassword.visibility == View.GONE) {
                    edtConfirmPassword.visibility = View.VISIBLE
                    return
                }
                criarConta(edtEmail.text.toString(), edtPassword.text.toString(), edtConfirmPassword.text.toString())
            }
        }
    }

    private fun criarConta(email: String, password: String, checkPassword: String) {
        Log.e(TAG, "criarConta: " + email)

        if (!validarInformacoes(email, password, checkPassword, true)) {
            return
        }

        desabilitarBotoes()

        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.e(TAG, "criarConta: Sucesso!")

                        val usuario = mAuth!!.currentUser
                        adicionarNovoUsuario(usuario!!.uid, obterUsernamePeloEmail(usuario.email), usuario.email, password)
                        Toast.makeText(applicationContext, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                        abrirApp()

                    } else {

                        Log.e(TAG, "criarConta: Falhou!", task.exception)

                        var message = task.exception.toString()

                        if (message.contains("already")) message = "E-mail já cadastrado! Informe outro..."

                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

                        habilitarBotoes()
                    }
        }
    }

    private fun conectarUsuario(email: String, password: String) {

        Log.e(TAG, "conectarUsuario: " + email)
        if (!validarInformacoes(email, password, "", false)) {
            return
        }

        desabilitarBotoes()

        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        Log.e(TAG, "conectarUsuario: Sucesso!")

                        // atualiza tela com informações do usuário conectado
//                        val usuario = mAuth!!.currentUser
                        abrirApp()

                    } else {

                        habilitarBotoes()

                        Log.e(TAG, "conectarUsuario: Falhou!", task.exception)
                        Toast.makeText(applicationContext, "Falha na autenticação!", Toast.LENGTH_SHORT).show()
//                        atualizaTela(null)
                    }

//                    if (!task.isSuccessful) {
//                        tvStatus.text = "Falha na autenticação!"
//                    }
                }
    }

    private fun validarInformacoes(email: String, password: String, confirmPassword: String, criacaoUsuario: Boolean): Boolean {
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

        if (criacaoUsuario) {

            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(applicationContext, "Confirme a senha!", Toast.LENGTH_SHORT).show()
                return false
            }

            if (!TextUtils.equals(password, confirmPassword)) {
                Toast.makeText(applicationContext, "As senhas informadas não conferem.", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }

    private fun adicionarNovoUsuario(id: String, nome: String?, email: String?, senha: String) {
        val usuario = Usuario(id, nome, email, senha)
        FirebaseDatabase.getInstance().reference.child("usuario").child(id).setValue(usuario)
    }

    private fun obterUsernamePeloEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    private fun desabilitarBotoes(){
        btnEmailSignIn.isEnabled = false
        btnEmailSignIn.setBackgroundResource(R.drawable.rounded_button_disabled)
        btnEmailCreateAccount.isEnabled = false
        btnEmailCreateAccount.setBackgroundResource(R.drawable.rounded_button_disabled)
    }

    private fun habilitarBotoes(){
        btnEmailSignIn.isEnabled = true
        btnEmailSignIn.setBackgroundResource(R.drawable.rounded_button)
        btnEmailCreateAccount.isEnabled = true
        btnEmailCreateAccount.setBackgroundResource(R.drawable.rounded_button)
    }

    private fun abrirApp() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
