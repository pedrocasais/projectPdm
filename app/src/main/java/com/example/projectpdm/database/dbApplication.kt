package com.example.projectpdm.database

import android.app.Application

// relaciona os repositorios com os respetivos daos
class dbApplication : Application(){
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ListasDatabase.getDatabase(this) }
    val repository_listas by lazy { ListasRepository(database.ListasDao()) }
    val repository_produtos by lazy { ProdutosRepository(database.ProdutosDao()) }
    val repository_listaprodutos by lazy { ListaProdutosRepository(database.ListaProdutosDao()) }


}
