package com.felixfavour.mobotithe.gui.view.income

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.DataBinderMapperImpl
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.felixfavour.mobotithe.databinding.IncomeCategoryItemBinding
import com.felixfavour.mobotithe.databinding.SubmitIncomeFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.SubmitIncomeViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class SubmitIncomeFragment : Fragment() {

    private lateinit var viewModel: SubmitIncomeViewModel
    private lateinit var binding: SubmitIncomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(SubmitIncomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.submit_income_fragment, container, false)

        //Get Default date from Calendar
        val date = Date(binding.calendar.date)

        binding.submitAmount.setOnClickListener {

            // Creation of an IncomeHistory Object
            val income = IncomeHistory(
                date,
                binding.amount.text.toString().toDouble(),
                Income("Test", "Weekly", 500.00)
            )

            viewModel.submitIncome(income, view!!, context!!.applicationContext)
        }

        return binding.root
    }

}
