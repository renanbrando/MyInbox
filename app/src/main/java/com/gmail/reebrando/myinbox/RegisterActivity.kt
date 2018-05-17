package com.gmail.reebrando.myinbox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.gmail.reebrando.myinbox.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var displayName: EditText
    private lateinit var status: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var cPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var backLoginLink: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference

        displayName = findViewById(R.id.displayName) as EditText
        status = findViewById(R.id.status) as EditText
        email = findViewById(R.id.emailRegister) as EditText
        password = findViewById(R.id.passwordRegister) as EditText
        cPassword = findViewById(R.id.cPasswordRegister) as EditText
        registerButton = findViewById(R.id.registerActionButton) as Button
        backLoginLink = findViewById(R.id.backLoginLink) as TextView

        registerButton.setOnClickListener(){
            if (validate()) {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        val registerRef = dbRef.child("user").child(userId)
                        val user = User(displayName.text.toString(), status.text.toString())
                        registerRef.setValue(user).addOnSuccessListener() {
                            toast("User created")
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
                }
            }
        }

        backLoginLink.setOnClickListener(){
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun validate(): Boolean {
        var valid = true

        val displayNameText = displayName.text.toString()
        val emailText = email.text.toString()
        val passwordText = password.text.toString()
        val cPasswordText = cPassword.text.toString()

        if (displayNameText.isEmpty()) {
            displayName.error = getString(R.string.valid_display_name)
            valid = false
        } else {
            displayName.error = null
        }

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

        if (cPasswordText.isEmpty() || cPasswordText.length < 4 || cPasswordText.length > 10 || cPasswordText != passwordText) {
            cPassword.error = getString(R.string.match_email)
            valid = false
        } else {
            cPassword.error = null
        }

        return valid
    }
}
