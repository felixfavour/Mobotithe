package com.felixfavour.mobotithe.gui.View.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentSettingsBinding
import com.felixfavour.mobotithe.util.ThemeHelper

class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {


    companion object {
        private const val PREF = "theme_preferences"
    }

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val preferences = context!!.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        ThemeHelper.setDefaultTheme(preferences)

        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.visitDeveloper.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWebFragment())
        }

        binding.license.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLicensesFragment())
        }

        val spinner = binding.uiMode

        ArrayAdapter.createFromResource(this.context!!.applicationContext, R.array.ui_mode, R.layout.support_simple_spinner_dropdown_item).also {adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_popup_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val preferences = context!!.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        when(position) {
            0 -> ThemeHelper.setLightTheme(preferences)
            1 -> ThemeHelper.setDarkTheme(preferences)
            2 -> ThemeHelper.setThemeByBatterySaver(preferences)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Do Nothing
    }


}
