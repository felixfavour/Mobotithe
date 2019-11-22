package com.felixfavour.mobotithe.gui.View.settings


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentWebBinding

/**
 * A simple [Fragment] subclass.
 */
class WebFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentWebBinding>(inflater, R.layout.fragment_web, container, false)

        binding.webBrowser.loadUrl("http://www.github.com/felixfavour")

        return binding.root
    }


}
