package com.example.projectpdm.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.projectpdm.listaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ListasViewmodel(private val repository: ListasRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    // coleta os valores obtidos pela query para uma lista do tipo priceQuantidade
    //depois faz o fold para multiplicar o preco pela quantidade e soma num acumulador todos os valores da lista,
    // retorna o acumulador(acc)
    //update da lista com o id e o valor total da lista
    fun getPrice(id: Int) = viewModelScope.launch {
        repository.getPrice(id).collect { lista ->
            val total = lista.fold(0.0f) { acc, lst -> (lst.price * lst.quantidade) + acc }
            Log.d("total", "asda, $total")
            repository.updateLista(id = id, price = total)
        }

    }

    //add a uma lista na db
    // ve qual o ultimo Id e poe em lastId
    // se nao tiver id fica 1
    //insere a nova lista e
    // chama a funcao getPrice para atualizar o preco da lista
    fun insert(nome: String, price: Float, data: String) = viewModelScope.launch {
        val lastId = repository.lastId().first()

        if (lastId != null) {
            val lista = Listas(id = lastId + 1, nome = nome, data = data, price = 0f)
            repository.insert(lista)
            getPrice((lastId + 1))
        } else {
            val lista = Listas(id = 1, nome = nome, data = data, price = price)
            repository.insert(lista)
            getPrice(1)
        }
    }

    //obtem o nome, data e preco de uma lista para utilizar no recycler viewer
    // live data para poder utilizar diretamente na UI
    val allListas: LiveData<List<listaModel>> = repository.getRecycler.asLiveData()
    //Log.d("dsa",allListas)

    // return do total de dinheiero "gasto" nas listas
    // entre duas datas
    fun getTotal(data1: String, data2: String): Flow<Float> {
        return repository.getTotal(data1,data2)
    }

}


class ListasViewModelFactory(private val repository: ListasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListasViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListasViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



class ProdutosViewModel(private val repository: ProdutosRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    //inserir na tabela produtos um produto
    //obtem o lastId presente na tabela caso nao haja entao lastId = 1
    fun insert(nome: String, price: Float, categoria: String,quantidade: Int) = viewModelScope.launch {
        var lastId = repository.lastId().first()
        if (lastId != null){
            val produto = Produtos(id = lastId + 1,nome = nome, price= price, categoria = categoria,quantidade = quantidade)
            repository.insert(produto)
            Log.d("prduto", "added$lastId")
        }else{
            val produto = Produtos(id = 1,nome = nome, price= price, categoria = categoria,quantidade = quantidade)
            repository.insert(produto)
        }
    }

}


class ProdutosViewModelFactory(private val repository: ProdutosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProdutosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProdutosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class ListaProdutosViewModel(private val repository: ListaProdutosRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    // insere na tabela listaProduto os ids de produtos que fazem parta de uma lista
    //  obtem o lastId de uma lista caso nao haja fica 1
    // recebe o cont que é o n de produtos adicionados na ultima lista (usado na query)
    // percorre cada id que obteve e adiciona á listaProduto
    fun insert(cont :Int) = viewModelScope.launch {
        var lastIdLista = repository.lastId().first()
        if (lastIdLista != null){
            repository.getProductsIds(cont).collect{ ids ->
                ids.forEach { id ->
                    if (id != null) {
                        val listaP = ListaProduto(id_lista = lastIdLista +1 ,id_produto = id)
                        repository.insert(listaP)
                    }
                }
            }
        }else{
            repository.getProductsIds(cont).collect{ ids ->
                ids.forEach { id ->
                    if (id != null) {
                        val listaP = ListaProduto(id_lista = 1, id_produto = id)
                        repository.insert(listaP)
                    }
                }
            }
        }
    }


}


class ListaProdutosViewModelFactory(private val repository: ListaProdutosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaProdutosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListaProdutosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}