package com.example.yemekuygulamasi.ui.cart

import CartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yemekuygulamasi.R
import com.example.yemekuygulamasi.data.model.CartFood
import com.example.yemekuygulamasi.data.model.BaseResponse
import com.example.yemekuygulamasi.data.model.CartResponse
import com.example.yemekuygulamasi.data.remote.ApiUtils
import com.example.yemekuygulamasi.databinding.FragmentCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CartAdapter
    private val kullaniciAdi = "test_kullanici"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddFood.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_foodListFragment)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CartAdapter(
            onItemClick = { cartFood ->
                Toast.makeText(requireContext(), "${cartFood.yemek_adi} seçildi", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { cartFood ->
                sepettenYemekSil(cartFood)
            }
        )
        binding.recyclerView.adapter = adapter


        binding.buttonSiparisiOnayla.setOnClickListener {
            Toast.makeText(requireContext(), "Yemeğiniz hazırlanıyor", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_cartFragment_to_paymentFragment)
        }

        loadCartFoods()
    }

    private fun loadCartFoods() {
        ApiUtils.apiService.getCartFoods(kullaniciAdi).enqueue(object : Callback<CartResponse> {
            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ) {
                if (response.isSuccessful) {
                    val cartList = response.body()?.sepet_yemekler ?: emptyList()
                    adapter.submitList(cartList)
                    hesaplaToplamTutar(cartList)
                } else {
                    Toast.makeText(requireContext(), "Sepet yüklenemedi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Hata: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sepettenYemekSil(cartFood: CartFood) {
        val sepetYemekIdInt = cartFood.sepet_yemek_id.toInt()
        ApiUtils.apiService.deleteFromCart(sepetYemekIdInt, kullaniciAdi).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == 1) {
                    Toast.makeText(requireContext(), "${cartFood.yemek_adi} sepetten silindi", Toast.LENGTH_SHORT).show()
                    loadCartFoods()
                } else {
                    Toast.makeText(requireContext(), "Silme işlemi başarısız", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Hata: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun hesaplaToplamTutar(sepetList: List<CartFood>) {
        val toplamTutar = sepetList.sumOf { it.yemek_fiyat.toInt() * it.yemek_siparis_adet.toInt() }
        binding.textViewToplamTutar.text = "Toplam: $toplamTutar ₺"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
