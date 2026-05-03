package com.tcastro.swordcatchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tcastro.core.ui.theme.SwordCatChallengeTheme
import com.tcastro.swordcatchallenge.navigation.NavigationScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwordCatChallengeTheme {
                NavigationScaffold()
            }
        }
    }
}


