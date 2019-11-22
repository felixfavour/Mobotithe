package com.felixfavour.mobotithe.gui.View.income

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

//        val spinnerAdapter = ArrayAdapter(context, )

        return binding.root
    }

}
