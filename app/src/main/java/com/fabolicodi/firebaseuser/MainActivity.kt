package com.fabolicodi.firebaseuser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fabolicodi.firebaseuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnbor.setOnClickListener{
            Intent(this,  pregledrv::class.java).also{ startActivity(it) }
        }
        binding.btneditprofile.setOnClickListener{
            Intent(this,  editprofile::class.java).also{ startActivity(it) }
        }


        val btnOpenUrl: Button=findViewById(R.id.btnOpenURL)

        btnOpenUrl.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")

            startActivity(openURL)
        }

    }


}