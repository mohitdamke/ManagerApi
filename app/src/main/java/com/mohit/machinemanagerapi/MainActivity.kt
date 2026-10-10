package com.mohit.machinemanagerapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mohit.machinemanagerapi.navigation.AppNavigation
import com.mohit.machinemanagerapi.ui.theme.MachineManagerApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MachineManagerApiTheme {
                AppNavigation()
            }
        }
    }
}
