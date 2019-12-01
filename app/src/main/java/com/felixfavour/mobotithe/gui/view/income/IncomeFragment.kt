package com.felixfavour.mobotithe.gui.view.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentIncomeBinding
import com.felixfavour.mobotithe.gui.viewModel.IncomeViewModel

class IncomeFragment : Fragment() {

    private lateinit var incomeViewModel: IncomeViewModel
    private lateinit var binding: FragmentIncomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)

        // NAvigation
        // Add Income Category
        binding.addIncomeCategory.setOnClickListener {
            findNavController().navigate(IncomeFragmentDirections.actionIncomeToRegisterIncomeFragment())
        }
        return binding.root
    }
}