package com.fabolicodi.firebaseuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fabolicodi.firebaseuser.databinding.ActivityAutentifikacijaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class autentifikacija : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityAutentifikacijaBinding
    private var userId:String=""
    var databaseReference =
    FirebaseDatabase.getInstance("https://fir-zrakoplov-b34cb-default-rtdb.europe-west1.firebasedatabase.app/")
    .getReference("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAutentifikacijaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.loginbtn.setOnClickListener {
            if (binding.unosemail.text.toString().isEmpty()) Toast.makeText(applicationContext, getString(R.string.jes_glup_nisi_ime_napiso), Toast.LENGTH_SHORT).show()
            else if (binding.unoslozinke.text.toString().isEmpty()) Toast.makeText(applicationContext, getString(R.string.nisi_lozinku_napisao), Toast.LENGTH_SHORT).show()
            else{
                val str = binding.unosemail.text.toString().filter { !it.isWhitespace() }
                val email ="$str@gmail.com"
                val password=binding.unoslozinke.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(baseContext, getString(R.string.uspjesna_prijava),
                                Toast.LENGTH_SHORT).show()
                            getString(R.string.uspjesna_prijava)
                            val user = auth.currentUser
                            val intent= Intent(this, pregledrv::class.java)
                            intent.putExtra("user", user)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, getString(R.string.neuspjesna_prijava),
                                Toast.LENGTH_SHORT).show()
                            getString(R.string.neuspjesna_prijava)
                        }
                    } }
        }
        binding.signbtn.setOnClickListener {

            if (binding.unosemail.text.toString().isEmpty()) Toast.makeText(applicationContext, getString(R.string.unesi_email), Toast.LENGTH_SHORT).show()
            else if (binding.unoslozinke.text.toString().isEmpty()) Toast.makeText(applicationContext, getString(R.string.unesi_pass), Toast.LENGTH_SHORT).show()
            else if (binding.unoslozinke.text.toString().length < 6) Toast.makeText(applicationContext, getString(R.string.pass_kratak), Toast.LENGTH_SHORT).show()
            else{

                val str = binding.unosemail.text.toString().filter { !it.isWhitespace() }
                val email ="$str@gmail.com"
                val password=binding.unoslozinke.text.toString()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, getString(R.string.uspjesna_registracija),
                                Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser
                            userId = user!!.uid
                            databaseReference.child(userId).child("0").setValue(User("",userId,"","",0))

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, getString(R.string.neuspjesna_registracija),
                                Toast.LENGTH_SHORT).show()
                        }
                    } }
        }


    }

}
