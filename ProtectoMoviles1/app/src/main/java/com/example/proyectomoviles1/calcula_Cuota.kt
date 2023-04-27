package com.example.proyectomoviles1

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Loan

class calcula_Cuota : AppCompatActivity() {

    lateinit var credito: EditText
    lateinit var periodo: Spinner
    lateinit var tipo: Spinner
    lateinit var procesarBtn: Button
    lateinit var infoPeriodo: TextView
    lateinit var mensualidad: TextView
    lateinit var infoTipo: TextView
    lateinit var infoInteres: TextView

    lateinit var loan: Loan

    lateinit var id_user : String
    var salary : Double = 0.0
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcula_cuota)

        id_user = intent.getIntExtra("USER_ID",0).toString()
        println("UserID :: $id_user")
        extraerSalario();
        initComponents()
        procesarBtn.setOnClickListener { procesarPrestamo() }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun extraerSalario() {
        val admin = DataBase(this, "GestionPrestamos", null, 1)
        val db = admin.writableDatabase
        val fila = db.rawQuery("select salary from usuarios where id = '$id_user'", null)

        if(fila.moveToFirst()){
            salary = fila.getDouble(0)

        }
        db.close()
    }

    fun initComponents() {

        val periodos = arrayOf("3 años", "5 años", "10 años")
        val tipos = arrayOf("Hipotecario", "Educación", "Personal", "Viajes")


        periodo = findViewById(R.id.spinnerPeriodo)
        tipo = findViewById(R.id.spinnerTipo)
        credito = findViewById(R.id.creditoTxt)
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

    fun verificaCredito(): Boolean {
        if (credito.text.toString().toInt() <= salary * 0.45) {
            return true

        }
        return false
    }

    fun compruebaCampos(): Boolean {

        var bandera = true

        if (!credito.text.toString().isEmpty()) {
            if (!verificaCredito()) {
                credito.setError(
                    "El crédito máximo para este usuario es de: " + salary
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


    fun procesarPrestamo(): Boolean {
        if (compruebaCampos()) {

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
                1,
                id_user,
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


}