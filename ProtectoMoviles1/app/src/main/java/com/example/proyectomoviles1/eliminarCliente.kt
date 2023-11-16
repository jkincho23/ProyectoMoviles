package com.example.proyectomoviles1

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomoviles1.utilities.CustomItemDecorator
import data.DataBase


@RequiresApi(Build.VERSION_CODES.P)
class eliminarCliente : AppCompatActivity() {

    private val clientes = mutableListOf<ClienteRecycler>()

    private lateinit var recycler: RecyclerView
    private lateinit var clientesAdapter: ClientesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_cliente)

        inicializarRecycler()
    }

    private fun cargarClientes() {
        clientes.removeAll { cliente -> cliente != null }
        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        val fila = db.rawQuery("select id, name from usuarios where role like 'Cliente'", null)
        if (fila.moveToFirst()) {

            do {
                val id = fila.getString(0)
                val nombre = fila.getString(1)
                clientes.add(ClienteRecycler(nombre, id))
            } while (fila.moveToNext())
        } else {
            Toast.makeText(this, "No hay clientes en el Sistema.", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    private fun inicializarRecycler() {
        recycler = findViewById(R.id.recyclerClientes)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.addItemDecoration(DividerItemDecoration(recycler.context, LinearLayoutManager.VERTICAL))

        cargarClientes()

        clientesAdapter = ClientesAdapter(clientes) { cliente -> onItemSelected(cliente) }
        recycler.adapter = clientesAdapter
    }

    private fun onItemSelected(clienteRecycler: ClienteRecycler) {
        val idABorrar = clienteRecycler.id
        val admin = DataBase(this,"GestionPrestamos",null,1)
        val db = admin.writableDatabase

        db.delete("usuarios", "id=?", arrayOf(idABorrar))
        db.close()

        val indexRemoved = clientes.indexOf(clienteRecycler)
        clientesAdapter.notifyItemRemoved(indexRemoved)
        cargarClientes()
    }

    private data class ClienteRecycler(val nombre: String, val id: String) {}


    private inner class ClientesAdapter(var lista: List<ClienteRecycler>, val onClickListener: (ClienteRecycler) -> Unit):
        RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_cliente_componente, parent, false)

            return ClienteViewHolder(view)
        }

        override fun getItemCount(): Int {
            return lista.size
        }

        override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
            holder.bind(lista[position], onClickListener)
        }

        inner class ClienteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            private val tvIdCliente = itemView.findViewById<TextView>(R.id.idCliente)
            private val tvNombreCliente = itemView.findViewById<TextView>(R.id.nombreCliente)

            fun bind(cliente: ClienteRecycler, onClickListener: (ClienteRecycler) -> Unit) {
                tvIdCliente.text = cliente.id
                tvNombreCliente.text = cliente.nombre

                itemView.setOnClickListener { onClickListener(cliente) }
            }
        }
    }
}