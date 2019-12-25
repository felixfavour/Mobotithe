package com.felixfavour.mobotithe.gui.view.transactions.income

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.databinding.RegisterIncomeFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.RegisterIncomeViewModel
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NumberFormatException


class RegisterIncomeFragment : Fragment() {

    private lateinit var viewModel: RegisterIncomeViewModel
    private lateinit var binding: RegisterIncomeFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    companion object {
        const val TAG = "SignUpFragment"
        const val USERS_COLLECTION = "users"
        const val PREF = "my_preferences"
        const val RC_SIGN_IN = 9001
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.register_income_fragment,container, false)
        viewModel = ViewModelProviders.of(this).get(RegisterIncomeViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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
        viewModel.taskStatus.observe(this, androidx.lifecycle.Observer { taskStatus ->
            if(taskStatus == TaskAssesor.PASS) {
                Snackbar.make(view!!, "Transaction was Successfully Registered", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view!!, "Transaction was Successfully Registered", Snackbar.LENGTH_SHORT).show()
            }
        })

        binding.submitIncome.setOnClickListener {

            // Set income to follow the pattern of Transaction class in Entities
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

//            Save newly defined income to database
            viewModel.saveIncome(userIncome)
        }

        return binding.root
    }

}
