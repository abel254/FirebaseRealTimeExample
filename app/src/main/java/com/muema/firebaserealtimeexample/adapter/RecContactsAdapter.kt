package com.muema.firebaserealtimeexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.muema.firebaserealtimeexample.HomeFragmentDirections
import com.muema.firebaserealtimeexample.databinding.RecContactsItemBinding
import com.muema.firebaserealtimeexample.models.Contacts

class RecContactsAdapter(private val contactList: ArrayList<Contacts>) : RecyclerView.Adapter<RecContactsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        return ViewHolder(RecContactsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val currentItem = contactList[position]
        holder.apply {
            binding.apply {
                txtNameItem.text = currentItem.name
                txtPhoneItem.text = currentItem.phoneNumber
                txtIdItem.text = currentItem.id

                recContainer.setOnClickListener {

                    val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
                        currentItem.id.toString(),
                        currentItem.name.toString(),
                        currentItem.phoneNumber.toString()
                    )
                    findNavController(holder.itemView).navigate(action)
                }

                recContainer.setOnLongClickListener {
                    MaterialAlertDialogBuilder(holder.itemView.context)
                        .setTitle("Delete item permanently")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes"){_,_ ->
                            val firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")
                            firebaseRef.child(currentItem.id.toString()).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(holder.itemView.context, "Item removed successfully", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(holder.itemView.context, "error: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("No"){_,_ ->
                            Toast.makeText(holder.itemView.context, "cancelled", Toast.LENGTH_SHORT).show()
                        }
                        .show()

                    return@setOnLongClickListener true
                }
            }
        }
    }

    override fun getItemCount(): Int {

        return contactList.size
    }

    class ViewHolder(val binding: RecContactsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}