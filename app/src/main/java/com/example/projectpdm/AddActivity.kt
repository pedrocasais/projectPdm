package com.example.projectpdm

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectpdm.database.ListaProdutosViewModel
import com.example.projectpdm.database.ListaProdutosViewModelFactory
import com.example.projectpdm.database.ListasViewModelFactory
import com.example.projectpdm.database.ListasViewmodel
import com.example.projectpdm.database.ProdutosViewModel
import com.example.projectpdm.database.ProdutosViewModelFactory
import com.example.projectpdm.database.dbApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date

class AddActivity : AppCompatActivity() {

    private val listasViewmodel: ListasViewmodel by viewModels {
        ListasViewModelFactory((application as dbApplication).repository_listas)
    }

    private val produtosViewModel: ProdutosViewModel by viewModels {
        ProdutosViewModelFactory((application as dbApplication).repository_produtos)
    }

    private val listaprodutosViewModel: ListaProdutosViewModel by viewModels {
        ListaProdutosViewModelFactory((application as dbApplication).repository_listaprodutos)
    }

    private var item: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var text1: EditText = findViewById(R.id.editTextText)
        var text2:EditText = findViewById(R.id.editTextNumberDecimal)
        var text3 : EditText = findViewById(R.id.editTextNumber2)
        val spinner:Spinner = findViewById(R.id.spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_add,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                item = parent?.getItemAtPosition(position).toString()
                //Toast.makeText(this@AddActivity,"$item", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        var cont = 0
        val button = findViewById<FloatingActionButton>(R.id.btn1)

        button.setOnClickListener(){
            if(text1.text.toString().isEmpty()){
                Toast.makeText(this,"Falta nome de produto", Toast.LENGTH_SHORT).show()
            }
            else if(text2.text.toString().isEmpty()){
                Toast.makeText(this,"Falta preço do produto", Toast.LENGTH_SHORT).show()
            }
            else if (text3.text.toString().isEmpty()){
                Toast.makeText(this,"Falta quantidade do produto",Toast.LENGTH_SHORT).show()
            }
            else{
                var cat  = item.toString()
                cont += 1
                produtosViewModel.insert(nome = text1.text.toString() , price = text2.text.toString().toFloat() , categoria = cat,quantidade = text3.text.toString().toInt())
                text1.text.clear()
                text2.text.clear()
                text3.text.clear()
                Toast.makeText(this,"Produto adicionado", Toast.LENGTH_SHORT).show()

            }
        }


        val button2 = findViewById<FloatingActionButton>(R.id.btnfinish)
        button2.setOnClickListener(){
            //val a = produtosViewModel.getProductsIds(cont)
            //Log.d("getProductsIds",a.toString())

            //faz o dialog do nome da lista
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialog,null)
            val editTextDialog:EditText = dialogLayout.findViewById(R.id.dialog)
            if (cont != 0){
                //Dialog box para adicionar o nome da lista
                with(builder){
                    setTitle("Nome Lista")
                    setPositiveButton("Ok"){ dialog, which ->
                        if(editTextDialog.text.toString().isNotEmpty()){
                            // get data no formato dd-MM-yyyy
                            val data = SimpleDateFormat("dd-MM-yyyy").format(Date())
                            listasViewmodel.insert( nome = editTextDialog.text.toString(), data = data, price = 1.0f )
                            Toast.makeText(context,"Lista adicionada", Toast.LENGTH_SHORT).show()
                            listaprodutosViewModel.insert(cont)
                            finish()
                        }else{
                            Toast.makeText(context,"add nome lista", Toast.LENGTH_SHORT).show()
                        }
                    }
                    setNegativeButton("Voltar"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    setView(dialogLayout)
                    show()
                }
            }else{
                Toast.makeText(this,"Adicione pelo menos 1 produto",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
