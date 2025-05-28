package com.example.yemekuygulamasi.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yemekuygulamasi.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonOdemeyiTamamla.setOnClickListener {
            val isim = binding.editCardName.text.toString()
            val kartNo = binding.editCardNumber.text.toString()
            val tarih = binding.editExpiry.text.toString()
            val cvv = binding.editCvv.text.toString()

            if (isim.isBlank() || kartNo.length != 16 || tarih.isBlank() || cvv.length != 3) {
                Toast.makeText(requireContext(), "Lütfen tüm bilgileri doğru giriniz", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Ödeme başarılı 🎉", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
