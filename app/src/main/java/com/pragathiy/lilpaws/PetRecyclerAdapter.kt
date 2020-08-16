package com.pragathiy.lilpaws

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class PetRecyclerAdapter(
    private val context: Context,
    options: FirebaseRecyclerOptions<Pet>,
    private val onItemClickListener: OnItemClickListener
) :
    FirebaseRecyclerAdapter<Pet, PetRecyclerAdapter.ViewHolder>(options) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.pet_card)
        val type: MaterialTextView = view.findViewById(R.id.pet_card_category)
        val name: MaterialTextView = view.findViewById(R.id.pet_card_name)
        val age: MaterialTextView = view.findViewById(R.id.pet_card_age)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Pet, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pet_card, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Pet) {
        holder.type.text = context.getString(R.string.text_name)
            .replace("\$name", model.type!!, false)
        holder.name.text = context.getString(R.string.text_name)
            .replace("\$name", model.name!!, false)
        holder.age.text = context.getString(R.string.text_age)
            .replace("\$age", model.age!!.toString(), false)
        holder.card.setOnClickListener {
            onItemClickListener.onItemClick(model, position)
        }
    }
}