package com.felixfavour.mobotithe.gui.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.felixfavour.mobotithe.databinding.FragmentHistoryBinding
import com.felixfavour.mobotithe.gui.viewModel.HistoryViewModel
import com.google.firebase.Timestamp

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = historyViewModel

        val adapter = HistoryAdapter()
        binding.incomeHistoriesList.adapter = adapter

        return binding.root
    }
}