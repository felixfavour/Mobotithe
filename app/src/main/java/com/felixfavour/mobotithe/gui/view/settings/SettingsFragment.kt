package com.felixfavour.mobotithe.gui.view.settings

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
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentSettingsBinding
import com.felixfavour.mobotithe.util.ThemeHelper

class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        private const val PREF = "theme_preferences"
        private const val SPINNER_STATE = "current_spinner_selection"
        private lateinit var preferences: SharedPreferences
    }

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = context!!.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.visitDeveloper.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWebFragment())
        }

        binding.license.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLicensesFragment())
        }

        val spinner = binding.uiMode

        ArrayAdapter.createFromResource(this.context!!.applicationContext, R.array.ui_mode, R.layout.spinner_popup_item).also {adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_popup_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this
        spinner.setSelection(preferences.getInt(SPINNER_STATE, 0))

        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position) {
            0 -> ThemeHelper.setLightTheme(preferences, position)
            1 -> ThemeHelper.setDarkTheme(preferences, position)
            2 -> ThemeHelper.setThemeByBatterySaver(preferences, position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do Nothing
    }


}
