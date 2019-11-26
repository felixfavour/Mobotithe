package com.felixfavour.mobotithe.gui.view.income

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.RegisterIncomeFragmentBinding
import com.felixfavour.mobotithe.gui.ViewModel.RegisterIncomeViewModel


class RegisterIncomeFragment : Fragment() {

    private lateinit var viewModel: RegisterIncomeViewModel
    private lateinit var binding: RegisterIncomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.register_income_fragment,container, false)
        viewModel = ViewModelProviders.of(this).get(RegisterIncomeViewModel::class.java)

        val incomeCategorySpinner = binding.incomeCategory
/*
        Array Adapter for Income Category Spinner Spinner
*/
//        ArrayAdapter.createFromResource(this.context!!.applicationContext, R.array.intervals, R.layout.spinner_popup_item).also {adapter ->
//            intervalsSpinner.adapter = adapter
//            adapter.setDropDownViewResource(R.layout.spinner_dropdown_popup_item)
//        }

        val intervalsSpinner = binding.interval
/*
        Array Adapter for Intervals Spinner
*/
        ArrayAdapter.createFromResource(this.context!!.applicationContext, R.array.intervals, R.layout.spinner_popup_item).also {adapter ->
            intervalsSpinner.adapter = adapter
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_popup_item)
        }

        return binding.root
    }

}
