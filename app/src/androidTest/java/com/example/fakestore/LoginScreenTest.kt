package com.example.fakestore


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.fakestore.core.peresention.components.toMessage
import com.example.fakestore.core.peresention.screens.LoginScreenContent
import org.junit.Rule
import org.junit.Test
import com.example.fakestore.core.peresention.uistate.LoginUiState
import com.example.fakestore.core.peresention.uistate.UiError


class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testLoginUserInputsAndClicksForLogin() {
        var useremailtest = "testuser"
        var passwordtest = "testpassword"
        var loginbuttontest = false

        composeTestRule.setContent {
            LoginScreenContent(
                email = useremailtest,
                password = passwordtest,
                emailError = null,
                passwordError = null,
                passwordVisible = false,
                uiState = LoginUiState.Idle,
                onEmailChange = { useremailtest = it },
                onPasswordChange = { passwordtest = it },
                onPasswordVisibilityToggle = {},
                onLoginClick = { loginbuttontest = true },
                onNavigateToSignUp = {}

            )



        }
        composeTestRule.onNodeWithTag("email_input").performTextInput("testuser")
        composeTestRule.onNodeWithTag("password_input").performTextInput("testpassword")
        composeTestRule.onNodeWithTag("login_button").performClick()

        assert(useremailtest == "testuser")
        assert(passwordtest == "testpassword")
        assert(loginbuttontest)

    }

    @Test
    fun whenStateIsError_showsErrorMessage() {
        val testError = UiError.NoInternet
        composeTestRule.setContent {
            LoginScreenContent(
                email = "",
                password = "",
                emailError = null,
                passwordError = null,
                passwordVisible = false,

                uiState = LoginUiState.Error(testError),
                onEmailChange = {},
                onPasswordChange = {},
                onPasswordVisibilityToggle = {},
                onLoginClick = {},
                onNavigateToSignUp = {}
            )
        }
        val expectedErrorMessage = testError.toMessage()

        composeTestRule.onNodeWithText(expectedErrorMessage).assertIsDisplayed()
    }

    @Test
    fun LoadingState_displaysLoadingIndicator(){
        composeTestRule.setContent {
            LoginScreenContent(
                email = "",
                password = "",
                emailError = null,
                passwordError = null,
                passwordVisible = false,

                uiState = LoginUiState.Loading,
                onEmailChange = {},
                onPasswordChange = {},
                onPasswordVisibilityToggle = {},
                onLoginClick = {},
                onNavigateToSignUp = {}
            )
        }
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun PasswrodVisibilityToggle_togglesPasswordVisibility() {
        var togglevisiblitybutton = false

        composeTestRule.setContent {
            LoginScreenContent(
                email = "",
                password = "",
                emailError = null,
                passwordError = null,
                passwordVisible = false,

                uiState = LoginUiState.Loading,
                onEmailChange = {},
                onPasswordChange = {},
                onPasswordVisibilityToggle = { togglevisiblitybutton = true },
                onLoginClick = {},
                onNavigateToSignUp = {}
            )
        }
        composeTestRule.onNodeWithTag("password_visibility_toggle").performClick()

        assert(togglevisiblitybutton) {
            "Password visibility toggle callback should be invoked"
        }


    }
}