package com.example.projectpdm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    val filename = "file.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText =  findViewById<EditText>(R.id.nome)

        //chama a funcao le caso true entao esconde o editText
        val vesetem = le()
        if(vesetem){
            editText.setVisibility(View.INVISIBLE)
        }

        val button: Button = findViewById(R.id.btn1)

        button.setOnClickListener(){
            //se nao tiver nome e editText nao for empty
            if (!vesetem && editText.text.isNotEmpty()){
                escreve(editText)
            }
            val intent: Intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)

        }

    }

    // escreve o nome num ficheiro de texto
    private fun escreve(editText: EditText){
        val nome = editText.text.toString()
        try {
            val fos : FileOutputStream = openFileOutput(filename, MODE_PRIVATE)
            fos.write(nome.toByteArray())
            fos.flush()
            fos.close()
        }catch (e: IOException) {
            println(e.message)
        }
    }

    // ve se existe algo escrito no ficherio
    //caso sim devolve true, se nao false
    private fun le():Boolean{
        try {
            val fis : FileInputStream = openFileInput(filename)
            val text = fis.bufferedReader().use { it.readText() }
            fis.close()
            return text.isNotEmpty()
        }catch (e: IOException) {
            println(e.message)
        }
        return false
    }


}
