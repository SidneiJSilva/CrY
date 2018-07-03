package com.example.tjsid.cry

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        logo_bit.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))

        Handler().postDelayed({

            logo_bit.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_out))
            Handler().postDelayed({

                logo_bit.visibility = View.GONE
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }, 500)

        }, 2500)

    }
}
