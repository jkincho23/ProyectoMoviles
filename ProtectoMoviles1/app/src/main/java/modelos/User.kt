package modelos

open class User(private var username: String, private var password: String, private var role: String) {

    // Constructor primario
    init {
        println("Se ha creado un objeto Usuario con los siguientes datos: $username, $password, $role")
    }

    // Métodos get y set para la propiedad username
    fun getUsername(): String {
        return username
    }

    fun setUsername(newUsername: String) {
        username = newUsername
    }

    // Métodos get y set para la propiedad password
    fun getPassword(): String {
        return password
    }

    fun setPassword(newPassword: String) {
        password = newPassword
    }

    // Métodos get y set para la propiedad role
    fun getRole(): String {
        return role
    }

    fun setRole(newRole: String) {
        role = newRole
    }

    // Método toString para mostrar información del objeto Usuario
    override fun toString(): String {
        return "User(username='$username', password='$password', role='$role')"
    }
}
