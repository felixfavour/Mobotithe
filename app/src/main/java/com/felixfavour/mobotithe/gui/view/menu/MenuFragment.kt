package com.felixfavour.mobotithe.gui.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentMenuBinding
import com.felixfavour.mobotithe.gui.viewModel.MenuViewModel
import com.felixfavour.mobotithe.util.cropCircle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.InputStream

class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var binding: FragmentMenuBinding
    private lateinit var auth: FirebaseAuth

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
        /*
        Circle Profile Image
        */
        binding.profilePicture.setImageDrawable(cropCircle(resources, R.drawable.profile_user))
        /*
        Image Popup menu on profile Picture long click
*/
        binding.profilePicture.setOnLongClickListener {
            showMenu(binding.profilePicture)
        }
        // Navigation
        // Register New Income
        binding.registerNewIncome.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuToRegisterIncomeFragment())
        }

        binding.lifecycleOwner = this
        binding.viewModel = menuViewModel

        return binding.root
    }

    private fun showMenu(anchor: View) : Boolean {
        val picturePopupMenu = PopupMenu(context, anchor)
        picturePopupMenu.menuInflater.inflate(R.menu.profile_picture_menu, picturePopupMenu.menu)
        picturePopupMenu.show()
        picturePopupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.change_profile_picture -> {
                    pickProfileImage()
//                    menuViewModel.changeProfilePicture()
                    true
                }
                R.id.view_profile_picture -> {
                    menuViewModel.viewProfilePicture()
                    true
                }
                R.id.delete_profile_picture -> {
                    menuViewModel.deleteProfilePicture()
                    true
                }
                else -> {
                    // Do nothing
                    true
                }
            }
        }
        return true
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
                menuViewModel.setProfilePicture(context!!.applicationContext, data)
            }
        }
    }


}