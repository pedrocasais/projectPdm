package com.example.projectpdm.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//tabela lista
@Entity(tableName = "listas")
data class Listas(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_listas")
    val id: Int=0,
    @ColumnInfo(name = "nome")
    val nome: String,
    @ColumnInfo(name = "data")
    val data: String,
    @ColumnInfo(name= "price")
    val price: Float
)

//tabela produtos
@Entity(tableName = "produtos")
data class  Produtos(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int=0,
    @ColumnInfo(name = "nome")
    val nome:String,
    @ColumnInfo(name = "price")
    val price: Float,
    @ColumnInfo(name = "categoria")
    val categoria: String,
    @ColumnInfo(name = "quantidade")
    val quantidade: Int
    )

//tabela listaProdutos tem duas foregeins keys
//o  id lista da tabela lista
//e o id produto da tabela produtos
@Entity(tableName = "listaProduto",
    foreignKeys = [
        ForeignKey(
            entity = Listas::class,
            parentColumns = arrayOf("id_listas"),
            childColumns = arrayOf("id_lista"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
            ),
        ForeignKey(
            entity = Produtos::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_produto"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ])
data class ListaProduto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "id_lista")
    val id_lista :Int,
    @ColumnInfo(name = "id_produto")
    val id_produto :Int/*,
    @ColumnInfo(name = "quantidade")
    val quantidade :Int
    */
)

//TODO tabela listaAnte para guardar historico de precos
@Entity
data class listaAnte(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id :Int = 0
)

// data class para guardar resultados da query que calcula os precos de uma lista
data class priceQuantidade(
    val price: Float,
    val quantidade: Int
)