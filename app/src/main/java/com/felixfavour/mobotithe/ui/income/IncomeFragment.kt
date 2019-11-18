package com.felixfavour.mobotithe.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentIncomeBinding

class IncomeFragment : Fragment() {

    private lateinit var incomeViewModel: IncomeViewModel
    private lateinit var binding: FragmentIncomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)
        return binding.root
    }
}