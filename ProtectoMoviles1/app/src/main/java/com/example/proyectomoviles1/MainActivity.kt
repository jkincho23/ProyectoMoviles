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


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()

/*        val cliente1: Client = Client("andres","andres","Cliente",
            "Andres Martinez Araya", 1500000.0, "632548785", "28/05/1997",
            "Soltero", "Barreal de Heredia 100 mts pul veranera");
        val cliente2: Client = Client("marcos","marcos","Administrador",
            "Marcos Lopez Araya", 1800000.0, "632548785", "28/10/1997",
            "Soltero", "Barreal de Heredia 100 mts pul veranera");

        insertarCliente(cliente1);
        insertarCliente(cliente2);
*/
        ingresarBtn.setOnClickListener {login()}
    }

    fun initComponents() {
        usuarioTxt = findViewById(R.id.usuarioTxt)
        contrasenaTxt = findViewById(R.id.contrasenaTxt)
        ingresarBtn = findViewById(R.id.ingresarBtn)
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

    fun openClientePrincipal(){
        val intent = Intent(this, ClientePrincipal::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login() {
        if (compruebaCampos()) {
            consultar();
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun insertarCliente(client: Client){

        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        if(!client.getUsername().isEmpty() && !client.getPassword().isEmpty() &&
            !client.getRole().isEmpty() && !client.getName().isEmpty() && !client.getSalary().toString().isEmpty()
            && !client.getPhone().isEmpty() && !client.getAddress().isEmpty() && !client.getMaritalStatus().isEmpty()
            && !client.getAddress().isEmpty()
        ){
            val registro = ContentValues()
            registro.put("userName", client.getUsername())
            registro.put("password",client.getPassword())
            registro.put("role",client.getRole())
            registro.put("name",client.getName())
            registro.put("salary",client.getSalary())
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

        val fila = db.rawQuery("select userName, password, role from usuarios where userName = '$user' and password = '$password'", null)

        if(fila.moveToFirst()){
            if(fila.getString(2) == "Cliente"){
                openClientePrincipal()
            }else{
                openAdminPrincipal()
            }
        }else {
            Toast.makeText(this, "Usuario no encontrados", Toast.LENGTH_SHORT).show()
        }
    }
}