package com.gmail.reebrando.myinbox

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class InboxApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //Enable Firebase persistence for offline access
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}