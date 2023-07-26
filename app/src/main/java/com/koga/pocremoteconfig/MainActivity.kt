package com.koga.pocremoteconfig

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.koga.pocremoteconfig.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeConfig.collect(::setupView)
            }
        }

        viewModel.retrieveConfigData(this)
    }

    private fun setupView(homeConfig: HomeConfig) = with(binding) {
        toolbar.title = homeConfig.toolbar.text
        toolbar.setTitleTextColor(homeConfig.toolbar.textColor.toHexaColor())
        toolbar.setBackgroundColor(homeConfig.toolbar.backgroundColor.toHexaColor())

        tvFirst.text = homeConfig.firstText.text
        tvFirst.setTextColor(homeConfig.firstText.color.toHexaColor())

        tvSecond.text = homeConfig.secondText.text
        tvSecond.setTextColor(homeConfig.secondText.color.toHexaColor())

        btnPrimary.text = homeConfig.primaryButton.text
        btnPrimary.setBackgroundColor(homeConfig.primaryButton.backgroundColor.toHexaColor())
        btnPrimary.setTextColor(homeConfig.primaryButton.textColor.toHexaColor())
    }

    private fun String.toHexaColor() = Color.parseColor(this)
}