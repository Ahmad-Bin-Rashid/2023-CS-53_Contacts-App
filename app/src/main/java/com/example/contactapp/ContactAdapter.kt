package com.example.contactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private var contactList: MutableList<Contact>,
    private val listener: OnContactActionListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var filteredList: MutableList<Contact> = contactList

    interface OnContactActionListener {
        fun onItemClick(contact: Contact)
        fun onEditClick(contact: Contact, position: Int)
        fun onDeleteClick(contact: Contact, position: Int)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContactName: TextView = itemView.findViewById(R.id.tvContactName)
        val tvContactPhone: TextView = itemView.findViewById(R.id.tvContactPhone)
        val ivContactItemImage: ImageView = itemView.findViewById(R.id.ivContactItemImage)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = filteredList[position]

        holder.tvContactName.text = currentContact.name
        holder.tvContactPhone.text = currentContact.phone
        
        if (currentContact.imageUri != null) {
            holder.ivContactItemImage.setImageURI(currentContact.imageUri)
        } else {
            holder.ivContactItemImage.setImageResource(android.R.drawable.ic_menu_report_image)
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentContact)
        }

        holder.btnEdit.setOnClickListener {
            listener.onEditClick(currentContact, position)
        }

        holder.btnDelete.setOnClickListener {
            listener.onDeleteClick(currentContact, position)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun updateList(newList: MutableList<Contact>) {
        contactList = newList
        filteredList = newList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            contactList
        } else {
            val resultList = mutableListOf<Contact>()
            for (contact in contactList) {
                if (contact.name.contains(query, ignoreCase = true) || 
                    contact.phone.contains(query, ignoreCase = true)) {
                    resultList.add(contact)
                }
            }
            resultList
        }
        notifyDataSetChanged()
    }
}
