package com.muema.firebaserealtimeexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
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
            }
        }
    }

    override fun getItemCount(): Int {

        return contactList.size
    }

    class ViewHolder(val binding: RecContactsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}