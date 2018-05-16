package com.gmail.reebrando.myinbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.widget.ImageView


class SplashActivity : AppCompatActivity() {

    // Time that the splashscreen will be visible
    private val SPLASH_DISPLAY_LENGTH = 3500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadScreen()
    }

    private fun loadScreen() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.animation_splash)
        val animText = AnimationUtils.loadAnimation(this, R.anim.animation_splash_text)
        anim.reset()
        animText.reset()
        val iv = findViewById<View>(R.id.splash) as ImageView
        val tv = findViewById<View>(R.id.tvName) as TextView
        if (iv != null && tv != null) {
            iv!!.clearAnimation()
            tv.clearAnimation()
            iv!!.startAnimation(anim)
            tv.startAnimation(animText)
        }
        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
            this@SplashActivity.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

}
