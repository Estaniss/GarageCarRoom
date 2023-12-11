package br.edu.ifsp.aluno.garagecarroom.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CarDAO {
    @Insert
    suspend fun insert(car: Car)

    @Update
    suspend fun update (car: Car)

    @Delete
    suspend fun delete(car: Car)

    @Query("SELECT * FROM car ORDER BY carModel")
    fun getAllCars(): LiveData<List<Car>>

    @Query("SELECT * FROM Car WHERE id=:id")
    fun getCarById(id: Int): LiveData<Car>
}