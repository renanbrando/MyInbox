package com.gmail.reebrando.myinbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.gmail.reebrando.myinbox.config.Constants
import com.gmail.reebrando.myinbox.models.Inbox
import com.gmail.reebrando.myinbox.utils.NotificationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.toast
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var context: Context
    lateinit var mDatabase: DatabaseReference
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 1000 //Set after 1 seconds from the current time.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        context = applicationContext
        mDatabase = FirebaseDatabase.getInstance().reference

        NotificationUtils().setNotification(mNotificationTime, this, "Incoming!!!!!", "You got a new inbox")


        fab.setOnClickListener { view ->
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)

            Snackbar.make(view, prefs.getString("mac", "Not set!"), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()


            //prefs.getBoolean("checkbox", false).toString()
            //prefs.getString("ringtone", "<unset>")
            //prefs.getString("text", "<unset>")
            //prefs.getString("list", "<unset>")

            //addNewItemDialog()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val fragment = MainFragment.newInstance()
        replaceFragment(fragment)

    }

    private fun addNewItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setMessage("Add New Item")
        alert.setTitle("Enter To Do Item Text")
        alert.setView(itemEditText)
        alert.setPositiveButton("Submit") { dialog, positiveButton ->
            val todoItem = Inbox.create()
            todoItem.itemText = itemEditText.text.toString()
            todoItem.done = false
            //We first make a push so that a new item is made with a unique ID
            val newItem = mDatabase.child(Constants.FIREBASE_ITEM).push()
            todoItem.objectId = newItem.key
            //then, we used the reference to set the value on that ID
            newItem.setValue(todoItem)
            dialog.dismiss()
            toast("Item saved with ID " + todoItem.objectId)
        }
        alert.show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_inbox -> {
                val fragment = MainFragment.newInstance()
                replaceFragment(fragment)
            }
            R.id.nav_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logs -> {
                val intentToMain = Intent(this@MainActivity, InboxActivity::class.java)
                startActivity(intentToMain)
            }
            R.id.nav_about -> {
                val fragment = AboutFragment.newInstance()
                replaceFragment(fragment)
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent_ = Intent(this@MainActivity, LoginActivity::class.java)
                intent_.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent_.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent_)
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
