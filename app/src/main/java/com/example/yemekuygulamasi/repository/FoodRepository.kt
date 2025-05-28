package com.example.yemekuygulamasi.repository
import com.example.yemekuygulamasi.data.model.BaseResponse
import com.example.yemekuygulamasi.data.model.CartResponse
import com.example.yemekuygulamasi.data.remote.FoodApiService

import com.example.yemekuygulamasi.data.model.FoodResponse
import retrofit2.Call

class FoodRepository(private val apiService: FoodApiService) {

    fun getAllFoods(): Call<FoodResponse> {
        return apiService.getAllFoods()
    }
    fun getCartFoods(kullaniciAdi: String): Call<CartResponse> {
        return apiService.getCartFoods(kullaniciAdi)
    }

    fun addToCart(yemekAdi: String, resimAdi: String, fiyat: Int, adet: Int, kullaniciAdi: String): Call<BaseResponse> {
        return apiService.addToCart(yemekAdi, resimAdi, fiyat, adet, kullaniciAdi)
    }

    fun deleteFromCart(sepetYemekId: Int, kullaniciAdi: String): Call<BaseResponse> {
        return apiService.deleteFromCart(sepetYemekId, kullaniciAdi)
    }



}