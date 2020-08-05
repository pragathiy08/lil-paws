package com.pragathiy.lilpaws

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        input_category.check(R.id.category_dog)
        database = Firebase.database.getReference("pets")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_pet_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                val type: Pet.PetType = when (input_category.checkedChipId) {
                    R.id.category_dog -> Pet.PetType.DOG
                    R.id.catergory_cat -> Pet.PetType.CAT
                    R.id.category_bird -> Pet.PetType.BIRD
                    else -> Pet.PetType.DOG
                }
                val pet = Pet(
                    type = type,
                    name = input_pet_name.text.toString(),
                    age = input_pet_age.text.toString().toInt()
                )
                val newPet = database.push()
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