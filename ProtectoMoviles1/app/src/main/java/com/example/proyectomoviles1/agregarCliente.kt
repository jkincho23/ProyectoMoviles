package com.example.proyectomoviles1

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Client

class agregarCliente : AppCompatActivity() {

    // variables con la informacon del usuario
    lateinit var nombre: EditText
    lateinit var cedula: EditText
    lateinit var salario: EditText
    lateinit var telefono: EditText
    lateinit var fechaNacimiento: EditText
    lateinit var direccion: EditText
    lateinit var rol: Spinner
    lateinit var estadoCivil: Spinner
    lateinit var nombreUsuario: EditText
    lateinit var contrasena: EditText

    lateinit var client: Client

    // boton de ingresar nuevo usuario
    lateinit var agregarBtn: Button

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_cliente)
        initComponents()

        agregarBtn.setOnClickListener { insertarCliente() }
    }

    fun initComponents() {
        nombre = findViewById(R.id.nombreTxt)
        cedula = findViewById(R.id.cedulaClienteTxt)
        salario = findViewById(R.id.salarioClienteTxt)
        telefono = findViewById(R.id.telefonoClienteTxt)
        estadoCivil = findViewById(R.id.estadoCivilSpinner)
        fechaNacimiento = findViewById(R.id.fechaNacimientoTxt)
        direccion = findViewById(R.id.direccionClienteTxt)
        rol = findViewById(R.id.rolSpinner)
        nombreUsuario = findViewById(R.id.userName)
        contrasena = findViewById(R.id.contrasenaClienteTxt)

        val roles = arrayOf("Cliente", "Administrador")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        rol.adapter = adapter

        val estadosCiviles = arrayOf("Estado Civil","Soltero(a)", "Casado(a)", "Divorciado(a)", "Viudo(a)", "Unión Libre")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, estadosCiviles)
        estadoCivil.adapter = adapter2

        agregarBtn = findViewById(R.id.insertarClienteBtn)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun compruebaCampos(): Boolean {

        var bandera = true

        val nomUsuarioText = nombreUsuario.text.toString()
        val contrasenaTxt = contrasena.text.toString()
        val nombreTxt = nombre.text.toString()
        val cedulaTxt = cedula.text.toString()
        val salarioText = salario.text.toString()
        val telefonoTxt = telefono.text.toString()
        val fecNacText = fechaNacimiento.text.toString()
        val direecionTxt = direccion.text.toString()

        if (nomUsuarioText.isEmpty()) {
            nombreUsuario.error = "Ingrese un nombre de usuario válido"
            bandera = false
        }
        if (contrasenaTxt.isEmpty()) {
            contrasena.error = "Ingrese una contraseña válida"
            bandera = false
        }
        if (nombreTxt.isEmpty()) {
            nombre.error = "Ingrese un nombre válido"
            bandera = false
        }
        if (cedulaTxt.isEmpty()) {
            cedula.error = "Ingrese un número de cedula válido"
            bandera = false
        }
        if(estadoCivil.selectedItem.toString() == "Estado Civil"){
            val mensaje:TextView = estadoCivil.selectedView as TextView
            mensaje.error = ""
            bandera = false
        }
        if (salarioText.isEmpty() || salarioText.toInt() <= 0) {
            salario.setError("Ingrese un salario mayor a 0")
            bandera = false
        }
        if (telefonoTxt.isEmpty()) {
            telefono.error = "Ingrese un número de teléfono válido"
            bandera = false
        }
        if (fecNacText.isEmpty()) {
            fechaNacimiento.error = "Ingrese una fecha de nacimiento válida"
            bandera = false
        }
        if (direecionTxt.isEmpty()) {
            direccion.error = "Ingrese una dirección válida"
            bandera = false
        }
        if (bandera) {
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun compruebaNombreUsuario(): Boolean {

        val admin = DataBase(this, "GestionPrestamos", null, 1)
        val db = admin.writableDatabase
        val user =  nombreUsuario.text.toString()

        val fila = db.rawQuery("select * from usuarios where userName = '$user'", null)

        if(fila.moveToFirst()){
           return true
         }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun compruebaCedula(): Boolean {

        val admin = DataBase(this, "GestionPrestamos", null, 1)
        val db = admin.writableDatabase
        val idUser =  cedula.text.toString()

        val fila = db.rawQuery("select * from usuarios where id = '$idUser'", null)

        if(fila.moveToFirst()){
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun insertarCliente() {

        val admin = DataBase(this, "GestionPrestamos", null, 1)
        val db = admin.writableDatabase

        if (compruebaCampos()
        ) {
            if (!compruebaNombreUsuario()) {
                if(!compruebaCedula()){
                client = Client(
                    nombreUsuario.text.toString(),
                    nombreUsuario.text.toString(),
                    rol.selectedItem.toString(),
                    nombre.text.toString(),
                    cedula.text.toString().toInt(),
                    salario.text.toString().toDouble(),
                    telefono.text.toString(),
                    fechaNacimiento.text.toString(),
                    estadoCivil.selectedItem.toString(),
                    direccion.text.toString()
                )
                // insert del cliente
                val registro = ContentValues()
                registro.put("userName", client.getUsername())
                registro.put("password", client.getPassword())
                registro.put("role", client.getRole())
                registro.put("id", client.getCedula())
                registro.put("name", client.getName())
                registro.put("salary", client.getSalary())
                registro.put("phone", client.getPhone())
                registro.put("birtDate", client.getBirthDate())
                registro.put("maritalStatus", client.getMaritalStatus())
                registro.put("address", client.getAddress())

                val count = db.insert("usuarios", null, registro)

                if (count > 0) {
                    Toast.makeText(this, "!Usuario ingresado con éxito!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "!Ocurrió un error ingresando el usuario!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Insert de Ahorros para este cliente
                val registroAhorro = ContentValues()
                registroAhorro.put("idUser", client.getCedula())
                registroAhorro.put("typeSaving", "Navideño")
                registroAhorro.put("isActive", false)
                registroAhorro.put("savingAmount", 0.0)
                db.insert("ahorros", null, registroAhorro)
                registroAhorro.put("typeSaving", "Escolar")
                db.insert("ahorros", null, registroAhorro)
                registroAhorro.put("typeSaving", "Marchamo")
                db.insert("ahorros", null, registroAhorro)
                registroAhorro.put("typeSaving", "Extraordinario")
                db.insert("ahorros", null, registroAhorro)

                db.close()

                nombreUsuario.setText("")
                contrasena.setText("")
                cedula.setText("")
                nombre.setText("")
                salario.setText("")
                telefono.setText("")
                fechaNacimiento.setText("")
                direccion.setText("")
                }else{
                    cedula.error = "¡Cédula ya existente!"
                }
            }else{
                nombreUsuario.error = "¡Nombre de usuario ya existente!"
            }
        }
    }
}