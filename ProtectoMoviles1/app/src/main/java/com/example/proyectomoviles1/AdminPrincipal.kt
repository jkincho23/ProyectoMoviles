package com.example.proyectomoviles1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AdminPrincipal : AppCompatActivity() {
    private lateinit var agregarClienteBtn: Button
    private lateinit var asignarPrestamoBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_principal)
        initComponents()

        agregarClienteBtn.setOnClickListener {openAgregarCliente()}
        asignarPrestamoBtn.setOnClickListener {openAgregarPrestamo()}
    }

    fun initComponents() {
        agregarClienteBtn = findViewById(R.id.agregarClienteBtn)
        asignarPrestamoBtn = findViewById(R.id.asignarPrestamoBtn)
    }

    fun openAgregarCliente(){
        val intent = Intent(this, agregarCliente::class.java)
        startActivity(intent)
    }

    fun openAgregarPrestamo(){
        val intent = Intent(this, agregarPrestamo::class.java)
        startActivity(intent)
    }
}