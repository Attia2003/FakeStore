package com.example.fakestore.core.peresention.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fakestore.core.peresention.components.*
import com.example.fakestore.core.peresention.uistate.LoginUiState
import com.example.fakestore.core.peresention.vm.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.Success -> {
                Toast.makeText(
                    context,
                    "Login successful! Welcome back!",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.resetState()
                onLoginSuccess()
            }
            is LoginUiState.Error -> {
                Toast.makeText(
                    context,
                    state.error.toMessage(),
                    Toast.LENGTH_LONG
                ).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))


            AuthHeader(
                title = "Welcome Back",
                subtitle = "Login to your account"
            )

            Spacer(modifier = Modifier.height(64.dp))


            AuthTextField(
                value = email,
                onValueChange = viewModel::onEmailChange,
                label = "Email",
                placeholder = "Enter your email",
                leadingIcon = Icons.Default.Email,
                errorMessage = emailError,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))


            PasswordTextField(
                value = password,
                onValueChange = viewModel::onPasswordChange,
                label = "Password",
                placeholder = "Enter your password",
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { viewModel.onPasswordVisibilityToggle() },
                errorMessage = passwordError,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { 
                        focusManager.clearFocus()
                        viewModel.onLoginClick()
                    }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {  }
            )

            Spacer(modifier = Modifier.height(24.dp))


            AnimatedVisibility(
                visible = uiState is LoginUiState.Error,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                if (uiState is LoginUiState.Error) {
                    ErrorCard(
                        errorMessage = (uiState as LoginUiState.Error).error.toMessage()
                    )
                }
            }


            AuthButton(
                text = "Login",
                onClick = { 
                    focusManager.clearFocus()
                    viewModel.onLoginClick() 
                },
                isLoading = uiState is LoginUiState.Loading
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onNavigateToSignUp() }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
