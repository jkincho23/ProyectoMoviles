package com.example.proyectomoviles1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import modelos.Loan

class LoanDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_details)

        val loan = intent.getSerializableExtra("loan") as Loan

        val customerTextView: TextView = findViewById(R.id.customerTextView)
        val amountTextView: TextView = findViewById(R.id.amountTextView)
        val periodTextView: TextView = findViewById(R.id.periodTextView)
        val remainingAmountTextView: TextView = findViewById(R.id.remainingAmountTextView)
        val remainingPeriodTextView: TextView = findViewById(R.id.remainingPeriodTextView)
        val typeTextView: TextView = findViewById(R.id.typeTextView)

        customerTextView.text = "Cliente: ${loan.getCustomerUsername()}"
        amountTextView.text = "Monto: ${loan.getAmount()}"
        periodTextView.text = "Plazo: ${loan.getPeriod()}"
        remainingAmountTextView.text = "Monto: ${loan.remainingAmount()}"
        remainingPeriodTextView.text = "Plazo: ${loan.remainingPayments()}"
        typeTextView.text = "Tipo de préstamo: ${loan.getTypeLoan()}"

    }
}