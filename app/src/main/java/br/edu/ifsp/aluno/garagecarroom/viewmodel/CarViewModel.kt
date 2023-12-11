package br.edu.ifsp.aluno.garagecarroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.aluno.garagecarroom.data.Car
import br.edu.ifsp.aluno.garagecarroom.data.CarDatabase
import br.edu.ifsp.aluno.garagecarroom.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel (application: Application): AndroidViewModel(application) {
    private val repository: CarRepository
    var allCars : LiveData<List<Car>>
    lateinit var car : LiveData<Car>
    init {
        val dao = CarDatabase.getDatabase(application).carDAO()
        repository = CarRepository(dao)
        allCars = repository.getAllCars()
    }
    fun insert(car: Car) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(car)
    }

    fun update(car: Car) = viewModelScope.launch(Dispatchers.IO){
        repository.update(car)
    }
    fun delete(car: Car) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(car)
    }

    fun getContactById(id: Int) {
        viewModelScope.launch {
            car = repository.getCarById(id)
        }
    }
}