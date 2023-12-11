package br.edu.ifsp.aluno.garagecarroom.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.aluno.garagecarroom.data.Car
import br.edu.ifsp.aluno.garagecarroom.data.CarDAO

class CarRepository (private val carDAO: CarDAO) {
    suspend fun insert(car: Car){
        carDAO.insert(car)
    }

    suspend fun update(car: Car){
        carDAO.update(car)
    }

    suspend fun delete(car: Car){
        carDAO.delete(car)
    }

    fun getAllCars(): LiveData<List<Car>> {
        return carDAO.getAllCars()
    }
    fun getCarById(id: Int): LiveData<Car>{
        return carDAO.getCarById(id)
    }
}