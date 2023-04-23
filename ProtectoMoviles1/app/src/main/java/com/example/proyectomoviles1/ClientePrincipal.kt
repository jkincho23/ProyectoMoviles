package com.example.proyectomoviles1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ClientePrincipal : AppCompatActivity() {
    lateinit var view_loan: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_principal)

        initCompontent()

        view_loan.setOnClickListener{openVerPrestamos()}
    }

    fun initCompontent(){
        view_loan =findViewById(R.id.gestionarPrestamosBtn)

    }

    fun openVerPrestamos(){
        val intent = Intent(this, ver_prestamos::class.java)
        startActivity(intent)
    }

}