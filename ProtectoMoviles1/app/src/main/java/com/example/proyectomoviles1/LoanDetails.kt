package com.example.proyectomoviles1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import modelos.Loan

class LoanDetails : AppCompatActivity() {

    private lateinit var customerTextView: TextView
    private lateinit var idLoan: TextView
    private lateinit var amountTextView: TextView
    private lateinit var periodTextView: TextView
    private lateinit var remainingAmountTextView: TextView
    private lateinit var remainingPeriodTextView: TextView
    private lateinit var typeTextView: TextView
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
        typeTextView.text = "Tipo de préstamo: ${loan.getTypeLoan()}"
    }

    fun pagar(){
        
    }
}