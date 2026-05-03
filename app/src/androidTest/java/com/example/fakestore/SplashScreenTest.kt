package com.example.fakestore

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import org.junit.Rule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.fakestore.core.peresention.screens.SplashScreen
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeRule = createComposeRule()


    @Test
    fun SplashShowButtonvisVibility(){


        composeRule.setContent {
            SplashScreen(
                isLoggedIn = false,
                onNavigate = {}
            )
        }

        composeRule.onNodeWithText("FAKESTORE").assertIsDisplayed()
        composeRule.onNodeWithText("YOUR PREMIUM SHOPPING DESTINATION").assertIsDisplayed()
        composeRule.onNodeWithText("Start Exploring  →").assertIsNotEnabled()

        composeRule.waitUntil(timeoutMillis = 4500) {
            composeRule
                .onAllNodes(hasText("Start Exploring  →").and(isEnabled()))
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeRule.onNodeWithText("Start Exploring  →").assertIsEnabled()
    }

    @Test
    fun splash_enables_button_after_3_seconds() {
        composeRule.mainClock.autoAdvance = false

        composeRule.setContent {
            SplashScreen(
                isLoggedIn = false,
                onNavigate = {}
            )
        }

    }

}