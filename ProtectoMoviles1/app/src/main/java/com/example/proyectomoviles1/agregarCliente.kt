package com.example.proyectomoviles1

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Saving

class agregarCliente : AppCompatActivity() {

    // variables con la informacon del usuario
    lateinit var nombre: EditText
    lateinit var cedula: EditText
    lateinit var salario: EditText
    lateinit var telefono: EditText
    lateinit var estadoCivil: EditText
    lateinit var fechaNacimiento: EditText
    lateinit var direccion: EditText
    lateinit var rol: Spinner
    lateinit var nombreUsuario: EditText
    lateinit var contrasena: EditText

    // boton de ingresar nuevo usuario
    lateinit var agregarBtn: Button

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_cliente)
        initComponents()

        agregarBtn.setOnClickListener{insertarCliente()}
    }

    fun initComponents(){
        nombre = findViewById(R.id.nombreTxt)
        cedula = findViewById(R.id.cedulaClienteTxt)
        salario = findViewById(R.id.salarioClienteTxt)
        telefono = findViewById(R.id.telefonoClienteTxt)
        estadoCivil = findViewById(R.id.estadoCivilTxt)
        fechaNacimiento = findViewById(R.id.fechaNacimientoTxt)
        direccion = findViewById(R.id.direccionClienteTxt)
        rol = findViewById(R.id.rolSpinner)
        nombreUsuario = findViewById(R.id.userName)
        contrasena = findViewById(R.id.contrasenaClienteTxt)

        val roles = arrayOf("Cliente","Administrador")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,roles)
        rol.adapter = adapter

        agregarBtn = findViewById(R.id.insertarClienteBtn)
    }

    fun compruebaCampos(): Boolean {

        val nomUsuarioText = nombreUsuario.text.toString()
        val contrasenaTxt = contrasena.text.toString()
        val nombreTxt = nombre.text.toString()
        val cedulaTxt = cedula.text.toString()
        val salarioText = salario.text.toString()
        val telefonoTxt = telefono.text.toString()
        val estadoTxt = estadoCivil.text.toString()
        val fecNacText = fechaNacimiento.text.toString()
        val direecionTxt = direccion.text.toString()

        if(nomUsuarioText.isEmpty()) {
            nombreUsuario.setError("Ingrese un nombre de usuario válido")
            return false
        }else if(contrasenaTxt.isEmpty()){
              contrasena.setError("Ingrese una contraseña válida")
            return false
        }else if(nombreTxt.isEmpty()){
            nombre.setError("Ingrese un nombre válido")
            return false
        }else if(cedulaTxt.isEmpty()){
            cedula.setError("Ingrese un número de cedula válido")
            return false
        }else if(salarioText.isEmpty()){
            salario.setError("Ingrese un salario mayor a 0")
            return false
        }else if(telefonoTxt.isEmpty()){
            telefono.setError("Ingrese un número de teléfono válido")
            return false
        }else if(estadoTxt.isEmpty()){
            estadoCivil.setError("Ingrese un estado civil")
            return false
        }else if(fecNacText.isEmpty()){
            fechaNacimiento.setError("Ingrese una fecha de nacimiento válida")
            return false
        }else if(direecionTxt.isEmpty()){
            direccion.setError("Ingrese una dirección válida")
            return false
        }else{
            return true
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun insertarCliente(){

        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        if(compruebaCampos()
        ){
            val registro = ContentValues()
            registro.put("userName", nombreUsuario.text.toString())
            registro.put("password", contrasena.text.toString())
            registro.put("role",rol.selectedItem.toString())
            registro.put("id", cedula.text.toString())
            registro.put("name", nombre.text.toString())
            registro.put("salary", salario.text.toString())
            registro.put("phone",telefono.text.toString())
            registro.put("birtDate", fechaNacimiento.text.toString())
            registro.put("maritalStatus", estadoCivil.text.toString())
            registro.put("address",direccion.text.toString())

            db.insert("usuarios", null, registro)
            // Insert de Ahorros para este cliente
            val registroAhorro = ContentValues()
            registroAhorro.put("idUser",cedula.text.toString())
            registroAhorro.put("typeSaving","Navideño")
            registroAhorro.put("isActive",false)
            registroAhorro.put("savingAmount",0.0)
            db.insert("ahorros", null, registroAhorro)
            registroAhorro.put("typeSaving","Escolar")
            db.insert("ahorros", null, registroAhorro)
            registroAhorro.put("typeSaving","Marchamo")
            db.insert("ahorros", null, registroAhorro)
            registroAhorro.put("typeSaving","Extraordinario")
            db.insert("ahorros", null, registroAhorro)

            db.close()

            nombreUsuario.setText("")
            contrasena.setText("")
            cedula.setText("")
            nombre.setText("")
            salario.setText("")
            telefono.setText("")
            fechaNacimiento.setText("")
            estadoCivil.setText("")
            direccion.setText("")
            Toast.makeText(this, "!Usuario ingresado con éxito!", Toast.LENGTH_SHORT).show()
        }
    }
}