package com.felixfavour.mobotithe.gui.view.transactions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.felixfavour.mobotithe.gui.view.transactions.expense.ExpensesFragment
import com.felixfavour.mobotithe.gui.view.transactions.income.IncomeFragment

class TransactionsVPAdapter(private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val NUM_OF_PAGES = 2
    }

    override fun getItemCount(): Int {
        return NUM_OF_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IncomeFragment()
            1 -> ExpensesFragment()
            else -> IncomeFragment()
        }
    }

}