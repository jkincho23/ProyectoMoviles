package data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class DataBase(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int,
) : SQLiteOpenHelper(context, name, factory, version) {



    override fun onCreate(dataBase: SQLiteDatabase?) {
        dataBase?.execSQL("create table usuarios(userName String, password String, role String, id String primary key, " +
                "name String, salary Double, phone String, birtDate String, maritalStatus String, address String)")

        dataBase?.execSQL("create table prestamos(credit real, periodo real, tipoCredito String, idUser String)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}