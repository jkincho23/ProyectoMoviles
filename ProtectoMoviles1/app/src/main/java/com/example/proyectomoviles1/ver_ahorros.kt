package com.example.proyectomoviles1

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
import modelos.Saving

class ver_ahorros : AppCompatActivity() {

    private val ahorrosList = mutableListOf<Saving>()
    private lateinit var listView: ListView
    lateinit var id_user : String

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ahorros)

        id_user = intent.getIntExtra("USER_ID",0).toString()
        println("OnCreate UserID :: $id_user")
        cargarAhorros()
        initComponent()
        clicklers()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()
        ahorrosList.clear()
        cargarAhorros()
        initComponent()
    }

    fun initComponent(){
        val adapter = object : ArrayAdapter<Saving>(this, R.layout.item_ahorro, ahorrosList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_ahorro, parent, false)
                val ahorro = ahorrosList[position]
                view.findViewById<TextView>(R.id.monto).text = "Ahorro: ${ahorro.getSavingAmount()}"
                if(ahorro.getIsActive()){
                    view.findViewById<TextView>(R.id.estado).text = "Activo: SI"
                }
                else{
                    view.findViewById<TextView>(R.id.estado).text = "Activo: NO"

                }

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
            val intent = Intent(this, editar_ahorro::class.java)
            intent.putExtra("saving", saving)
            startActivity(intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun cargarAhorros(){

        println("Cargando ahorros")

        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        val fila = db.rawQuery("select idUser, typeSaving, isActive, savingAmount from ahorros where idUser = '$id_user'", null)
        var counter = 1
        if(fila.moveToFirst()){

            do {
                val idUser = fila.getString(0) // Obtener el valor de la columna "credit" como entero
                val typeActive = fila.getString(1) // Obtener el valor de la columna "periodo" como cadena de texto
                val isActive = fila.getInt(2) == 1
                val savingAmount = fila.getDouble(3)

                println("-")
                println("-")
                println("-")
                println("Ahorro : $counter")
                println("-")
                val saving = Saving(id_user, typeActive, isActive, savingAmount)
                ahorrosList.add(saving)
                counter++
            } while (fila.moveToNext())

            db.close()
        }else {
            Toast.makeText(this, "No tiene Ahorros", Toast.LENGTH_SHORT).show()
        }

    }



}