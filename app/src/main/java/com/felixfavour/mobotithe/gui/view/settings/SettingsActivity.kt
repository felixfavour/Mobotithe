package com.felixfavour.mobotithe.gui.view.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.SettingsActivityBinding
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {


    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.settings_activity, container, false)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.settings_nav_host_fragment)

        // Change ActionBar to toolbar
        setSupportActionBar(binding.toolbar)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.settings_nav_host_fragment)
        NavigationUI.navigateUp(navController, AppBarConfiguration(navController.graph))
        return true
    }

}
