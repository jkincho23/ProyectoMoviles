package com.example.proyectomoviles1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Loan

class agregarPrestamo : AppCompatActivity() {

    // Variables
    var siguienteId: Int = 0

    // Información del cliente consultado
    lateinit var numIdConsulta: EditText
    lateinit var nombreView: TextView
    lateinit var cedulaView: TextView
    lateinit var salarioView: TextView
    lateinit var telefonoView: TextView
    lateinit var consultarBtn: ImageButton

    //Informacion para ingresar prestamo
    lateinit var credito: EditText
    lateinit var periodo: Spinner
    lateinit var tipo: Spinner
    lateinit var procesarBtn: Button
    lateinit var agregarPrestamoBtn: Button
    lateinit var infoPeriodo: TextView
    lateinit var mensualidad: TextView
    lateinit var infoTipo: TextView
    lateinit var infoInteres: TextView

    lateinit var loan: Loan


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_prestamo)
        obtenerId()
        initComponents()

        consultarBtn.setOnClickListener { consultarUsuario() }
        agregarPrestamoBtn.setOnClickListener { agregarPrestamo() }
        procesarBtn.setOnClickListener { procesarPrestamo() }

    }

    fun compruebaCampos(): Boolean {

        var bandera = true
        if (nombreView.text.toString().isEmpty() || cedulaView.text.toString().isEmpty()
            || salarioView.text.toString().isEmpty() || telefonoView.text.toString().isEmpty()
        ) {
            numIdConsulta.setError("Ingrese una identificación válida")
            bandera = false
        }
        if (credito.text.toString().isEmpty()) {
            credito.setError("Debe ingresar un valor")
            bandera = false
        }
        if (!credito.text.toString().isEmpty()) {
            if (!verificaCredito()) {
                credito.setError(
                    "El crédito máximo para este usuario es de: " + salarioView.text.toString()
                        .toInt() * 0.45
                )
                bandera = false
            }
        } else {
            credito.setError("Ingrese un monto válido")
        }
        if (bandera) {
            return true
        }
        return false
    }

    fun verificaCredito(): Boolean {
        if (credito.text.toString().toInt() <= salarioView.text.toString().toInt() * 0.45) {
            return true

        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("SuspiciousIndentation")
    fun procesarPrestamo(): Boolean {
        if (compruebaCampos()) {
            cedulaView.text.toString()
            credito.text.toString()
            periodo.selectedItem.toString()
            tipo.selectedItem.toString()

            val anios: Int
            if (periodo.selectedItem.toString().equals("3 años")) {
                anios = 3
            } else if (periodo.selectedItem.toString().equals("5 años")) {
                anios = 5
            } else {
                anios = 10
            }

            val loan = Loan(
                siguienteId + 1,
                cedulaView.text.toString(),
                credito.text.toString().toDouble(),
                anios,
                tipo.selectedItem.toString(),
                0
            )

            mensualidad.text = loan.calculateMonthlyPayment().toInt().toString()
            infoPeriodo.text = "${loan.getPeriod()} años"
            infoTipo.text = loan.getTypeLoan()
            infoInteres.text = (loan.getInterestRate() * 100).toString() + "%"
            return true
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun agregarPrestamo() {

        if (mensualidad.text.toString().isEmpty() ||
            infoPeriodo.text.toString().isEmpty() ||
            infoTipo.text.toString().isEmpty() ||
            infoInteres.text.toString().isEmpty()
        ) {
            credito.setError("Ingrese un monto antes de proceder")
        } else {
            val anios: Int
            if (periodo.selectedItem.toString().equals("3 años")) {
                anios = 3
            } else if (periodo.selectedItem.toString().equals("5 años")) {
                anios = 5
            } else {
                anios = 10
            }
            val pagos = 0

            loan = Loan(
                siguienteId + 1,
                cedulaView.text.toString(),
                credito.text.toString().toDouble(),
                anios,
                tipo.selectedItem.toString(),
                pagos
            )


            val admin = DataBase(this, "GestionPrestamos", null, 1)
            val db = admin.writableDatabase
            val registro = ContentValues()
            registro.put("id",loan.getId())
            registro.put("credit", loan.getTotalAmount())
            registro.put("periodo", loan.getPeriod())
            registro.put("tipoCredito", loan.getTypeLoan())
            registro.put("idUser", loan.getUserId())
            registro.put("cantPagos", loan.getPayment())


            db.insert("prestamos", null, registro)
            db.close()

            mensualidad.text = ""
            infoPeriodo.text = ""
            infoTipo.text = ""
            infoInteres.text = ""

            Toast.makeText(this, "!Prestamo agregado correctamente!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun consultarUsuario() {
        val numIdTxt = numIdConsulta.text.toString()

        if (numIdTxt.isEmpty()) {
            numIdConsulta.setError("Ingrese una identificación")
        } else {
            val admin = DataBase(this, "GestionPrestamos", null, 1)
            val db = admin.writableDatabase
            val fila = db.rawQuery(
                "select name, id, salary, phone from usuarios where id = '$numIdTxt'",
                null
            )

            if (fila.moveToFirst()) {
                nombreView.text = fila.getString(0)
                cedulaView.text = fila.getString(1)
                salarioView.text = fila.getDouble(2).toInt().toString()
                telefonoView.text = fila.getString(3)
                numIdConsulta.setText("")
            } else {
                nombreView.text = ""
                cedulaView.text = ""
                salarioView.text = ""
                telefonoView.text = ""
                credito.setText("")
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
            db.close()
        }
    }

    fun initComponents() {

        val periodos = arrayOf("3 años", "5 años", "10 años")
        val tipos = arrayOf("Hipotecario", "Educación", "Personal", "Viajes")

        numIdConsulta = findViewById(R.id.consultacedula)
        nombreView = findViewById(R.id.muestraNombre)
        cedulaView = findViewById(R.id.muestraCedula)
        salarioView = findViewById(R.id.muestraSalario)
        telefonoView = findViewById(R.id.muestraTelefono)
        periodo = findViewById(R.id.spinnerPeriodo)
        tipo = findViewById(R.id.spinnerTipo)
        credito = findViewById(R.id.creditoTxt)
        consultarBtn = findViewById(R.id.consultarUsuarioBtn)
        agregarPrestamoBtn = findViewById(R.id.agregarPrestamoBtn)
        procesarBtn = findViewById(R.id.procesarBtn)
        infoPeriodo = findViewById(R.id.periodoFinalText)
        mensualidad = findViewById(R.id.mensualidadText)
        infoTipo = findViewById(R.id.tipoFinalText)
        infoInteres = findViewById(R.id.interesTxt)

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, periodos)
        periodo.adapter = adapter1
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        tipo.adapter = adapter2

    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun obtenerId() {
        val admin = DataBase(this, "GestionPrestamos", null, 1)
        val db = admin.writableDatabase

        val filaCountQuery = "SELECT COUNT(*) FROM prestamos"
        val filaCount = db.rawQuery(filaCountQuery, null)

        filaCount.moveToFirst()
        siguienteId = filaCount.getInt(0)
        db.close()
    }
}