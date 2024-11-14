package com.example.projectpdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectpdm.database.ListasViewModelFactory
import com.example.projectpdm.database.ListasViewmodel
import com.example.projectpdm.database.dbApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.collections.arrayListOf

class VerActivity : AppCompatActivity() {

    var listaModel: ArrayList<listaModel> = ArrayList()

    private val listasViewmodel: ListasViewmodel by viewModels {
        ListasViewModelFactory((application as dbApplication).repository_listas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

        val adapter: listaRecyclerAdapter = listaRecyclerAdapter(this,listaModel)

        val name = arrayListOf<String>()
        val date = arrayListOf<String>()
        val price = arrayListOf<Float>()


        // obtem os dados da query e adiciona aos arrays
        // name, date e  price
        // chama a funcao fazRecycler
        // chama o adpter para por os dados no recycler e faz o layout do recyclerViewer
        // atualiza o recycler
        listasViewmodel.allListas.observe(this) { listas ->
            for (i in listas){
                name.add(i.nome)
                date.add(i.data)
                price.add(i.price.toFloat())
            }

            Log.d("listasmodel", " lista ${listas.size}")
            //Log.d("listasmodel", " dentro${name.size}")
            //Log.d("lista",listas.toString())
            fazRecycler(name,date,price)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter.notifyDataSetChanged()
        }

        //Log.d("listaModel", " fora${name.size}")

        val button : FloatingActionButton = findViewById(R.id.btn)
        button.setOnClickListener(){
            val intent: Intent = Intent(this,AddActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // faz um recyclerViewer serve para adicionar á class listaModel os dados dos
    // arrays name, date e price
    // os dados dos arrays sao os da lista
    private fun fazRecycler(name: ArrayList<String>, date: ArrayList<String>, price: ArrayList<Float>){
        Log.d("listaModel", "n de cenas ${name.size}")
        for (i in name.indices){
            Log.d("RecyclerView", " ${name[i]}")
            listaModel.add(listaModel(nome = name[i], data = date[i], price = price[i]))
        }
        Log.d("listaModel", "tamanho da lsita ${listaModel.size}")

        for (i in listaModel) {
            Log.d("listaModel", "print as cada uma  nome: ${i.nome}, data: ${i.data},price: ${i.price}")
        }
    }

}