package com.gmail.reebrando.myinbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mesage_verification.*

class MesageVerificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesage_verification)

        btnSim.setOnClickListener {
            Toast.makeText(applicationContext, "Sim! Recebi e retirei!", Toast.LENGTH_LONG).show()
        }

        btnNao.setOnClickListener {
            Toast.makeText(applicationContext, "Não... não recebi nada!", Toast.LENGTH_LONG).show()
        }

        btnDepois.setOnClickListener {
            super.onBackPressed()
        }
    }
}
