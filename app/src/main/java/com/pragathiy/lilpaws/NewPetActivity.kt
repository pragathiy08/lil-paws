package com.pragathiy.lilpaws

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_new_pet.*

class NewPetActivity : AppCompatActivity() {

    private val _tag = "appx"
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_pet)
    }

    override fun onStart() {
        super.onStart()
        database = Firebase.database.getReference("pets")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_pet_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                val type = findViewById<Chip>(input_category.checkedChipId).text.toString()
                val newPet = database.push()
                val pet = Pet().apply {
                    this.key = newPet.key
                    this.type = type
                    this.name = input_pet_name.text.toString()
                    this.age = input_pet_age.text.toString().toInt()
                    this.owner = FirebaseAuth.getInstance().currentUser?.email
                }
                newPet.setValue(pet).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Couldn't save", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        return true
    }
}