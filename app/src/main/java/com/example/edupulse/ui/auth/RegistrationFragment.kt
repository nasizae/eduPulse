package com.example.edupulse.ui.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.edupulse.R
import com.example.edupulse.databinding.FragmentRegistrationBinding
import com.example.edupulse.model.Users
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import okio.IOException

class RegistrationFragment : Fragment() {
    private lateinit var myDatabase: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var filePath: Uri
    private val firebaseStorage = FirebaseStorage.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRegisterUsers()
        auth = FirebaseAuth.getInstance()
    }

    private fun initRegisterUsers() {
        myDatabase = Firebase.database.getReference(USER)
        binding.btnTransitionAuth.setOnClickListener {
            findNavController().navigate(R.id.authenticationFragment)
        }
        binding.btnRegister.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etEmail.text.toString()) &&
                !TextUtils.isEmpty(binding.etPassword.text.toString())
            ) {
                auth.createUserWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val uid = user?.uid
                        if (uid != null) {
                            setDataUser(uid)
                            findNavController().navigate(R.id.navigation_home)
                        } else {
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Пустое поле", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageUser.setOnClickListener {
            selectLoadImage()
        }
    }

    private fun setDataUser(uid: String) {
        if (uid != null) {
            firebaseStorage.getReference().child("image/" + uid).putFile(filePath)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "good", Toast.LENGTH_SHORT).show()
                    firebaseStorage.getReference()
                        .child("image/" + uid).downloadUrl.addOnSuccessListener {
                            val fullName = binding.etFullName.text.toString()
                            val email = binding.etEmail.text.toString()
                            val password = binding.etPassword.text.toString()
                            val users = Users(uid, fullName, email, password, it.toString())
                            myDatabase.child(uid).setValue(users)
                        }
                }
        }
    }

    private fun selectLoadImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK &&
                result.data != null && result.data?.data != null
            ) {
                filePath = result.data!!.data!!
                try {
                    val bitmap: Bitmap
                    bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        filePath
                    )
                    binding.imageUser.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    companion object {
        const val USER = "Users"
    }
}