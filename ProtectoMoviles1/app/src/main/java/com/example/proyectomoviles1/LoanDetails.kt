package com.example.proyectomoviles1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import data.DataBase
import modelos.Loan

class LoanDetails : AppCompatActivity() {

    private lateinit var customerTextView: TextView
    private lateinit var idLoan: TextView
    private lateinit var amountTextView: TextView
    private lateinit var periodTextView: TextView
    private lateinit var remainingAmountTextView: TextView
    private lateinit var remainingPeriodTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var amoutToPayTextView: TextView
    private lateinit var payButton: Button
    private lateinit var loan : Loan

    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_details)

        loan = intent.getSerializableExtra("loan") as Loan
        initComponent()
        cargarInfo();
        payButton.setOnClickListener { pagar() }
    }

    fun initComponent(){
        customerTextView = findViewById(R.id.customerTextView)
        idLoan = findViewById(R.id.idLoan)
        amountTextView = findViewById(R.id.amountTextView)
        amoutToPayTextView = findViewById(R.id.amoutToPayTextView)
        periodTextView = findViewById(R.id.periodTextView)
        remainingAmountTextView  = findViewById(R.id.remainingAmountTextView)
        remainingPeriodTextView = findViewById(R.id.remainingPeriodTextView)
        typeTextView = findViewById(R.id.typeTextView)
        payButton = findViewById(R.id.payButton)
    }

    fun cargarInfo(){
        idLoan.text = "Prestamos : #${loan.getId()}"
        customerTextView.text = "Cliente: ${loan.getUserId()}"
        amountTextView.text = "Monto Total: ${loan.getTotalAmount()}"
        periodTextView.text = "Plazo Total en Meses: ${loan.getPeriod()*12}"
        remainingAmountTextView.text = "Monto Restante : ${loan.remainingAmount()}"
        remainingPeriodTextView.text = "Plazo Restante  en Meses: ${loan.remainingPayments()}"
        amoutToPayTextView.text = "Cuota Mensual : ${loan.calculateMonthlyPayment()}"
        typeTextView.text = "Tipo de préstamo: ${loan.getTypeLoan()}"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun pagar(){
        loan.doPaymenent()
        println("Monto Pendiente  ${loan.remainingAmount()}")
        println("Pagos Pendiente  ${loan.remainingPayments()}")

        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        val fila = db.rawQuery("select credit, periodo, tipoCredito, cantPagos, id from prestamos where id = '${loan.getId()}'", null)

        if(fila.moveToFirst()){

                val credit = fila.getDouble(0) // Obtener el valor de la columna "credit" como entero
                val periodo = fila.getInt(1) // Obtener el valor de la columna "periodo" como cadena de texto
                val tipoCredito = fila.getString(2) // Obtener el valor de la columna "tipoCredito" como cadena de texto
                val cantPagos = fila.getString(3).toInt()
                val id = fila.getString(4).toInt()

        }else {
            Toast.makeText(this, "Prestamo Pagado", Toast.LENGTH_SHORT).show()
        }


    }
}