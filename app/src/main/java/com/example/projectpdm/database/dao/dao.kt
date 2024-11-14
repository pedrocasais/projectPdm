package com.example.projectpdm.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectpdm.database.ListaProduto
import com.example.projectpdm.database.Listas
import com.example.projectpdm.database.Produtos
import com.example.projectpdm.database.priceQuantidade
import com.example.projectpdm.listaModel
import kotlinx.coroutines.flow.Flow


@Dao
interface ListasDao {
    //inserir dados na tabela lista
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listas: Listas)

    // obter last id da lista
    @Query("SELECT id_listas FROM listas ORDER BY id_listas DESC LIMIT 1")
    fun lastId(): Flow<Int?>

    //obter preco do produto e a quantidade desse produto
    @Query("""
    SELECT p.price, p.quantidade
    FROM produtos p
    INNER JOIN listaProduto lp ON p.id = lp.id_produto
    INNER JOIN listas l ON l.id_listas = lp.id_lista
    WHERE l.id_listas = :id
    """)
    fun getPrice(id:Int): Flow<List<priceQuantidade>>

    //update a lista com o preco dos produtos*quantidade
    @Query("UPDATE listas SET price = :price WHERE id_listas = :id")
    suspend fun updateLista(id: Int,price:Float)

    //obter dados da lista para o recycler viwer
    @Query("SELECT nome, data , price FROM listas")
    fun getRecycler(): Flow<List<listaModel>>

    //obter a soma do preco total de listas entre duas datas
    @Query("SELECT SUM(price) FROM listas WHERE data BETWEEN :data1 AND :data2 ")
    fun getTotal(data1:String,data2:String): Flow<Float>

}

@Dao
interface ProdutosDao {
    //inserir dados na tabela prrodutos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(produtos: Produtos)

    // obter ultiimo id da tabela produtos
    @Query("SELECT id FROM Produtos ORDER BY id DESC LIMIT 1")
    fun lastId(): Flow<Int?>

}


@Dao
interface ListaProdutosDao {
    // obter ultiimo id de uma lista da tabela listas
    @Query("SELECT id_listas FROM listas ORDER BY id_listas DESC LIMIT 1")
    fun lastId(): Flow<Int?>

    // obter ids dos ultimos cont(n de produtos) produtos (ordem mais recente)
    @Query("SELECT id FROM produtos ORDER BY id DESC LIMIT :cont")
    fun getProductsIds(cont : Int) : Flow<List<Int?>>

    //inserir na tabela listaproduto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ids: ListaProduto)

}

