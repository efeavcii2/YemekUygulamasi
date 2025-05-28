package com.example.yemekuygulamasi.data.model
import com.example.yemekuygulamasi.data.model.Food


data class FoodResponse(
    val yemekler: List<Food>,
    val success: Int
)
