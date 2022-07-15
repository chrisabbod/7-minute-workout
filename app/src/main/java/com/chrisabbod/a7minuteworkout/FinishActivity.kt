package com.chrisabbod.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chrisabbod.a7minuteworkout.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding : ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        
    }
}