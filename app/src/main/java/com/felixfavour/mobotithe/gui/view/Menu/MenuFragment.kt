package com.felixfavour.mobotithe.gui.view.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        // Navigation
        // Register New Income
        binding.registerNewIncome.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuToRegisterIncomeFragment())
        }


        return binding.root
    }
}