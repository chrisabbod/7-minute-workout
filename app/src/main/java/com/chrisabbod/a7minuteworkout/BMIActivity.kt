package com.chrisabbod.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chrisabbod.a7minuteworkout.databinding.ActivityBmiactivityBinding

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiactivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}