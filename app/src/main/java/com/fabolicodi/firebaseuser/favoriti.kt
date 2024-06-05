package com.fabolicodi.firebaseuser

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fabolicodi.firebaseuser.databinding.ActivityEditprofileBinding
import com.fabolicodi.firebaseuser.databinding.ActivityFavoritiBinding
import com.fabolicodi.firebaseuser.databinding.ActivityPregledrvBinding

class favoriti : AppCompatActivity() {
    lateinit var binding: ActivityFavoritiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFavoritiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setSelectedItemId(R.id.favoriti)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.favoriti -> Intent(this, favoriti::class.java).also { startActivity(it) }
                R.id.liste -> Intent(this, pregledrv::class.java).also { startActivity(it) }
                R.id.profil -> Intent(this, editprofile::class.java).also { startActivity(it) }
                else -> {


                }
            }

            true
        }
    }
}