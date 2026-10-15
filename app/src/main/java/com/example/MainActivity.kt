package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ExploreRepository
import com.example.ui.adaptive.AdaptiveMainScaffold
import com.example.ui.theme.ExploreNandedTheme
import com.example.viewmodel.ExploreViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ExploreViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = ExploreRepository(applicationContext)
                return ExploreViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val profileState by viewModel.profileUiState.collectAsState()
            val isDark = profileState.isDarkMode || isSystemInDarkTheme()

            ExploreNandedTheme(darkTheme = isDark) {
                AdaptiveMainScaffold(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
