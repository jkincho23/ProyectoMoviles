package modelos

class Saving(private val customerUserName: String, private var typeSaving: String) {


    private var isActive: Boolean = false
    private var savingAmount: Double = 0.0

    init {
        println("Se ha creado un objeto Saving con los siguientes datos:")
        println("Nombre de usuario del cliente: $customerUserName")
        println("Tipo de ahorro: $typeSaving")
        println("Estado del ahorro: $isActive")
        println("Monto ahorrado: $savingAmount")
    }

    fun getCustomerUserName(): String {
        return customerUserName
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
        return "Customer UserName: $customerUserName\nType of Saving: $typeSaving\nActive: $isActive\nSaving Amount: $savingAmount"
    }

}