package com.gmail.reebrando.myinbox

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gmail.reebrando.myinbox.helpers.MqttHelper
import com.gmail.reebrando.myinbox.utils.NotificationUtils
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.*


class MainFragment : Fragment() {

    lateinit var dataReceived: TextView
    lateinit var mqttHelper: MqttHelper
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 1000 //Set after 1 seconds from the current time.

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        dataReceived = view.findViewById(R.id.label) as TextView

        startMqtt()

        return view
    }

    private fun startMqtt() {
        mqttHelper = MqttHelper(context)
        mqttHelper.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {

            }

            override fun connectionLost(throwable: Throwable) {

            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Debug", mqttMessage.toString())
                dataReceived.setText(mqttMessage.toString())

               // NotificationUtils().setNotification(mNotificationTime, this@MainFragment, mqttMessage.toString(), "You got a new inbox")


            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {

            }
        })
    }
}
