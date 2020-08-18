package com.pragathiy.lilpaws

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_no_user.*

class MainActivity : AppCompatActivity(),
    SigninDialog.SigninDialogListener,
    SignupDialog.SignupDialogListener,
    PetRecyclerAdapter.OnItemClickListener,
    PetViewDialog.PetViewListener {

    private val _tag = "appx"
    private lateinit var auth: FirebaseAuth
    private lateinit var pets: DatabaseReference
    private lateinit var adapter: PetRecyclerAdapter
    private var selection: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        auth.currentUser?.run {
            setUserView()
        } ?: run {
            setNoUserView()
        }
    }

    override fun onSignin(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                setUserView()
            } else {
                Toast.makeText(applicationContext, "Signin failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSignup(username: String, password: String) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    setUserView()
                } else {
                    Toast.makeText(applicationContext, "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setUserView() {
        setContentView(R.layout.activity_main)

        // Set up Vet Locator
        chip_vet_locator.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("geo:0,0?q=vet")
                `package` = "com.google.android.apps.maps"
            }
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            } ?: run {
                Toast.makeText(
                    applicationContext,
                    "Your phone doesn't have Google Maps",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // Set up FAB
        fab_new_pet.setOnClickListener {
            startActivity(Intent(applicationContext, NewPetActivity::class.java))
        }

        // Set up Database references
        pets = MyFirebaseStore.getInstance(auth.currentUser!!).pets

        // Updates list
        pet_list.layoutManager = GridLayoutManager(applicationContext, 2)
        val recyclerOptions: FirebaseRecyclerOptions<Pet> = FirebaseRecyclerOptions.Builder<Pet>()
            .setQuery(pets, Pet::class.java)
            .build()
        adapter = PetRecyclerAdapter(applicationContext, recyclerOptions, this)
        adapter.startListening()
        pet_list.adapter = adapter
        pet_list.addItemDecoration(PetItemDecorator())
    }

    private fun setNoUserView() {
        setContentView(R.layout.activity_main_no_user)
        button_signin.setOnClickListener {
            SigninDialog().show(supportFragmentManager, _tag)
        }
        button_signup.setOnClickListener {
            SignupDialog().show(supportFragmentManager, _tag)
        }
    }

    override fun onItemClick(item: Pet, position: Int) {
        selection = position
        val isMyPet = auth.currentUser!!.email!! == item.owner
        PetViewDialog(
            pet = item, isMyPet = isMyPet
        ).show(supportFragmentManager, _tag)
    }

    override fun onDeletePet() {
        val task = adapter.getRef(selection).removeValue()
        task.addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(applicationContext, "Pet removed", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Pet not removed", Toast.LENGTH_LONG).show()
            }
        }
    }
}