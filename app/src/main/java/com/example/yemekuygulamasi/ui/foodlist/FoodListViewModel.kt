package com.example.yemekuygulamasi.ui.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yemekuygulamasi.data.model.Food
import com.example.yemekuygulamasi.repository.FoodRepository
import com.example.yemekuygulamasi.data.remote.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodListViewModel : ViewModel() {
    private val _foodList = MutableLiveData<List<Food>>()
    val foodList: LiveData<List<Food>> get() = _foodList

    private var fullList: List<Food> = listOf()

    private val repository = FoodRepository(ApiUtils.apiService)

    fun loadFoods() {
        repository.getAllFoods().enqueue(object : Callback<com.example.yemekuygulamasi.data.model.FoodResponse> {
            override fun onResponse(
                call: Call<com.example.yemekuygulamasi.data.model.FoodResponse>,
                response: Response<com.example.yemekuygulamasi.data.model.FoodResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.yemekler?.let { list ->
                        fullList = list
                        _foodList.value = list
                    }
                }
            }

            override fun onFailure(call: Call<com.example.yemekuygulamasi.data.model.FoodResponse>, t: Throwable) {
                _foodList.value = emptyList()
            }
        })
    }

    fun searchFoods(query: String) {
        val filtered = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter { it.yemek_adi.contains(query, ignoreCase = true) }
        }
        _foodList.value = filtered
    }
}
