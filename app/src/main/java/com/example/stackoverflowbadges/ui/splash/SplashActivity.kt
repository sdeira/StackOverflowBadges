package com.example.stackoverflowbadges.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.stackoverflowbadges.MainActivity
import com.example.stackoverflowbadges.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    companion object {
        private const val DELAY = 3000L
    }
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        lifecycleScope.launch {
            delay(DELAY)
            viewModel.checkUserToken()
        }
        viewModel.uiState.observe(this, Observer { uiState ->
            when (uiState) {
                is SplashUIState.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    ContextCompat.startActivity(this, intent, null)
                    finish()
                }
                is SplashUIState.LoginIntent -> {
                    val uri = Uri.Builder()
                        .scheme("https")
                        .authority("stackexchange.com")
                        .path("oauth")
                        .appendQueryParameter("client_id", getString(R.string.client_id))
                        .appendQueryParameter("scope", getString(R.string.scope))
                        .appendQueryParameter("redirect_uri", getString(R.string.redirect_uri))
                        .build()
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = uri
                    startActivity(i)
                    finish()
                }
            }
        })
    }
}