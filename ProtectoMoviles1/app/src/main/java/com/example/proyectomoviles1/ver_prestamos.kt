package com.example.proyectomoviles1

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Loan

class ver_prestamos : AppCompatActivity() {
    private val prestamosList = mutableListOf<Loan>()
    private lateinit var listView: ListView
    lateinit var id_user : String

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_prestamos)

        id_user = intent.getIntExtra("USER_ID",0).toString()
        println("UserID :: $id_user")
        cargarPrestamos()
        initComponent()
        clicklers()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()
        prestamosList.clear()
        cargarPrestamos()
        initComponent()
    }

    fun initComponent(){
        val adapter = object : ArrayAdapter<Loan>(this, R.layout.item_prestamo, prestamosList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_prestamo, parent, false)
                val prestamo = prestamosList[position]
                view.findViewById<TextView>(R.id.nombreUsuario).text = "Usuario: ${prestamo.getUserId()}"
                view.findViewById<TextView>(R.id.montoPrestamo).text = "Monto: ${prestamo.getAmount()}"
                view.findViewById<TextView>(R.id.tipoPrestamo).text = "Tipo: ${prestamo.getTypeLoan()}"
                return view
            }
        }
        listView = findViewById(R.id.listView)
        listView.adapter = adapter
    }

    fun clicklers(){
        listView.setOnItemClickListener { parent, view, position, id ->
            val loan = prestamosList[position]
            val intent = Intent(this, LoanDetails::class.java)
            intent.putExtra("loan", loan)
            startActivity(intent)
        }

    }
    @RequiresApi(Build.VERSION_CODES.P)
    fun cargarPrestamos(){

        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        val fila = db.rawQuery("select credit, periodo, tipoCredito, cantPagos, id from prestamos where idUser = '$id_user'", null)

        if(fila.moveToFirst()){

            do {
                val credit = fila.getDouble(0) // Obtener el valor de la columna "credit" como entero
                val periodo = fila.getInt(1) // Obtener el valor de la columna "periodo" como cadena de texto
                val tipoCredito = fila.getString(2) // Obtener el valor de la columna "tipoCredito" como cadena de texto
                val cantPagos = fila.getString(3).toInt()
                val id = fila.getString(4).toInt()


                val loan = Loan(id,id_user, credit, periodo, tipoCredito,cantPagos)
                if(loan.remainingPayments() != 0){
                    println(loan.getPayment())
                    prestamosList.add(loan)
                }
            } while (fila.moveToNext())

            if(prestamosList.isEmpty()){
                Toast.makeText(this, "No tiene Prestamos", Toast.LENGTH_SHORT).show()
            }
        db.close()
        }else {
            Toast.makeText(this, "No tiene Prestamos", Toast.LENGTH_SHORT).show()
        }

    }
}