package com.example.fakestore

import android.window.SplashScreen
import org.junit.Rule
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeRule = createComposeRule()


    @Test
    fun SplashShowButtonvisVibility(){
        composeRule.mainClock.autoAdvance = false

        composeRule.setContent {
            SplashScreen(
                isLogedin=false,
                onNavigate={}
            )
        }

    }

}