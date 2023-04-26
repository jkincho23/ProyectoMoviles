package com.example.proyectomoviles1

import android.content.ContentValues
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
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_details)

        loan = intent.getSerializableExtra("loan") as Loan
        initComponent()
        cargarInfo();
        payButton.setOnClickListener { pagar() }
    }

    override fun onPause() {
        super.onPause()
        payButton.isEnabled = true
        payButton.text = "Pagar"
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
        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        if(loan.remainingPayments() == 0){
            payButton.text = "Prestamo Cancelado"
            payButton.isEnabled = false
        }

        val values = ContentValues()
        values.put("cantPagos", loan.getPayment())

        val affectedRows = db.update(
            "prestamos",
            values,
            "id = ${loan.getId()}",
            null
        )

        db.close()
        if (affectedRows > 0) {
            Toast.makeText(this, "Préstamo actualizado", Toast.LENGTH_SHORT).show()
        }
        cargarInfo()


    }
}