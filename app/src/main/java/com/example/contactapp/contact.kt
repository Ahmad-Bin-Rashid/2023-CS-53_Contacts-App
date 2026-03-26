package com.example.contactapp

import android.net.Uri

data class Contact(
    var name: String,
    var phone: String,
    var imageUri: Uri? = null
)