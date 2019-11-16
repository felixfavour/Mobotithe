package com.felixfavour.mobotithe.ui.onboardingwelcome

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felixfavour.mobotithe.R

class OnboardingWelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = OnboardingWelcomeFragment()
    }

    private lateinit var viewModel: OnboardingWelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.onboarding_welcome_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OnboardingWelcomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
