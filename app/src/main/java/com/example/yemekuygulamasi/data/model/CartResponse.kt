package com.example.yemekuygulamasi.data.model

data class CartResponse(
    val sepet_yemekler: List<CartFood>,
    val success: Int
)
