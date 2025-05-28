package com.example.yemekuygulamasi.data.remote

import com.example.yemekuygulamasi.data.model.BaseResponse
import com.example.yemekuygulamasi.data.model.CartResponse
import com.example.yemekuygulamasi.data.model.FoodResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FoodApiService {


    @GET("tumYemekleriGetir.php")
    fun getAllFoods(): Call<FoodResponse>


    @FormUrlEncoded
    @POST("sepettekiYemekleriGetir.php")
    fun getCartFoods(
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<CartResponse>

    @FormUrlEncoded
    @POST("sepettenYemekSil.php")
    fun deleteFromCart(
        @Field("sepet_yemek_id") sepetYemekId: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<BaseResponse>


    @FormUrlEncoded
    @POST("sepeteYemekEkle.php")
    fun addToCart(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") resimAdi: String,
        @Field("yemek_fiyat") fiyat: Int,
        @Field("yemek_siparis_adet") adet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<BaseResponse>





}