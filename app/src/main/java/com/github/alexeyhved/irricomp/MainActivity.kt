package com.github.alexeyhved.irricomp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.alexeyhved.irricomp.presentation.main.MainScreen
import com.github.alexeyhved.irricomp.presentation.main.MainScreenViewModel
import com.github.alexeyhved.irricomp.ui.theme.IrriCompTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IrriCompTheme {
                val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
                val mainScreenState = mainScreenViewModel.mainScreenState.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        states = mainScreenState.value,
                        paddingValues = innerPadding,
                        onEvent = mainScreenViewModel::onEvent
                    )
                }
            }
        }
    }
}
