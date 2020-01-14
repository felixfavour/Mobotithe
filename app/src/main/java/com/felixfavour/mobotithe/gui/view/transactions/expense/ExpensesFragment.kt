package com.felixfavour.mobotithe.gui.view.transactions.expense

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Transaction
import com.felixfavour.mobotithe.databinding.ExpensesFragmentBinding
import com.felixfavour.mobotithe.gui.view.transactions.TransactionsFragmentDirections
import com.felixfavour.mobotithe.gui.viewModel.ExpensesViewModel
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ExpensesFragment : Fragment() {

    private lateinit var viewModel: ExpensesViewModel
    private lateinit var binding: ExpensesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ExpensesViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.expenses_fragment, container, false)

        // Navigation

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Set on Swipe Refresh Listener
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getExpenseCategories()
            viewModel.errorStatus.observe(this, Observer { task ->
                if (task == TaskAssesor.FAIL || task == TaskAssesor.PASS) {
                    binding.swipeRefresh.isRefreshing = false
                }
            })
        }

        // Add OnClickListener to the list items in the recyclerView
        binding.recyclerView.adapter = ExpenseCategoryAdapter(ExpenseCategoryAdapter.OnExpenseClickListener { expense->
            val transaction = Transaction(
                income = null,
                expense = expense,
                getIsIncome = false,
                transactionName = expense.name
            )
            val action = TransactionsFragmentDirections.actionTransactionToSubmitIncomeFragment(transaction)
            findNavController().navigate(action)
        }, ExpenseCategoryAdapter.OnExpenseLongClickListener { expense ->

            val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.deleteExpense.setOnClickListener {
                viewModel.deleteExpensesCategories(expense)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        })

        isExpenseEmpty()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getExpenseCategories()
    }

    private fun isExpenseEmpty() {
        viewModel.selectedExpenses.observe(this, Observer {expenses->
            if (expenses.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyWalletLayout.visibility = View.VISIBLE

            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyWalletLayout.visibility = View.GONE
            }
        })
    }

}
