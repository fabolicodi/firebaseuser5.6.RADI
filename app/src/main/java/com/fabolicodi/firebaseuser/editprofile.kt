package com.fabolicodi.firebaseuser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabolicodi.firebaseuser.databinding.ActivityEditprofileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class editprofile : AppCompatActivity() {
    private val REQUEST_CODE = 100
    private var userId: String = ""
    var currUser: User=User()
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityEditprofileBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    var selectedImage=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        userId = auth.currentUser!!.uid
        databaseReference =
            FirebaseDatabase.getInstance("https://fir-zrakoplov-b34cb-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
        binding.upload.setOnClickListener { uploadImage() }

        databaseReference.child(userId).addValueEventListener(object : ValueEventListener {
            var displayList = ArrayList<User>()
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a: List<User> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(
                            User::class.java
                        )!!
                    }

                    displayList.addAll(a)

                } catch (e: Exception) {
                }
                for (v in displayList) {
                    if (v.uid.equals(userId)) {
                        currUser = v
                        binding.userime.setText(v.firstName)
                        binding.userprezime.setText(v.lastName)
                        binding.userbio.setText(v.bio)
                        if (v.pic == 0) {
                            val sRef: StorageReference =
                                FirebaseStorage.getInstance().reference.child("profile.jpg")
                            GlideApp.with(this@editprofile).load(sRef)
                                .into(binding.userProfilePhoto)
                        } else {
                            val sRef: StorageReference =
                                FirebaseStorage.getInstance().reference.child("users/ $userId/profile${v.pic}.jpg")
                            GlideApp.with(this@editprofile).load(sRef)
                                .into(binding.userProfilePhoto)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.buttonspremi.setOnClickListener {
            val firstName= binding.userime.text.toString()
            val lastName = binding.userprezime.text.toString()
            val bio = binding.userbio.text.toString()
            val pic = selectedImage
            if (userId != null) {
                databaseReference.child(userId).child("0").setValue(User(firstName,userId,lastName, bio, pic))
            }
        }
        /* binding.buttonspremi.setOnClickListener {
            val firstName = binding.userime.text.toString()
            val lastName = binding.userprezime.text.toString()
            val bio = binding.userbio.text.toString()

            val user = User(firstName, lastName, bio, pic = 0)
            if (uid != null) {
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {

                        ()


                    } else {
                        Toast.makeText(
                            this@editprofile,
                            "Failed to update profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }*/
    }

    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val delete = currUser.pic
        val value = currUser.pic + 1
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "users/ $userId/profile$value.jpg"
            )
            val uri: Uri = data!!.data!!
            val uploadTask = sRef.putFile(uri)

            uploadTask.addOnSuccessListener {
                databaseReference.child(userId).child("1").child("pic").setValue(value)
                GlideApp.with(this@editprofile).load(sRef).into(binding.userProfilePhoto)
                val deleteRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                    "users/ $userId/profile$delete.jpg"
                )
                deleteRef.delete()

            }

        }
    }
}