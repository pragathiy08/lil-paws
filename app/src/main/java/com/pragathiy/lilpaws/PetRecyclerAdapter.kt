package com.pragathiy.lilpaws

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.textview.MaterialTextView

class PetRecyclerAdapter(options: FirebaseRecyclerOptions<Pet>) :
    FirebaseRecyclerAdapter<Pet, PetRecyclerAdapter.ViewHolder>(options) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val type: MaterialTextView = view.findViewById(R.id.pet_card_category)
        val name: MaterialTextView = view.findViewById(R.id.pet_card_name)
        val age: MaterialTextView = view.findViewById(R.id.pet_card_age)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pet_card, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Pet) {
        holder.type.text = model.type.name
        holder.name.text = model.name
        holder.age.text = model.age.toString()
    }
}