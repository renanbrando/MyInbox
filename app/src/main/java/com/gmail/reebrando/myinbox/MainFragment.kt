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
import android.widget.Button
import android.widget.TextView
import com.gmail.reebrando.myinbox.helpers.MqttHelper
import com.gmail.reebrando.myinbox.models.User
import com.gmail.reebrando.myinbox.utils.NotificationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.*


class MainFragment : Fragment() {

    private lateinit var displayName: TextView
    private lateinit var status: TextView
    private lateinit var logout: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

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

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        displayName = view.findViewById(R.id.nameTextView) as TextView
        status = view.findViewById(R.id.statusTextView) as TextView
        logout = view.findViewById(R.id.singoutButton) as Button

        logout.setOnClickListener(){
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            //finish()
        }

        isLogin()

        return view
    }

    private fun isLogin(){
        val intent = Intent(context, LoginActivity::class.java)

        auth.currentUser?.uid?.let { loadData(it)  } ?: startActivity(intent)

    }

    private fun loadData(userId: String){
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    var user: User = dataSnapshot.getValue(User::class.java)!!
                    displayName.text = user.displayName
                    status.text = user.status
                }

            }

            override fun onCancelled(p0: DatabaseError?) {
                //
            }
        }
        database.reference.child("user")
                .child(userId).addListenerForSingleValueEvent(dataListener)

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
