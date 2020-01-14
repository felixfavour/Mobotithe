package com.felixfavour.mobotithe.gui.viewModel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.User
import com.felixfavour.mobotithe.databinding.ProfileFragmentBinding
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val firestoreImage = storage.reference.child("images/profile_pictures/${auth.uid}")

    private val _currentUser = MutableLiveData<User>()
    val currentUser : LiveData<User>
        get() = _currentUser

    private val _photoUrl = MutableLiveData<Uri>()
    val photoUrl : LiveData<Uri>
        get() = _photoUrl

    private val _taskAssesor = MutableLiveData<TaskAssesor>()
    val taskAssesor : LiveData<TaskAssesor>
        get() = _taskAssesor

    init {
        getUserProfile()
        getPhotoUrl()
    }

    companion object {
        const val USERS_COLLECTION = "users"
        const val PICK_PROFILE_PHOTO = 1
    }

    private fun getPhotoUrl() {
        //var storageReference = Uri.EMPTY
        storage.reference.child("images/profile_pictures/${auth.uid}").downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _photoUrl.value = task.result
            } else {
                _photoUrl.value = auth.currentUser?.photoUrl
            }
        }
    }

    private fun getUserProfile() {
        firestore.collection(USERS_COLLECTION).document(auth.uid!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _currentUser.value = task.result?.toObject(User::class.java)
            }
        }
    }

    fun updateUserProfile(data: HashMap<String, Any>) {
        firestore.collection(USERS_COLLECTION).document(auth.uid!!).update(data).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                _taskAssesor.value = TaskAssesor.PASS
            } else {
                _taskAssesor.value = TaskAssesor.FAIL
            }
        }
    }

    fun setProfilePicture(context: Context, data: Intent) {
        val profileImage = context.contentResolver?.openInputStream(data.data!!)!!
        val uploadTask = firestoreImage.putStream(profileImage)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Photo Uploaded Successfully", Toast.LENGTH_SHORT).show()
                firestoreImage.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _photoUrl.value = task.result

                        // Add Image url to User data immediately photo is uploaded
//                        firestore.collection(USERS_COLLECTION).document(auth.uid!!).update("photoUrl", task.result!!)

                    } else {
                        Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Photo not Uploaded Successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

//
//
//    fun viewProfilePicture() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    fun deleteProfilePicture(view: View) {
//        firebaseStorage.reference.child("images/profile_pictures/${auth.uid}").delete().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Snackbar.make(view, "Picture was successfully deleted!", Snackbar.LENGTH_SHORT)
//            } else {
//                Snackbar.make(view, "Picture was not deleted!", Snackbar.LENGTH_SHORT)
//            }
//        }
//    }