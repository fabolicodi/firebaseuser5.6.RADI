package com.fabolicodi.firebaseuser

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fabolicodi.firebaseuser.databinding.ActivityMainBinding
import com.fabolicodi.firebaseuser.databinding.ActivityMenurealBinding

class menureal : AppCompatActivity() {
    lateinit var binding: ActivityMenurealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenurealBinding .inflate(layoutInflater);
        setContentView(binding.root)

            binding.bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.liste -> Intent(this, pregledrv::class.java).also { startActivity(it) }
                    R.id.profil -> Intent(this, editprofile::class.java).also { startActivity(it) }
                    R.id.favoriti -> Intent(this, favoriti::class.java).also { startActivity(it) }
                    else -> {


                    }
                }

                true
            }



        }
    }
