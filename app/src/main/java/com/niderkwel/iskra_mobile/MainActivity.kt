package com.niderkwel.iskra_mobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.niderkwel.iskra_mobile.components.navigation.graphs.RootNavGraph
import com.niderkwel.iskra_mobile.theme.IskramobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            IskramobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavGraph(
                        navController = rememberNavController(),
                        applicationContext.getSharedPreferences("iskra_sp", Context.MODE_PRIVATE),
                        applicationContext.contentResolver
                    )
                }
            }
        }
    }
}