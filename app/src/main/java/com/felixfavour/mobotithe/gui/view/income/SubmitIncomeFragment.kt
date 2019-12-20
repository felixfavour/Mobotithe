package com.felixfavour.mobotithe.gui.view.income

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.DataBinderMapperImpl
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.IncomeCategoryItemBinding
import com.felixfavour.mobotithe.databinding.SubmitIncomeFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.SubmitIncomeViewModel


class SubmitIncomeFragment : Fragment() {

    private lateinit var viewModel: SubmitIncomeViewModel
    private lateinit var binding: SubmitIncomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.submit_income_fragment, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.submit_income_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
