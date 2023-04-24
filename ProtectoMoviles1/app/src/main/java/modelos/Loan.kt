package modelos

import java.io.Serializable
import kotlin.math.pow

class Loan(
    private val userId: String,
    private var amount: Double,
    private var period: Int,
    private var typeLoan: String
): Serializable {

    private var interestRate: Double = 0.0
    private var totalAmount: Double = 0.0
    init {
        // Aquí podrías realizar alguna validación o cálculo inicial
        assignLoan()
        calculateTotalAmount()
        println("Se ha creado un objeto Loan con los siguientes datos: Cliente: $userId, Monto: $amount, Periodo: $period años, Tasa de interés: $interestRate%, Total a pagar: $totalAmount, typeLoan=$typeLoan")
    }

    fun setAmount(amount: Double) {
        this.amount = amount
        calculateTotalAmount()
    }

    fun setPeriod(period: Int) {
        this.period = period
        calculateTotalAmount()
    }

    fun setInterestRate(interestRate: Double) {
        this.interestRate = interestRate
    }

    fun setTypeLoan(newTypeLoan: String) {
        typeLoan = newTypeLoan
    }

    fun getCustomerUsername(): String {
        return userId
    }

    fun getAmount(): Double {
        return amount
    }

    fun getPeriod(): Int {
        return period
    }

    fun getInterestRate(): Double {
        return interestRate
    }

    fun getTotalAmount(): Double {
        return totalAmount
    }

    fun getTypeLoan(): String {
        return typeLoan
    }

    override fun toString(): String {
        return "Loan(customer=${userId}, amount=$amount, period=$period, interestRate=$interestRate, totalAmount=$totalAmount), typeLoan=$typeLoan)"
    }

    private fun calculateTotalAmount() {
        totalAmount = amount * (1 + interestRate)
    }

    fun calculateMonthlyPayment(): Double {
        val totalPeriods = period * 12
        return totalAmount / totalPeriods
    }

    fun assignLoan() {
        when(typeLoan) {
            "Hipotecario" -> {
                interestRate = 0.075
            }
            "Educación" -> {
                interestRate = 0.08
            }
            "Personal" -> {
                interestRate = 0.1
            }
            "Viajes" -> {
                interestRate = 0.12
            }
            else -> {
                // Tipo de crédito no reconocido
            }
        }
    }


}