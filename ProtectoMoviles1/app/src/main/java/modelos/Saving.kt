package modelos

import java.io.Serializable

class Saving(private val idUser: String,
             private var typeSaving: String,
             private var isActive: Boolean,
             private var savingAmount: Double
): Serializable {



    init {
        println("Se ha creado un objeto Saving con los siguientes datos:")
        println("Nombre de usuario del cliente: $idUser")
        println("Tipo de ahorro: $typeSaving")
        println("Estado del ahorro: $isActive")
        println("Monto ahorrado Mensualmente: $savingAmount")
    }

    fun getCustomerUserName(): String {
        return idUser
    }

    fun setTypeSaving(typeSaving: String) {
        this.typeSaving = typeSaving
    }

    fun getTypeSaving(): String {
        return typeSaving
    }

    fun setIsActive(isActive: Boolean) {
        this.isActive = isActive
    }

    fun getIsActive(): Boolean {
        return isActive
    }

    fun setSavingAmount(savingAmount: Double) {
        this.savingAmount = savingAmount
    }

    fun getSavingAmount(): Double {
        return savingAmount
    }


    fun activateSaving(amount: Double): Boolean {
        if (amount >= 5000) {
            isActive = true
            savingAmount = amount
            return true
        } else {
            println("El monto mínimo de ahorro es de 5000 colones por mes.")
            return false
        }
    }

    fun deactivateSaving() {
        isActive = false
        savingAmount = 0.0
    }

    override fun toString(): String {
        return "Customer UserName: $idUser\nType of Saving: $typeSaving\nActive: $isActive\nSaving Amount: $savingAmount"
    }

}