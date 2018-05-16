package com.gmail.reebrando.myinbox.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.gmail.reebrando.myinbox.services.NotificationService

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
        service.putExtra("title", intent.getStringExtra("title"))
        service.putExtra("message", intent.getStringExtra("message"))

        service.data = Uri.parse("custom://" + System.currentTimeMillis())
        context.startService(service)
    }

}