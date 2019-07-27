package com.example.rappitest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rappitest.views.TmdbFeedActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_test.setOnClickListener {
            val intent = Intent(this, TmdbFeedActivity::class.java)
            startActivity(intent)
        }
    }
}
