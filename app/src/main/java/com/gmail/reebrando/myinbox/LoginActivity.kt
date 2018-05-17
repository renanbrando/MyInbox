package com.gmail.reebrando.myinbox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerButtton: Button
    private lateinit var forgotLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email = findViewById(R.id.email) as EditText
        password = findViewById(R.id.password) as EditText
        loginButton = findViewById(R.id.loginButton) as Button
        registerButtton = findViewById(R.id.registerButton) as Button
        forgotLink = findViewById(R.id.forgotLink) as TextView

        registerButtton.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{
            auth = FirebaseAuth.getInstance()
            if (validate()) {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->
                    val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intentToMain)
                }
            }

        }

        forgotLink.setOnClickListener(){
            val emailText = email.text.toString()
            auth = FirebaseAuth.getInstance()

            if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                email.error = getString(R.string.valid_address)
            } else {
                auth!!.sendPasswordResetEmail(emailText)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                toast("Check email to reset your password!")
                            } else {
                                toast("Failed to send reset password email!")
                            }
                        }
            }
        }
    }

    private fun validate(): Boolean {
        var valid = true

        val emailText = email.text.toString()
        val passwordText = password.text.toString()

        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.error = getString(R.string.valid_address)
            valid = false
        } else {
            email.error = null
        }

        if (passwordText.isEmpty() || passwordText.length < 4 || passwordText.length > 10) {
            password.error = getString(R.string.between_chars)
            valid = false
        } else {
            password.error = null
        }

        return valid
    }
}
