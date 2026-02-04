package com.muema.firebaserealtimeexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muema.firebaserealtimeexample.adapter.RecContactsAdapter
import com.muema.firebaserealtimeexample.databinding.FragmentHomeBinding
import com.muema.firebaserealtimeexample.models.Contacts


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var contactsList: ArrayList<Contacts>
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")
        contactsList = arrayListOf()

        fetchData()

        binding.recContacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }


        return binding.root
    }

    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               contactsList.clear()
                if (snapshot.exists()){
                    for (contactSnap in snapshot.children){
                        val contacts = contactSnap.getValue(Contacts::class.java)
                        contactsList.add(contacts!!)
                    }
                }
                val recAdapter = RecContactsAdapter(contactsList)
                binding.recContacts.adapter = recAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error: $error", Toast.LENGTH_SHORT).show()
            }

        })
    }


}