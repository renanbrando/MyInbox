package com.gmail.reebrando.myinbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.gmail.reebrando.myinbox.Helpers.MqttHelper
import com.gmail.reebrando.myinbox.Utils.NotificationUtils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken

import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mAuth: FirebaseAuth? = null
    lateinit var mqttHelper: MqttHelper
    lateinit var context: Context
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 1000 //Set after 1 seconds from the current time.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        getSupportActionBar()?.hide()
//        setSupportActionBar(toolbar)

        mAuth = FirebaseAuth.getInstance()
        context = applicationContext

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        startMqtt()
    }

    private fun startMqtt() {
        mqttHelper = MqttHelper(applicationContext)
        mqttHelper.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {

            }

            override fun connectionLost(throwable: Throwable) {

            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Debug", mqttMessage.toString())
                MqttHelper(context).writeMessageInDB(topic, mqttMessage)
                NotificationUtils().setNotification(mNotificationTime, this@MainActivity, mqttMessage.toString(), "You got a new inbox")
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {

            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        try {
            // Handle navigation view item clicks here.
            when (item.itemId) {
                R.id.navMarcarCorrespondencia -> {
                    startActivity(Intent(this, MesageVerificationActivity::class.java))
                }
                R.id.navListarHistorico -> {
                    startActivity(Intent(this, ListInboxActivity::class.java))
                }
                R.id.nav_share -> {

                }
                R.id.nav_send -> {

                }
                R.id.signOut -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    mAuth!!.signOut()
                    this.finish()
                }
            }
        }
        catch (e: Exception){
             Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
