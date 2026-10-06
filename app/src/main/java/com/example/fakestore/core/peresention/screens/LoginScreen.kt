

package com.example.fakestore.core.peresention.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fakestore.core.peresention.components.authButton
import com.example.fakestore.core.peresention.components.authHeader
import com.example.fakestore.core.peresention.components.authTextField
import com.example.fakestore.core.peresention.components.errorCard
import com.example.fakestore.core.peresention.components.passwordTextField
import com.example.fakestore.core.peresention.components.toMessage
import com.example.fakestore.core.peresention.uistate.LoginUiState
import com.example.fakestore.core.peresention.vm.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.Success -> {
                Toast
                    .makeText(
                        context,
                        "Login successful! Welcome back!",
                        Toast.LENGTH_LONG,
                    ).show()
                viewModel.resetState()
                onLoginSuccess()
            }

            is LoginUiState.Error -> {
                Toast
                    .makeText(
                        context,
                        state.error.toMessage(),
                        Toast.LENGTH_LONG,
                    ).show()
                viewModel.resetState()
            }

            else -> {}
        }
    }
    loginScreenContent(
        email = email,
        password = password,
        emailError = emailError,
        passwordError = passwordError,
        passwordVisible = passwordVisible,
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVisibilityToggle = viewModel::onPasswordVisibilityToggle,
        onLoginClick = viewModel::onLoginClick,
        onNavigateToSignUp = onNavigateToSignUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreenContent(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    passwordVisible: Boolean,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    MaterialTheme.colorScheme.background,
                                ),
                        ),
                ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            authHeader(
                title = "Welcome Back",
                subtitle = "Login to your account",
            )

            Spacer(modifier = Modifier.height(64.dp))

            authTextField(
                modifier = Modifier.testTag("email_input"),
                value = email,
                onValueChange = onEmailChange,
                label = "Email",
                placeholder = "Enter your email",
                leadingIcon = Icons.Default.Email,
                errorMessage = emailError,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                keyboardActions =
                    KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            passwordTextField(
                modifier = Modifier.testTag("password_input"),
                value = password,
                onValueChange = onPasswordChange,
                label = "Password",
                placeholder = "Enter your password",
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = onPasswordVisibilityToggle,
                errorMessage = passwordError,
                imeAction = ImeAction.Done,
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onLoginClick()
                        },
                    ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier =
                    Modifier
                        .align(Alignment.End)
                        .clickable { },
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = uiState is LoginUiState.Error,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                if (uiState is LoginUiState.Error) {
                    errorCard(
                        errorMessage = (uiState as LoginUiState.Error).error.toMessage(),
                    )
                }
            }

            authButton(
                modifier = Modifier.testTag("login_button"),
                text = "Login",
                onClick = {
                    focusManager.clearFocus()
                    onLoginClick()
                },
                isLoading = uiState is LoginUiState.Loading,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Sign Up",
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onNavigateToSignUp() },
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
