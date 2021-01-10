package com.example.stackoverflowbadges.ui.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.stackoverflowbadges.MainActivity
import com.example.stackoverflowbadges.R
import com.example.stackoverflowbadges.databinding.DeeplinkActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeepLinkActivity : AppCompatActivity() {
    private val viewModel: DeepLinkViewModel by viewModels()
    lateinit var binding: DeeplinkActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.deeplink_activity)

        val data: Uri? = intent?.data

        login(data)
        binding.retryButton.setOnClickListener {
            val data: Uri? = intent?.data
            login(data)
        }
        viewModel.uiState.observe(this, Observer { uiState ->
            when (uiState) {
                is UIState.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    ContextCompat.startActivity(this, intent, null)
                    finish()
                }
                is UIState.SnackBar -> {
                    Toast.makeText(this, uiState.text, Toast.LENGTH_LONG).show()
                }
                is UIState.Loading -> {
                    binding.progressBar.visibility = if (uiState.show) View.VISIBLE else View.GONE
                }
                is UIState.FullScreenError -> {
                    binding.retryButton.visibility = if (uiState.show) View.VISIBLE else View.GONE
                    binding.textError.visibility = if (uiState.show) View.VISIBLE else View.GONE
                }
            }
        })
    }

    private fun login(data: Uri?) {
        lifecycleScope.launch {
            viewModel.login(data, getString(R.string.client_id), getString(R.string.client_secret),
                getString(R.string.redirect_uri), getString(R.string.scope), getString(R.string.login_successfully))
        }
    }
}