package com.example.proyectomoviles1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import modelos.Loan

class ver_prestamos : AppCompatActivity() {
    private val prestamosList = mutableListOf<Loan>()
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_prestamos)

        // Inicializa la lista de préstamos
        prestamosList.add(Loan("johndoe", 500.0, 12, "personal"))
        prestamosList.add(Loan("janedoe", 1000.0, 24, "hipotecario"))
        prestamosList.add(Loan("johndoe", 750.0, 6, "automotriz"))
        prestamosList.add(Loan("johndoe", 500.0, 12, "personal"))
        prestamosList.add(Loan("janedoe", 1000.0, 24, "hipotecario"))
        prestamosList.add(Loan("johndoe", 750.0, 6, "automotriz"))
        prestamosList.add(Loan("johndoe", 500.0, 12, "personal"))
        prestamosList.add(Loan("janedoe", 1000.0, 24, "hipotecario"))
        prestamosList.add(Loan("johndoe", 750.0, 6, "automotriz"))
        prestamosList.add(Loan("johndoe", 500.0, 12, "personal"))
        prestamosList.add(Loan("janedoe", 1000.0, 24, "hipotecario"))
        prestamosList.add(Loan("johndoe", 750.0, 6, "automotriz"))
        prestamosList.add(Loan("johndoe", 500.0, 12, "personal"))
        prestamosList.add(Loan("janedoe", 1000.0, 24, "hipotecario"))
        prestamosList.add(Loan("johndoe", 750.0, 6, "automotriz"))

        // Crea un adaptador para el ListView
//        val adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            prestamosList.map { it.toString() }
//        )

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

        // Asigna el adaptador al ListView
        listView = findViewById(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val loan = prestamosList[position]
            val intent = Intent(this, LoanDetails::class.java)
            intent.putExtra("loan", loan)
            startActivity(intent)
        }
    }
}