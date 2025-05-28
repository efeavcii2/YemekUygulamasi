package com.example.yemekuygulamasi

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.yemekuygulamasi.R.id.topAppBar
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(topAppBar)
        setSupportActionBar(toolbar)


        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        toolbarTitle.setTextColor(Color.BLACK)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.foodListFragment -> toolbarTitle.text = "Yemek Listesi"
                R.id.foodDetailFragment -> toolbarTitle.text = "Yemek Detayları"
                R.id.cartFragment -> toolbarTitle.text = "Sepet"
                else -> toolbarTitle.text = ""
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }
}
