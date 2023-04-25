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
import modelos.Saving

class ver_ahorros : AppCompatActivity() {

    private val ahorrosList = mutableListOf<Saving>()
    private lateinit var listView: ListView
    lateinit var id_user : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ahorros)

        id_user = intent.getIntExtra("USER_ID",0).toString()
        println("UserID :: $id_user")
        cargarAhorros()
        initComponent()
        clicklers()
    }

    fun initComponent(){
        var adapter = object : ArrayAdapter<Saving>(this, R.layout.item_ahorro, ahorrosList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_ahorro, parent, false)
                val ahorro = ahorrosList[position]
                view.findViewById<TextView>(R.id.monto).text = "Ahorro: ${ahorro.getSavingAmount()}"
                view.findViewById<TextView>(R.id.estado).text = "Monto: ${ahorro.getIsActive()}"
                view.findViewById<TextView>(R.id.tipoAhorro).text = "Tipo: ${ahorro.getTypeSaving()}"
                return view
            }
        }
        listView = findViewById(R.id.listView)
        listView.adapter = adapter
    }

    fun clicklers(){
        listView.setOnItemClickListener { parent, view, position, id ->
            val saving = ahorrosList[position]
            val intent = Intent(this, LoanDetails::class.java)
            intent.putExtra("saving", saving)
            startActivity(intent)
        }

    }

    fun cargarAhorros(){

    }



}