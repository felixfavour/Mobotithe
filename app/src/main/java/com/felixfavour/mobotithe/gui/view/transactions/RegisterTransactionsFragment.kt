package com.felixfavour.mobotithe.gui.view.transactions

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Expense
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.databinding.RegisterTransactionFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.RegisterTransactionsViewModel
import com.felixfavour.mobotithe.gui.viewModel.TransactionsViewModel
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NumberFormatException


class RegisterTransactionsFragment : Fragment() {

    private lateinit var viewModel: RegisterTransactionsViewModel
    private lateinit var binding: RegisterTransactionFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Initialize important variables
        binding = DataBindingUtil.inflate(inflater, R.layout.register_transaction_fragment,container, false)
        viewModel = ViewModelProviders.of(this).get(RegisterTransactionsViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val isIncome = RegisterTransactionsFragmentArgs.fromBundle(arguments!!).isIncome
        val intervalsSpinner = binding.interval

        /*
        Array Adapter for Intervals Spinner
        */
        ArrayAdapter.createFromResource(this.context!!.applicationContext, R.array.intervals, R.layout.spinner_popup_item).also {adapter ->
            intervalsSpinner.adapter = adapter
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_popup_item)

        }

        /*
        Listen to LiveData to know when the
        Transaction has been fully registered and submitted to
        the database.
        */
        viewModel.taskStatus.observe(this, Observer { taskStatus ->
            if(taskStatus == TaskAssesor.PASS) {
                Snackbar.make(view!!, "Transaction was Successfully Registered", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view!!, "Transaction was Successfully Registered", Snackbar.LENGTH_SHORT).show()
            }
        })

        // Change Button Text based on [isIncome] LiveData boolean
        if (isIncome) {
            binding.submitTransaction.text = getString(R.string.register_income)
        } else {
            binding.submitTransaction.text = getString(R.string.register_expense)
        }

        binding.submitTransaction.setOnClickListener {

            // Check if Transaction is Income or Expense
            if (isIncome) {

                // Set income to follow the pattern of Income class in Entities
                var userIncome: Income
                try {
                    userIncome = Income(
                        binding.incomeCategory.text.toString(),
                        intervalsSpinner.selectedItem as String,
                        binding.amount.text.toString().toLong())
                } catch (ex: NumberFormatException) {
                    userIncome = Income(
                        binding.incomeCategory.text.toString(),
                        intervalsSpinner.selectedItem as String,
                        0)
                }

//                  Save newly defined income to database
                viewModel.saveIncome(userIncome)
            } else {

                // Set expense to follow the pattern of Expense class in Entities
                var userExpense: Expense
                try {
                    userExpense = Expense(
                        binding.incomeCategory.text.toString(),
                        intervalsSpinner.selectedItem as String,
                        binding.amount.text.toString().toLong())
                } catch (ex: NumberFormatException) {
                    userExpense = Expense(
                        binding.incomeCategory.text.toString(),
                        intervalsSpinner.selectedItem as String,
                        0)
                }

//            Save newly defined income to database
                viewModel.saveExpense(userExpense)
            }

        }

        return binding.root
    }

}
