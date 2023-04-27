package com.example.proyectomoviles1

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Saving

class editar_ahorro : AppCompatActivity() {

    private lateinit var tipo: TextView
    private lateinit var estado: TextView
    private lateinit var cantidadMensual: TextView
    private lateinit var cantidadNueva: EditText
    private lateinit var switchButton: Switch
    private lateinit var guardar: Button

    private var switchValue: Boolean = false

    private lateinit var saving: Saving
    private lateinit var listView: ListView

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_ahorro)

        saving = intent.getSerializableExtra("saving") as Saving
        initComponent()
        cargarInfo();
        guardar.setOnClickListener { guardar() }

    }

    fun initComponent() {
        tipo = findViewById(R.id.tipo)
        estado = findViewById(R.id.estado)
        cantidadMensual = findViewById(R.id.cantidadMensual)
        guardar = findViewById(R.id.guardar)
        switchButton = findViewById(R.id.switch_button)
    }

    fun cargarInfo() {
        tipo.text = "Tipo : #${saving.getTypeSaving()}"
        if (saving.getIsActive()) {
            estado.text = "Activo: SI"
            switchButton.text = "Activo: SI"
            switchButton.isChecked = true
            switchValue = true
        } else {
            estado.text = "Activo: NO"
            switchButton.text = "Activo: NO"
            switchButton.isChecked = false
            switchValue = false
        }
        cantidadMensual.text = "Cantidad Mensual: ${saving.getSavingAmount()}"

        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchButton.text = "Activo: SI"
            } else {
                switchButton.text = "Activo: NO"
            }
        }

    }



    @RequiresApi(Build.VERSION_CODES.P)
    fun guardar() {
        val cantidadNueva = findViewById<EditText>(R.id.editTextNumerico)
        val isActive = switchButton.isChecked
        var cantidadNuevaNum = if (cantidadNueva.text.toString().isNotEmpty()) cantidadNueva.text.toString().toInt() else 0

        if (!isActive && !switchValue) {
            Toast.makeText(this, "Ahorro no actualizado", Toast.LENGTH_SHORT).show()
        } else if (cantidadNuevaNum < 5000 && isActive) {
            Toast.makeText(this, "La cantidad debe ser mayor a 5000", Toast.LENGTH_SHORT).show()
        } else if (cantidadNuevaNum == saving.getSavingAmount().toInt() && isActive) {
            Toast.makeText(this, "La cantidad es la misma", Toast.LENGTH_SHORT).show()
        } else {
            updateSavingAmount(isActive, cantidadNuevaNum)

            val admin = DataBase(this, "GestionPrestamos", null, 1)
            val db = admin.writableDatabase

            val values = ContentValues().apply {
                put("isActive", saving.getIsActive())
                put("savingAmount", saving.getSavingAmount())
            }

            val affectedRows = db.update(
                "ahorros",
                values,
                "idUser = '${saving.getCustomerUserName()}' AND typeSaving = '${saving.getTypeSaving()}'",
                null
            )

            db.close()
            if (affectedRows > 0) {
                Toast.makeText(this, "Ahorro actualizado", Toast.LENGTH_SHORT).show()
            }
            cargarInfo()
        }

    }

    private fun updateSavingAmount(isActive: Boolean, cantidadNuevaNum: Int) {
        saving.setIsActive(isActive)
        saving.setSavingAmount(if (isActive) cantidadNuevaNum.toDouble() else 0.0)
    }
}






