package com.example.proyectomoviles1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import data.DataBase

class agregarPrestamo : AppCompatActivity() {

    // Información del cliente consultado
    lateinit var numIdConsulta: EditText
    lateinit var nombreView: TextView
    lateinit var cedulaView: TextView
    lateinit var salarioView: TextView
    lateinit var telefonoView: TextView
    lateinit var consultarBtn: ImageButton
    //Informacion para ingresar prestamo
    lateinit var credito: EditText
    lateinit var periodo: Spinner
    lateinit var tipo: Spinner


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_prestamo)
        initComponents()

        consultarBtn.setOnClickListener{consultarUsuario()}

    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun consultarUsuario(){
        val numIdTxt = numIdConsulta.text.toString()

        if(numIdTxt.isEmpty()){
            numIdConsulta.setError("Ingrese una identificación")
        }else{
            val admin = DataBase(this, "GestionPrestamos", null, 1)
            val db = admin.writableDatabase
            val fila = db.rawQuery("select name, id, salary, phone from usuarios where id = '$numIdTxt'", null)

            if(fila.moveToFirst()){
                 nombreView.setText(fila.getString(0))
                 cedulaView.setText(fila.getString(1))
                 salarioView.setText(fila.getString(2))
                 telefonoView.setText(fila.getString(3))
            }else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
            db.close()
        }
    }

    fun initComponents(){

        val periodos = arrayOf("1 año", "3 años","5 años")
        val tipos = arrayOf("Hipotecario","Educación","Personal","Viajes")

        numIdConsulta = findViewById(R.id.consultacedula)
        nombreView = findViewById(R.id.muestraNombre)
        cedulaView = findViewById(R.id.muestraCedula)
        salarioView = findViewById(R.id.muestraSalario)
        telefonoView = findViewById(R.id.muestraTelefono)
        periodo = findViewById(R.id.spinnerPeriodo)
        tipo = findViewById(R.id.spinnerTipo)
        consultarBtn = findViewById(R.id.consultarUsuarioBtn)


        val adapter1 = ArrayAdapter(this,android.R.layout.simple_spinner_item,periodos)
        periodo.adapter = adapter1

        val adapter2 = ArrayAdapter(this,android.R.layout.simple_spinner_item,tipos)
        tipo.adapter = adapter2

    }
}