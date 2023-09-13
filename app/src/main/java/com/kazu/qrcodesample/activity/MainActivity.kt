package com.kazu.qrcodesample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kazu.qrcodesample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    /** binding */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        binding.zxingButton.setOnClickListener {
            ZxingActivity.open(this)
        }

        binding.mlkitButton.setOnClickListener {
            MlKitActivity.open(this)
        }
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}