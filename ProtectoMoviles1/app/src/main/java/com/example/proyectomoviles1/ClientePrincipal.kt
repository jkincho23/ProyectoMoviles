package com.example.proyectomoviles1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ClientePrincipal : AppCompatActivity() {
    lateinit var viewLoan: Button
    lateinit var viewSaving: Button
    lateinit var calcular_cuota: Button
    var idUser : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_principal)



        initCompontent()
        println("Username :  $idUser")
        viewLoan.setOnClickListener{openVerPrestamos()}
        viewSaving.setOnClickListener{openVerAhorros()}
        calcular_cuota.setOnClickListener { openCalculaCuota() }
    }

    fun initCompontent(){
        viewLoan =findViewById(R.id.gestionarPrestamosBtn)
        viewSaving =findViewById(R.id.gestionarAhorrosBtn)
        calcular_cuota = findViewById(R.id.calculaCuotaBtn)
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
    fun openCalculaCuota(){
        val intent = Intent(this, calcula_Cuota::class.java)
        intent.putExtra("USER_ID", idUser)
        startActivity(intent)
    }

}