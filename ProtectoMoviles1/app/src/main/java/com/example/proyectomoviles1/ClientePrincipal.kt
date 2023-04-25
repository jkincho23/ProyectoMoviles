package com.example.proyectomoviles1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ClientePrincipal : AppCompatActivity() {
    lateinit var view_loan: Button
    lateinit var view_saving: Button
    var id_user : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_principal)



        initCompontent()
        println("Username :  $id_user")
        view_loan.setOnClickListener{openVerPrestamos()}
        view_saving.setOnClickListener{openVerAhorros()}
    }

    fun initCompontent(){
        view_loan =findViewById(R.id.gestionarPrestamosBtn)
        view_saving =findViewById(R.id.gestionarAhorrosBtn)
        id_user = intent.getIntExtra("USER_ID",0)
    }

    fun openVerPrestamos(){
        val intent = Intent(this, ver_prestamos::class.java)
        intent.putExtra("USER_ID", id_user)
        startActivity(intent)
    }
    fun openVerAhorros(){
        val intent = Intent(this, ver_ahorros::class.java)
        intent.putExtra("USER_ID", id_user)
        startActivity(intent)
    }

}