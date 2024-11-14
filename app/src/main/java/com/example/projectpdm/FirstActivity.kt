package com.example.projectpdm

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.projectpdm.database.ListasViewModelFactory
import com.example.projectpdm.database.ListasViewmodel
import com.example.projectpdm.database.dbApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale


class FirstActivity : AppCompatActivity() {
    private val listasViewmodel: ListasViewmodel by viewModels {
        ListasViewModelFactory((application as dbApplication).repository_listas)
    }

    private val calendar = Calendar.getInstance()

    val filename = "file.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //chama a funcao para mudar o texto inicial
        val textView = findViewById<EditText>(R.id.textHello)
        le(textView)
        Log.d("text32",textView.text.toString())


        val data1 = findViewById<EditText>(R.id.data1)
        val data2 = findViewById<EditText>(R.id.data2)
        val btnTotal = findViewById<Button>(R.id.btnTotal)

        //onclick abre o dialog
        data1.setOnClickListener{
            escolheData(data1)
        }

        data2.setOnClickListener{
            escolheData(data2)
        }


        btnTotal.setOnClickListener {
            Log.d("data1", data1.text.toString())
            Log.d("data2", data2.text.toString())
            val total = findViewById<TextView>(R.id.total)
            // coletar o valor que a funcao getTotal retornou e mudar o texto do textView
            lifecycleScope.launch {
                listasViewmodel.getTotal(data1.text.toString(), data2.text.toString()).collect{total1 ->
                    Log.d("valueTotal2",total1.toString())
                    if (data1.text.toString().isEmpty()){
                        total.setText("Total ate ${data2.text.toString()}\n $total1")
                    } else{
                        total.setText("Total entre ${data1.text.toString()} e ${data2.text.toString()}\n $total1")
                    }
                }
            }
        }

        val buttonAdd = findViewById<FloatingActionButton>(R.id.btnadd)
        buttonAdd.setOnClickListener(){
            val intent: Intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            //finish()
        }

        val buttonVer = findViewById<FloatingActionButton>(R.id.btnver)
        buttonVer.setOnClickListener(){
            val intent : Intent = Intent(this,VerActivity::class.java)
            startActivity(intent)
        }
    }

    // le o ficheiro e muda o editText
    private fun le(editText: EditText) {
        try {
            val fis : FileInputStream = openFileInput(filename)
            val text = fis.bufferedReader().use { it.readText() }
            Log.d("texte",text)
            Log.d("text",text.toString())
            editText.setText("Olá, ${text}!")
            fis.close()
        }catch (e: IOException) {
            println(e.message)
        }

    }

    // funcao que faz mostrar o calendario para escolher duas duas
    fun escolheData(data:TextView) {

            val dataDialog = DatePickerDialog(
                this, { DatePicker, year: Int, month: Int, day: Int ->
                    val escolheData = Calendar.getInstance()
                    escolheData.set(year, month, day)

                    val Data = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(escolheData.time)
                    data.text = Data
                    Log.d("data" ,data.text.toString())

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dataDialog.show()
        }

}
