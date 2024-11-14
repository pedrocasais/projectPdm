package com.example.projectpdm.database

import androidx.annotation.WorkerThread
import com.example.projectpdm.database.dao.ListaProdutosDao
import com.example.projectpdm.database.dao.ListasDao
import com.example.projectpdm.database.dao.ProdutosDao
import com.example.projectpdm.listaModel
import kotlinx.coroutines.flow.Flow


class ListasRepository (private val ListasDao: ListasDao) {


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(lista: Listas) {
        ListasDao.insert(lista)
    }

    //@WorkerThread
    fun lastId(): Flow<Int?> {
        return  ListasDao.lastId()
    }

    //@WorkerThread
    suspend fun updateLista(id: Int,price:Float) {
        ListasDao.updateLista(id,price)
    }

    //@WorkerThread
    fun getPrice(id:Int) : Flow<List<priceQuantidade>>{
        return  ListasDao.getPrice(id)
    }

    val getRecycler: Flow<List<listaModel>> = ListasDao.getRecycler()

    fun getTotal(data1:String, data2:String): Flow<Float> {
        return ListasDao.getTotal(data1, data2)
    }
}



class ProdutosRepository (private val ProdutosDao: ProdutosDao) {


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(produtos: Produtos) {
        ProdutosDao.insert(produtos)
    }

    //@WorkerThread
    fun lastId(): Flow<Int?> {
        return  ProdutosDao.lastId()
    }

}


class ListaProdutosRepository (private val ListaProdutosDao: ListaProdutosDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(ids: ListaProduto) {
        ListaProdutosDao.insert(ids)
    }

    //@WorkerThread
    fun lastId(): Flow<Int?> {
        return  ListaProdutosDao.lastId()
    }

    //@WorkerThread
    fun getProductsIds(cont : Int): Flow<List<Int?>> {
        return  ListaProdutosDao.getProductsIds(cont)
    }

}