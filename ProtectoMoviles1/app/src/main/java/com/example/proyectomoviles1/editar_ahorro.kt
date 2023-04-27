package com.example.proyectomoviles1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import modelos.Saving

class editar_ahorro : AppCompatActivity() {

    private lateinit var tipo: TextView
    private lateinit var estado: TextView
    private lateinit var cantidadMensual: TextView
    private lateinit var cantidadNueva : EditText
    private lateinit var switchButton: Switch
    private lateinit var guardar: Button

    private lateinit var saving: Saving
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_ahorro)

        saving = intent.getSerializableExtra("saving") as Saving
        initComponent()
        cargarInfo()
        guardar.setOnClickListener { guardar() }

    }

    fun initComponent(){
        tipo = findViewById(R.id.tipo)
        estado = findViewById(R.id.estado)
        cantidadMensual = findViewById(R.id.cantidadMensual)
        guardar = findViewById(R.id.guardar)
        switchButton = findViewById(R.id.switch_button)
    }

    fun cargarInfo(){
        tipo.text = "Tipo : #${saving.getTypeSaving()}"
        if(saving.getIsActive()){
            estado.text = "Activo: SI"
            switchButton.text = "SI"
            switchButton.isChecked = true
        }
        else{
            estado.text = "Activo: NO"
            switchButton.text = "NO"
            switchButton.isChecked = false
        }
        cantidadMensual.text = "Cantidad Mensual: ${saving.getSavingAmount()}"

        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchButton.text = "SI"
            } else {
                switchButton.text = "NO"
            }
        }

    }

    fun guardar(){

        cantidadNueva = findViewById(R.id.editTextNumerico)


        if (cantidadNueva.text.toString().isNotEmpty()) {
            val cantidadNuevaNum = cantidadNueva.text.toString().toInt()
            if(cantidadNuevaNum >= 5000){
                if(switchButton.isChecked){
                    Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
                    // Update de Base
                    // Render la parte de arriba
                    // OnResumen para la vista anterior
                }
                else{
                    switchButton.error = "Debes activar el Switch"
                }
            }
            else{
                cantidadNueva.error = "El ahorro tiene que ser mayor a 5000"
            }
        } else {
            cantidadNueva.error = "Debe ingresar un número"
        }


    }

}
