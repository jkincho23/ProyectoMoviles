package modelos

class Client(
    username: String, password: String, role: String,
    private var name: String,
    private var cedula: Int,
    private var salary: Double,
    private var phone: String,
    private var birthDate: String,
    private var maritalStatus: String,
    private var address: String
) : User(username, password, role) {

    init {
        println("Se ha creado un objeto Cliente con los siguientes datos: " +
                "$username, $password, $role, $name,$cedula, $salary, $phone, $birthDate, $maritalStatus, $address")
    }

    // Métodos get y set para la propiedad name
    fun getName(): String {
        return name
    }

    fun setName(newName: String) {
        name = newName
    }

    fun getCedula(): Int {
        return cedula
    }

    // Métodos get y set para la propiedad salary
    fun getSalary(): Double {
        return salary
    }

    fun setSalary(newSalary: Double) {
        salary = newSalary
    }

    // Métodos get y set para la propiedad phone
    fun getPhone(): String {
        return phone
    }

    fun setPhone(newPhone: String) {
        phone = newPhone
    }

    // Métodos get y set para la propiedad birthDate
    fun getBirthDate(): String {
        return birthDate
    }

    fun setBirthDate(newBirthDate: String) {
        birthDate = newBirthDate
    }

    // Métodos get y set para la propiedad maritalStatus
    fun getMaritalStatus(): String {
        return maritalStatus
    }

    fun setMaritalStatus(newMaritalStatus: String) {
        maritalStatus = newMaritalStatus
    }

    // Métodos get y set para la propiedad address
    fun getAddress(): String {
        return address
    }

    fun setAddress(newAddress: String) {
        address = newAddress
    }

    // Método toString para mostrar información del objeto Cliente
    override fun toString(): String {
        return "Client(name='$name', cedula='$cedula' salary=$salary, phone='$phone', birthDate='$birthDate', maritalStatus='$maritalStatus', address='$address', ${super.toString()})"
    }

    fun maxLoanAmount(): Double {
        return salary * 0.45
    }

}