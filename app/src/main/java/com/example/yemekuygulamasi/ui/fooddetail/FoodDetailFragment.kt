package com.example.yemekuygulamasi.ui.fooddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.yemekuygulamasi.R
import com.example.yemekuygulamasi.data.model.BaseResponse
import com.example.yemekuygulamasi.data.model.Food
import com.example.yemekuygulamasi.data.remote.ApiUtils
import com.example.yemekuygulamasi.databinding.FragmentFoodDetailBinding

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var food: Food

    private val kullaniciAdi = "test_kullanici"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            food = it.getParcelable("food") ?: run {
                Toast.makeText(requireContext(), "Yemek verisi yok!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                return
            }
        }


        binding.food = food


        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(requireContext()).load(imageUrl).into(binding.foodImage)

        binding.quantityEditText.text = "1"

        binding.minusButton.setOnClickListener {
            val current = binding.quantityEditText.text.toString().toIntOrNull() ?: 1
            if (current > 1) {
                binding.quantityEditText.text = (current - 1).toString()
            }
        }

        binding.plusButton.setOnClickListener {
            val current = binding.quantityEditText.text.toString().toIntOrNull() ?: 1
            binding.quantityEditText.text = (current + 1).toString()
        }

        binding.addToCartButton.setOnClickListener {
            val adetStr = binding.quantityEditText.text.toString()
            val adet = adetStr.toIntOrNull()
            if (adet == null || adet <= 0) {
                Toast.makeText(requireContext(), "Lütfen geçerli bir adet girin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            sepeteYemekEkle(adet)
        }
    }


    private fun sepeteYemekEkle(adet: Int) {
        val fiyatInt = food.yemek_fiyat.toIntOrNull()
        if (fiyatInt == null) {
            Toast.makeText(requireContext(), "Geçersiz fiyat formatı: ${food.yemek_fiyat}", Toast.LENGTH_SHORT).show()
            return
        }

        ApiUtils.apiService.addToCart(
            yemekAdi = food.yemek_adi,
            resimAdi = food.yemek_resim_adi,
            fiyat = fiyatInt,
            adet = adet,
            kullaniciAdi = kullaniciAdi
        ).enqueue(object : retrofit2.Callback<BaseResponse> {
            override fun onResponse(call: retrofit2.Call<BaseResponse>, response: retrofit2.Response<BaseResponse>) {
                if (response.isSuccessful && response.body()?.success == 1) {
                    Toast.makeText(requireContext(), "Sepete eklendi!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_foodDetailFragment_to_cartFragment)
                } else {
                    Toast.makeText(requireContext(), "Sepete eklenemedi: ${response.body()?.message ?: "Bilinmeyen hata"}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<BaseResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Hata: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
