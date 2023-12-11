package br.edu.ifsp.aluno.garagecarroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class], version = 1)

abstract class CarDatabase: RoomDatabase() {
    abstract fun carDAO(): CarDAO
    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null
        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "garageroom.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}