package com.felixfavour.mobotithe.gui.view.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.ProfileFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        return binding.root
    }

}
