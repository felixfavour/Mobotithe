package com.felixfavour.mobotithe.gui.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentHistoryBinding
import com.felixfavour.mobotithe.gui.viewModel.HistoryViewModel
import com.google.firebase.Timestamp

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        return binding.root
    }
}