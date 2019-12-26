package com.felixfavour.mobotithe.gui.view.transactions

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.TransactionsFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.TransactionsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TransactionsFragment : Fragment() {

    private lateinit var viewModel: TransactionsViewModel
    private lateinit var binding: TransactionsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(TransactionsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.transactions_fragment, container, false)

        binding.transactionsPager.adapter = TransactionsVPAdapter(this.requireActivity())

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do nothing
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    modifyIncomeTab()
                }else {
                    modifyExpenseTab()
                }
            }

        })

        binding.transactionsPager.adapter = TransactionsVPAdapter(this.requireActivity())

        TabLayoutMediator(binding.tabLayout, binding.transactionsPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = getString(R.string.incomes)
                }
                1 -> {
                    tab.text = getString(R.string.expenses)
                }
            }
        }.attach()

        return binding.root
    }

    private fun modifyIncomeTab() {
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(TransactionsFragmentDirections.actionTransactionToRegisterIncomeFragment2(true))
        }
    }

    private fun modifyExpenseTab() {
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(TransactionsFragmentDirections.actionTransactionToRegisterIncomeFragment2(false))
        }
    }

}
