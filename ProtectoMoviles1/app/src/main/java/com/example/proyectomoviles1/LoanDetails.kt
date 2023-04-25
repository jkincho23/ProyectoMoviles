package com.example.proyectomoviles1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_details)

        val loan = intent.getSerializableExtra("loan") as Loan

        customerTextView: TextView = findViewById(R.id.customerTextView)
        idLoan: TextView = findViewById(R.id.idLoan)
        amountTextView: TextView = findViewById(R.id.amountTextView)
        periodTextView: TextView = findViewById(R.id.periodTextView)
        remainingAmountTextView: TextView = findViewById(R.id.remainingAmountTextView)
        remainingPeriodTextView: TextView = findViewById(R.id.remainingPeriodTextView)
        typeTextView: TextView = findViewById(R.id.typeTextView)

        idLoan.text = "#${loan.getId()}"
        customerTextView.text = "Cliente: ${loan.getCustomerUsername()}"
        amountTextView.text = "Monto Total: ${loan.getTotalAmount()}"
        periodTextView.text = "Plazo Total en Meses: ${loan.getPeriod()*12}"
        remainingAmountTextView.text = "Monto: ${loan.remainingAmount()}"
        remainingPeriodTextView.text = "Plazo Restante  en Meses: ${loan.remainingPayments()}"
        typeTextView.text = "Tipo de préstamo: ${loan.getTypeLoan()}"

    }

    fun initComponent(){

    }
}