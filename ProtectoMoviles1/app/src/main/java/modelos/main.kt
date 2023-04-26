package modelos

import android.content.ContentValues
import android.widget.Toast
import data.DataBase

fun main() {
    val usuario1 = User("Joaquin", "joaquin", "admin")
    val client : Client = Client("Joaquin", "joaquin", "admin", "Juan Perez",208270803, 1000.0, "555-1234", "01/01/1990", "Soltero/a", "Calle 123")

    val maxLoan = client.maxLoanAmount()
    println(maxLoan)
    val loan = Loan(23,client.getUsername(), 1000.0, 5, "Viajes",0)

    println("---------------------------")
    println("---------------------------")
    println("---------------------------")
    println("---------------------------")
    println("---------------------------")
    println("---------------------------")

//    val saving = Saving("usuario1", "Navideño")
    println("---------------------------")
    //saving.activateSaving(5000.0)
    //println(saving.toString())
    println("---------------------------")
    //saving.deactivateSaving()
    //println(saving.toString())

//    val users = arrayOf(usuario1,client)
//
//    for (user in users) {
//        println(user.toString())
//    }


}