package com.example.proyectomoviles1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class agregarPrestamo : AppCompatActivity() {

    // Información del cliente consultado
    lateinit var numIdConsulta: EditText
    lateinit var nombreView: EditText
    lateinit var cedulaView: EditText
    lateinit var salarioView: EditText
    lateinit var telefonoView: EditText
    //Informacion para ingresar prestamo
    lateinit var credito: EditText
    lateinit var periodo: Spinner
    lateinit var tipo: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_prestamo)
        initComponents()

    }

    fun initComponents(){

        val periodos = arrayOf("1 año", "3 años","5 años")
        val tipos = arrayOf("Hipotecario","Educación","Personal","Viajes")
        periodo = findViewById(R.id.spinnerPeriodo)
        tipo = findViewById(R.id.spinnerTipo)

        val adapter1 = ArrayAdapter(this,android.R.layout.simple_spinner_item,periodos)
        periodo.adapter = adapter1

        val adapter2 = ArrayAdapter(this,android.R.layout.simple_spinner_item,tipos)
        tipo.adapter = adapter2

    }
}