package com.felixfavour.mobotithe.gui.view.menu

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.TypedValue
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.ConfigurationCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentMenuBinding
import com.felixfavour.mobotithe.gui.viewModel.MenuViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.LabelFormatter
import com.jjoe64.graphview.Viewport
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.lang.NumberFormatException
import java.text.NumberFormat

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var binding: FragmentMenuBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val TAG = "SignUpFragment"
        const val USERS_COLLECTION = "users"
        const val PICK_PROFILE_PHOTO = 1
        const val PREF = "my_preferences"
        const val RC_SIGN_IN = 9001
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()

        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        // GET currency using CURRENT LOCALE
//        val locationServices = LocationServices.getFusedLocationProviderClient(this.context!!)
//        locationServices.lastLocation.addOnCompleteListener {
//            val location =  it.result
//        }

        val locale = ConfigurationCompat.getLocales(resources.configuration)[0]
        menuViewModel.getCurrency(locale)

        //----------------------------------------------------------------------------------------------------------------------------
        // Constructing Graph
        var x = 0.0
        var y = 0.0
        menuViewModel.weeksTotalIncome.observe(this, Observer {totalIncome ->
            x = totalIncome.toDouble()
            menuViewModel.weeksTotalExpense.observe(this, Observer {totalExpense ->
                y = totalExpense.toDouble()
                val graphSeries: BarGraphSeries<DataPoint> = BarGraphSeries(arrayOf(DataPoint(x, y)))
                binding.graph.addSeries(graphSeries)
            })
        })


        val numberFormatter = NumberFormat.getInstance()
        numberFormatter.apply {
            minimumIntegerDigits = 1
            maximumIntegerDigits = 8
        }

            //-----------------------------------
            // Formatting the default labels
        binding.graph.gridLabelRenderer.apply {
            labelFormatter = object : DefaultLabelFormatter(numberFormatter, numberFormatter) {
                override fun formatLabel(value: Double, isValueX: Boolean): String {
                    return super.formatLabel(value, isValueX) + " ${menuViewModel.currency.value}"
                }
            }
            gridColor = Color.parseColor("#002d95")
            textSize = 15f
        }

        //----------------------------------------------------------------------------------------------------------------------------

        // get greeting text and Image
        val greeting = menuViewModel.getGreeting(context!!.applicationContext, binding)
        binding.greetingText.text = greeting

        // Navigation
        // Register New Transactione
        binding.registerNewTransaction.setOnClickListener {
            MaterialAlertDialogBuilder(this.context).setMessage(getString(R.string.what_transaction_are_you_registering))
                .setPositiveButton(getString(R.string.income)) { _, _ ->
                    findNavController().navigate(MenuFragmentDirections.actionMenuToRegisterIncomeFragment(true))
                }.setNegativeButton(getString(R.string.expense)) { _, _ ->
                    findNavController().navigate(MenuFragmentDirections.actionMenuToRegisterIncomeFragment(false))
                }.show()
        }

        //Refresh the info on Swipe-Refresh
        binding.dashboardRefresh

        binding.dashboardRefresh.isRefreshing = false

        // Set Amount TextView to automatically resize text based on size and volume
        TextViewCompat.setAutoSizeTextTypeWithDefaults(binding.totalSavings, TypedValue.COMPLEX_UNIT_DIP)

        binding.lifecycleOwner = this
        binding.viewModel = menuViewModel

        return binding.root
    }


}