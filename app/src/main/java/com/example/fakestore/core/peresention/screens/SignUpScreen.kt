package com.example.fakestore.core.peresention.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
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
import com.example.fakestore.core.peresention.uistate.SignUpUiState
import com.example.fakestore.core.peresention.vm.SignUpViewModel

/**
 * SignUp Screen - Pure Presentation Layer
 * 
 * This composable follows Clean Architecture principles:
 * - No business logic or validation rules
 * - No local state management for inputs
 * - Only observes ViewModel state
 * - Delegates all events to ViewModel
 * - Handles only UI-specific side effects (toasts, navigation)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onSignUpSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val nameError by viewModel.nameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()
    val confirmPasswordVisible by viewModel.confirmPasswordVisible.collectAsState()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is SignUpUiState.Success -> {
                Toast.makeText(
                    context,
                    "Account created successfully! Welcome ${state.user.name}",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.resetState()
                onSignUpSuccess()
            }
            is SignUpUiState.Error -> {
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
            Spacer(modifier = Modifier.height(60.dp))


            AuthHeader(
                title = "Create Account",
                subtitle = "Sign up to get started"
            )

            Spacer(modifier = Modifier.height(48.dp))


            AuthTextField(
                value = name,
                onValueChange = viewModel::onNameChange,
                label = "Full Name",
                placeholder = "Enter your name",
                leadingIcon = Icons.Default.Person,
                errorMessage = nameError,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))


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
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))


            PasswordTextField(
                value = confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = "Confirm Password",
                placeholder = "Re-enter your password",
                passwordVisible = confirmPasswordVisible,
                onPasswordVisibilityChange = { viewModel.onConfirmPasswordVisibilityToggle() },
                errorMessage = confirmPasswordError,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = { 
                        focusManager.clearFocus()
                        viewModel.onSignUpClick()
                    }
                )
            )

            Spacer(modifier = Modifier.height(32.dp))


            AuthButton(
                text = "Sign Up",
                onClick = { 
                    focusManager.clearFocus()
                    viewModel.onSignUpClick()
                },
                isLoading = uiState is SignUpUiState.Loading
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
