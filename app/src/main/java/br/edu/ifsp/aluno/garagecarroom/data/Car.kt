package br.edu.ifsp.aluno.garagecarroom.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Car (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var carModel:String,
    var carYear:String,
    var carColor:String
)