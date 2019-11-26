package com.felixfavour.mobotithe.gui.view.income

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.gui.ViewModel.SubmitIncomeViewModel


class SubmitIncomeFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitIncomeFragment()
    }

    private lateinit var viewModel: SubmitIncomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.submit_income_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SubmitIncomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
