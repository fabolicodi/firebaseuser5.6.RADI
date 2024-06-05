package com.fabolicodi.firebaseuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabolicodi.firebaseuser.databinding.ActivityMainBinding
import com.fabolicodi.firebaseuser.databinding.ActivityPregledrvBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class pregledrv : AppCompatActivity() {
    lateinit var binding: ActivityPregledrvBinding
    private val database: DatabaseReference = FirebaseDatabase.getInstance("https://fir-zrakoplov-b34cb-default-rtdb.europe-west1.firebasedatabase.app/").getReference("korisnici")
    private var tekstlist = ArrayList<firebase>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPregledrvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonunesi.setOnClickListener{
            val tempime=binding.unosime.text.toString()
            val tempprezime =binding.unosprezime.text.toString()
            val tempnkzl =binding.unosnkzl.text.toString()
            val templokacija =binding.unoslokacije.text.toString()
            var tempid=0
            if (!tekstlist.isEmpty()) tempid=tekstlist[tekstlist.size-1].id+1

            tekstlist.add(firebase(tempid,tempime,tempprezime,tempnkzl,templokacija))

            database.setValue(tekstlist)

        }

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                tekstlist.clear()
                try {
                    val a:List<firebase> = snapshot.children.map{dataSnapshot -> dataSnapshot.getValue(firebase::class.java)!!  }

                    tekstlist.addAll(a)

                }catch (e:Exception){}

                binding.lista.apply{
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = textAdapter (tekstlist, this@pregledrv)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        binding.bottomNavigationView.setSelectedItemId(R.id.liste)

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