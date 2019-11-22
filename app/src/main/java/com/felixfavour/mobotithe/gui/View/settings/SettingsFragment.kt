package com.felixfavour.mobotithe.gui.View.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when(position) {
            0 -> Toast.makeText(parent.getContext(), "OnItemSelectedListener : $position", Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(parent.getContext(), "OnItemSelectedListener : $position", Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(parent.getContext(), "OnItemSelectedListener : $position", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Do Nothing
    }


}
