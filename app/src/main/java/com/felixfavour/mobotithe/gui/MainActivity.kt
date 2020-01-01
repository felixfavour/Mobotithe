package com.felixfavour.mobotithe.gui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.ActivityMainBinding
import com.felixfavour.mobotithe.gui.view.settings.SettingsActivity
import com.felixfavour.mobotithe.util.ThemeHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val appBarConfiguration = AppBarConfiguration(setOf(R.id.Menu, R.id.Transaction, R.id.History, R.id.Profile))
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG = "Main Activity"
        private const val PREF = "theme_preferences"
        private lateinit var preferences: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
/*
        Important theme changes that should take effect immediately
        after the instantiation of the class
*/
        setTheme(R.style.AppTheme_NoActionBar)
        preferences = this.getSharedPreferences(PREF, Context.MODE_PRIVATE)

        ThemeHelper.useDefaultTheme(preferences)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Main Activity has been created")

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, container, false)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)

/*
        Change ActionBar to toolbar
*/
        setSupportActionBar(binding.toolbar)



        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
/*
         Passing each menu ID as a set of Ids because each
         menu should be considered as top level destinations.
*/

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this.applicationContext)
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu!!.get(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val settingsIntent = Intent(this, SettingsActivity::class.java)
        
        when(item.itemId) {
            R.id.menu_settings -> {
                startActivity(settingsIntent)
            }
        }
        return true
    }
}
