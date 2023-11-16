package com.example.proyectomoviles1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi


class AdminPrincipal : AppCompatActivity() {
    private lateinit var agregarClienteBtn: Button
    private lateinit var asignarPrestamoBtn: Button
    private lateinit var eliminarClienteBtn: Button

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_principal)
        initComponents()

        agregarClienteBtn.setOnClickListener {openAgregarCliente()}
        asignarPrestamoBtn.setOnClickListener {openAgregarPrestamo()}
        eliminarClienteBtn.setOnClickListener { openEliminarCliente() }
    }

    fun initComponents() {
        agregarClienteBtn = findViewById(R.id.agregarClienteBtn)
        asignarPrestamoBtn = findViewById(R.id.asignarPrestamoBtn)
        eliminarClienteBtn = findViewById(R.id.eliminarClienteBtn)
    }

    fun openAgregarCliente(){
        val intent = Intent(this, agregarCliente::class.java)
        startActivity(intent)
    }

    fun openAgregarPrestamo(){
        val intent = Intent(this, agregarPrestamo::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun openEliminarCliente() {
        val intent = Intent(this, eliminarCliente::class.java)
        startActivity(intent)
    }
}