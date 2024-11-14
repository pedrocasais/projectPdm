package com.example.projectpdm.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectpdm.database.dao.ListaProdutosDao
import com.example.projectpdm.database.dao.ListasDao
import com.example.projectpdm.database.dao.ProdutosDao

/*
    cria uma room db com as tabelas listas, produtos, listaProduto e listaAnte
 */
@Database(entities = arrayOf(Listas::class,Produtos::class,ListaProduto::class,listaAnte::class),
    version = 11,
    exportSchema = false)
public abstract class ListasDatabase : RoomDatabase() {

        abstract fun ListasDao(): ListasDao
        abstract fun ProdutosDao(): ProdutosDao
        abstract fun ListaProdutosDao(): ListaProdutosDao


    companion object {
        //previne várias instancias da db abrirem ao mesmo tempo
            @Volatile
            private var INSTANCE: ListasDatabase? = null

            fun getDatabase(context: Context): ListasDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ListasDatabase::class.java,
                        "note_database2"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    instance
                }
            }
        }
    }




