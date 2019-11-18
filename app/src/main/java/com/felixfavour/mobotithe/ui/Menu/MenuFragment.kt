package com.felixfavour.mobotithe.ui.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        return binding.root
    }
}