package com.example.proyectomoviles1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ClientePrincipal : AppCompatActivity() {
    lateinit var viewLoan: Button
    lateinit var viewSaving: Button
    var idUser : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_principal)



        initCompontent()
        println("Username :  $idUser")
        viewLoan.setOnClickListener{openVerPrestamos()}
        viewSaving.setOnClickListener{openVerAhorros()}
    }

    fun initCompontent(){
        viewLoan =findViewById(R.id.gestionarPrestamosBtn)
        viewSaving =findViewById(R.id.gestionarAhorrosBtn)
        idUser = intent.getIntExtra("USER_ID",0)
    }

    fun openVerPrestamos(){
        val intent = Intent(this, ver_prestamos::class.java)
        intent.putExtra("USER_ID", idUser)
        startActivity(intent)
    }
    fun openVerAhorros(){
        val intent = Intent(this, ver_ahorros::class.java)
        intent.putExtra("USER_ID", idUser)
        startActivity(intent)
    }

}