package com.felixfavour.mobotithe.gui.view.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.ProfileFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.ProfileViewModel
import com.felixfavour.mobotithe.gui.viewModel.ProfileViewModel.Companion.PICK_PROFILE_PHOTO
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var activityMain: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMain = activity as AppCompatActivity
    }

    override fun onStop() {
        super.onStop()
        activityMain.supportActionBar!!.show()
    }

    override fun onPause() {
        super.onPause()
        activityMain.supportActionBar!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityMain.supportActionBar!!.show()
    }

    override fun onStart() {
        super.onStart()
        activityMain.supportActionBar!!.hide()
    }

    override fun onResume() {
        super.onResume()
        activityMain.supportActionBar!!.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Hide the Action Bar
        activityMain.supportActionBar!!.hide()

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.setProfilePicture.setOnClickListener {
            pickProfileImage()
        }

        binding.saveUserData.setOnClickListener {
            viewModel.updateUserProfile(hashMapOf(
                "firstName" to binding.inputFirstname.text.toString(),
                "lastName" to binding.inputLastname.text.toString(),
                "middleName" to binding.inputMiddleName.text.toString(),
                "username" to binding.inputUsername.text.toString(),
                "weeklyBudget" to binding.inputWeeklyBudget.text.toString().toLong()
            ))
            viewModel.taskAssesor.observe(this, Observer { task ->
                if (task == TaskAssesor.PASS) {
                    Snackbar.make(view!!, getString(R.string.updated_profile_successfully), Snackbar.LENGTH_SHORT)
                } else if (task == TaskAssesor.FAIL) {
                    Snackbar.make(view!!, getString(R.string.updated_profile_unsuccessfully), Snackbar.LENGTH_SHORT)
                } else {
                    Snackbar.make(view!!, getString(R.string.will_be_updated), Snackbar.LENGTH_SHORT)
                }
            })
        }

        return binding.root
    }

    private fun pickProfileImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).run {
            setType("image/*")
        }
        startActivityForResult(intent, PICK_PROFILE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PROFILE_PHOTO) {
            if (data == null) {
                Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "image was selected", Toast.LENGTH_SHORT).show()
                viewModel.setProfilePicture(context!!.applicationContext, data)
            }
        }
    }

}
