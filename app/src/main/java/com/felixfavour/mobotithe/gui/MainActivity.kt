package com.felixfavour.mobotithe.gui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.ActivityMainBinding
import com.felixfavour.mobotithe.gui.View.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val appBarConfiguration = AppBarConfiguration(setOf(R.id.Menu, R.id.Income, R.id.History))
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, container, false)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

        // Change ActionBar to toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this.applicationContext)
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val settingsIntent = Intent(this, SettingsActivity::class.java)
        
        when(item.itemId) {
            R.id.menu_settings -> startActivity(settingsIntent)
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
