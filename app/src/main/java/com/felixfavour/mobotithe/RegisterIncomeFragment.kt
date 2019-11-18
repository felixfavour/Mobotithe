package com.felixfavour.mobotithe

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class RegisterIncomeFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterIncomeFragment()
    }

    private lateinit var viewModel: RegisterIncomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_income_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterIncomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
