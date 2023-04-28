package com.example.proyectomoviles1

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Client

class MainActivity : AppCompatActivity() {
    private lateinit var usuarioTxt: EditText
    private lateinit var contrasenaTxt: EditText
    private lateinit var ingresarBtn: Button
    private lateinit var cargarDatosBtn: Button


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        ingresarBtn.setOnClickListener {login()}
        cargarDatosBtn.setOnClickListener { cargarDatos() }
    }

    fun initComponents() {
        usuarioTxt = findViewById(R.id.usuarioTxt)
        contrasenaTxt = findViewById(R.id.contrasenaTxt)
        ingresarBtn = findViewById(R.id.ingresarBtn)
        cargarDatosBtn = findViewById(R.id.cargarDatosBtn)
    }

    fun compruebaCampos(): Boolean {
        if (usuarioTxt.text.toString().isEmpty()) {
            usuarioTxt.error = "Ingrese un nombre de usuario valido"
            return false
        } else if (contrasenaTxt.text.toString().isEmpty()) {
            contrasenaTxt.error = "Ingrese una contraseña valida"
            return false
        } else {
            return true
        }
    }

    fun openAdminPrincipal(){
        val intent = Intent(this, AdminPrincipal::class.java)
        startActivity(intent)
    }

    fun openClientePrincipal(username: Int) {
        val intent = Intent(this, ClientePrincipal::class.java)
        intent.putExtra("USER_ID", username)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login() {
        if (compruebaCampos()) {
            consultar()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun insertarCliente(client: Client){

        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        if(!client.getUsername().isEmpty() && !client.getPassword().isEmpty() &&
            !client.getRole().isEmpty() && !client.getName().isEmpty() && !client.getSalary().toString().isEmpty()
            && !client.getPhone().isEmpty() && !client.getAddress().isEmpty() && !client.getMaritalStatus().isEmpty()
            && !client.getAddress().isEmpty() && !client.getCedula().toString().isEmpty()
        ){
            val registro = ContentValues()
            registro.put("userName", client.getUsername())
            registro.put("password",client.getPassword())
            registro.put("id",client.getCedula())
            registro.put("role",client.getRole())
            registro.put("name",client.getName())
            registro.put("salary",client.getSalary())
            registro.put("maritalStatus", client.getMaritalStatus())
            registro.put("phone",client.getPhone())
            registro.put("birtDate",client.getBirthDate())
            registro.put("address",client.getAddress())

            db.insert("usuarios", null, registro)
            db.close()
            Toast.makeText(this, "Datos guardados!", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun consultar(){

        val admin = DataBase(this, "GestionPrestamos", null, 1)
        val db = admin.writableDatabase
        val user = usuarioTxt.text.toString()
        val password = contrasenaTxt.text.toString()

        val fila = db.rawQuery("select userName,id, password, role from usuarios where userName = '$user' and password = '$password'", null)

        if(fila.moveToFirst()){
            if(fila.getString(3) == "Cliente"){
                println(fila.getString(1).toInt())
                openClientePrincipal(fila.getString(1).toInt())
            }else{
                openAdminPrincipal()
            }
        }else {
            Toast.makeText(this, "Usuario no encontrados", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun cargarDatos() {
        val cliente1 = Client("andres","andres","Cliente",
            "Andres Martinez Araya",208270803, 1500000.0, "632548785", "28/05/1997",
            "Soltero(a)", "Snta Cecilia, contiguo al AMPM")
        agregarAhorro(cliente1)
        val cliente2 = Client("marcos","marcos","Administrador",
            "Marcos Lopez Araya",111111111, 1800000.0, "582548785", "28/10/1997",
            "Soltero(a)", "Jardines 2, 50 mts este Hotel de paso")
        agregarAhorro(cliente2)
        val cliente3 = Client("luis","luis","Cliente",
            "Luis Soto Vega",123456789, 2000000.0, "632548785", "28/10/1997",
            "Soltero(a)", "Belen, por el mas x menos")
        agregarAhorro(cliente3)
        val cliente4 = Client("julian","julian","Administrador",
            "Julian Gutierrez Alvarez",987654321, 1250000.0, "56584715", "28/10/1997",
            "Soltero(a)", "Barreal de Heredia 100 mts pul veranera")
        agregarAhorro(cliente4)
        val cliente5 = Client("elias","elias","Cliente",
            "Elias Arias Muñoz",504330612, 800000.0, "732532785", "28/08/1982",
            "Soltero(a)", "100 mts pali aurora")
        agregarAhorro(cliente5)
        val cliente6 = Client("maria","maria","Cliente",
            "Maria Brenes Sequeira",112233445, 950000.0, "632532785", "21/10/1991",
            "Casado(a)", "100 mts este Wallmart Ulloa")
        agregarAhorro(cliente6)
        val cliente7 = Client("mateo","mateo","Cliente",
            "Mateo Vargas Lopez",165486848, 900000.0, "32532785", "21/10/1980",
            "Soltero(a)", "25 mts este pulperia San Cristobal")
        agregarAhorro(cliente7)
        val cliente8 = Client("katty","katty","Cliente",
            "Katyy Morales Soto",536601548, 1000000.0, "654532785", "28/02/1991",
            "Viudo(a)", "100 mts norte Ferret Fortaleza")
        agregarAhorro(cliente8)
        val cliente9 = Client("joaquin","joaquin","Cliente",
            "Joaquin Garcia Lopez",610245458, 1200000.0, "343532785", "21/10/1992",
            "Viudo(a)", "100 mts sur Pali")
        agregarAhorro(cliente9)
        val cliente10 = Client("lucas","lucas","Cliente",
            "Lucas Albornoz Chavez",312546587, 1300000.0, "824532785", "28/10/1991",
            "Viudo(a)", "100 mts norte Repuestos Gigante")
        agregarAhorro(cliente10)

        insertarCliente(cliente1)
        insertarCliente(cliente2)
        insertarCliente(cliente3)
        insertarCliente(cliente4)
        insertarCliente(cliente5)
        insertarCliente(cliente6)
        insertarCliente(cliente7)
        insertarCliente(cliente8)
        insertarCliente(cliente9)
        insertarCliente(cliente10)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun agregarAhorro(client: Client){

        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

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

        Toast.makeText(this, "Datos guardados!", Toast.LENGTH_SHORT).show()

    }


}