package com.example.proyectomoviles1


import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Client

class PersonalData : AppCompatActivity() {

    lateinit var nombreView: EditText
    lateinit var salarioView: EditText
    lateinit var telefonoView: EditText
    lateinit var fechaView: EditText
    lateinit var estadoCivilView: Spinner
    lateinit var DireccionView: EditText

    lateinit var guardarBtn: Button

    lateinit var cliente: Client
    lateinit var id_user : String
    val estadosCiviles = arrayOf("Estado Civil","Soltero(a)", "Casado(a)", "Divorciado(a)", "Viudo(a)", "Unión Libre")

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        id_user = intent.getIntExtra("USER_ID",0).toString()
        println("UserID :: $id_user")
        initComponent()
        buscarCliente()
        guardarBtn.setOnClickListener { guardar() }
    }
    @RequiresApi(Build.VERSION_CODES.P)

    fun initComponent(){
        nombreView = findViewById(R.id.muestraNombreData)
        salarioView = findViewById(R.id.muestraSalarioData)
        telefonoView = findViewById(R.id.muestraTelefonoData)
        fechaView = findViewById(R.id.muestraFechaData)
        estadoCivilView= findViewById(R.id.muestraEstadoCivilData)

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, estadosCiviles)

        estadoCivilView.adapter = adapter2

        DireccionView = findViewById(R.id.muestraDireccionData)
        guardarBtn = findViewById(R.id.botonGuardar)
    }

    fun compruebaCampos(): Boolean {

        var bandera = true
        val nombreTxt = nombreView.text.toString()
        val salarioText = salarioView.text.toString()
        val telefonoTxt = telefonoView.text.toString()
        val fecNacText = fechaView.text.toString()
        val direecionTxt = DireccionView.text.toString()

        if (nombreTxt.isEmpty()) {
            nombreView.error = "Ingrese un nombre válido"
            bandera = false
        }

        if(estadoCivilView.selectedItem.toString() == "Estado Civil"){
            val mensaje:TextView = estadoCivilView.selectedView as TextView
            mensaje.error = ""
            bandera = false
            }
        if (salarioText.isEmpty() || salarioText.toDouble() <= 0) {
            salarioView.setError("Ingrese un salario mayor a 0")
            bandera = false
        }
        if (telefonoTxt.isEmpty()) {
            telefonoView.error = "Ingrese un número de teléfono válido"
            bandera = false
        }
        val pattern = Regex("""\d{2}/\d{2}/\d{4}""")
        if (!fecNacText.matches(pattern)) {
            fechaView.error = "Ingrese una fecha de nacimiento válida con formato XX/XX/XXXX"
            bandera = false
        }
        if (direecionTxt.isEmpty()) {
            DireccionView.error = "Ingrese una dirección válida"
            bandera = false
        }
        if (bandera) {
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun guardar(){
        if (compruebaCampos()
        ) {
            val admin = DataBase(this, "GestionPrestamos", null, 1)
            val db = admin.writableDatabase

            val values = ContentValues().apply {
                put("name", nombreView.text.toString())
                put("salary", salarioView.text.toString())
                put("phone", telefonoView.text.toString())
                put("birtDate", fechaView.text.toString())
                put("maritalStatus", estadoCivilView.selectedItem.toString())
                put("address", DireccionView.text.toString())
            }

            val affectedRows = db.update(
                "usuarios",
                values,
                "id = '${id_user}'",
                null
            )

            db.close()
            if (affectedRows > 0) {
                Toast.makeText(this, "Datos Nuevos Guardados", Toast.LENGTH_SHORT).show()
            }
            else{
                println("algo pasa")
            }
        }
        else{
            Toast.makeText(this, "Datos No Guardados", Toast.LENGTH_SHORT).show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun buscarCliente(){

        val admin = DataBase(this, "GestionPrestamos", null, 1)
        val db = admin.writableDatabase

        val fila = db.rawQuery(
            "select name, salary, phone, birtDate, maritalStatus, address from usuarios where id = '$id_user'",
                null
        )

        if (fila.moveToFirst()) {

            val name = fila.getString(0)
            val salary = fila.getDouble(1)
            val phone = fila.getString(2)
            val birthDate = fila.getString(3)
            val maritalStatus = fila.getString(4)
            val address = fila.getString(5)

            cliente = Client("","","",name,id_user.toInt(), salary,phone,birthDate,maritalStatus, address)

            nombreView.setText(name)
            salarioView.setText(salary.toString())
            telefonoView.setText(phone)
            fechaView.setText(birthDate)
            estadoCivilView.setSelection(estadosCiviles.indexOf(maritalStatus))
            DireccionView.setText(address)
        }
        fila.close()
        db.close()
    }
}