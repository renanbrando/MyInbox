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
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.gmail.reebrando.myinbox.utils.NotificationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var context: Context
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 1000 //Set after 1 seconds from the current time.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        context = applicationContext

        NotificationUtils().setNotification(mNotificationTime, this, "Incoming!!!!!", "You got a new inbox")


        fab.setOnClickListener { view ->
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)

            Snackbar.make(view, prefs.getString("mac", "<unset>"), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()


            //prefs.getBoolean("checkbox", false).toString()
            //prefs.getString("ringtone", "<unset>")
            //prefs.getString("text", "<unset>")
            //prefs.getString("list", "<unset>")
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val fragment = MainFragment.newInstance()
        replaceFragment(fragment)

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
            R.id.nav_share -> {

            }
            R.id.nav_about -> {
                val fragment = AboutFragment.newInstance()
                replaceFragment(fragment)
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent_ = Intent(this@MainActivity, LoginActivity::class.java)
                intent_.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent_.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent_)
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
