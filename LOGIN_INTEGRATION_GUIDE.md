# Login Feature - Quick Integration Guide

## ✅ Setup Complete!

The login feature has been fully integrated into your FakeStore app. Here's everything you need to know:

---

## 🚀 How to Navigate to Login Screen

### Option 1: From Any Screen with NavController
```kotlin
navController.navigate(Routes.LOGIN)
```

### Option 2: Set as Start Destination (Recommended for Apps Requiring Auth)
In `AppNavGraph.kt`, change the start destination:
```kotlin
NavHost(
    navController = navController,
    startDestination = Routes.LOGIN,  // Changed from Routes.SIGNUP or Routes.HOME
    modifier = Modifier.padding(paddingValues)
)
```

### Option 3: From SignUp Screen
Users can tap "Login" at the bottom of the SignUp screen to navigate to Login.

---

## 🔄 Navigation Flow

```
LoginScreen
    ├── On Success → Navigate to HOME (with popUpTo to clear back stack)
    ├── On "Sign Up" click → Navigate to SIGNUP
    └── On "Forgot Password" → (TODO: Create forgot password screen)

SignUpScreen
    └── On "Login" click → Navigate to LOGIN
```

---

## 🧪 Testing the Login Feature

### 1. **Manual Testing:**
Navigate to the login screen and fill in the form:
- **Email:** john.doe@example.com
- **Password:** changeme

> **Note:** Use valid credentials from the Platzi Fake Store API or create a test account using the SignUp screen first.

### 2. **Test Cases to Verify:**

#### ✅ Validation Tests:
- [ ] Empty email shows error
- [ ] Invalid email format shows error
- [ ] Empty password shows error
- [ ] Password less than 6 characters shows error

#### ✅ UI/UX Tests:
- [ ] Password visibility toggle works
- [ ] Loading indicator appears during login
- [ ] Button disables during loading
- [ ] Toast appears on success
- [ ] Toast appears on error
- [ ] Keyboard "Next" navigates from email to password
- [ ] Keyboard "Done" submits form

#### ✅ Navigation Tests:
- [ ] Successful login navigates to home
- [ ] "Sign Up" link navigates to signup screen
- [ ] Back button from login works properly
- [ ] Login clears navigation back stack (can't go back to login after success)

#### ✅ Error Handling Tests:
- [ ] Shows appropriate error for wrong credentials
- [ ] Shows network error when offline
- [ ] Handles server errors gracefully

---

## 📱 UI Features

The login screen includes:
- ✨ **Gradient background** (primary color fadeout to background)
- 🎨 **Material Design 3** components
- 🔒 **Password visibility** toggle
- ✅ **Real-time form validation**
- 📱 **Responsive scrollable** layout
- 🌙 **Dark mode support** (automatic based on theme)
- 🎯 **Clean and minimal** design
- ⚡ **Loading states** with spinner
- 🎨 **Consistent styling** with SignUp screen

---

## 🔌 API Endpoint

The login feature uses the Platzi Fake Store API:
- **Endpoint:** `POST /auth/login`
- **Request Body:**
  ```json
  {
    "email": "john@mail.com",
    "password": "changeme"
  }
  ```
- **Response:**
  ```json
  {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```

---

## 🏗️ Architecture Overview

The login feature follows Clean Architecture principles:

### Data Layer
- **`loginRequest.kt`** - Request DTO (email, password)
- **`LoginResponse.kt`** - Response DTO (accessToken, refreshToken)
- **`ApiService.kt`** - API endpoint definition
- **`LoginRepoImpl.kt`** - Repository implementation

### Domain Layer
- **`loginRepository.kt`** - Repository interface
- **`LoginUseCase.kt`** - Business logic

### Presentation Layer
- **`LoginViewModel.kt`** - ViewModel with StateFlow
- **`LoginUiState.kt`** - UI state (Idle, Loading, Success, Error)
- **`LoginScreen.kt`** - Composable UI

### Dependency Injection
- **`LoginRepoModule.kt`** - Hilt module providing dependencies

---

## 🎯 Complete Authentication Flow

Now that both Login and SignUp are complete, here's the typical user flow:

```
1. App Launch
   ├── If not logged in → Show LOGIN screen
   └── If logged in → Show HOME screen

2. Login Screen
   ├── New user? → Tap "Sign Up" → Navigate to SIGNUP
   ├── Forgot password? → (TODO) Navigate to FORGOT_PASSWORD
   └── Valid credentials → Login → Navigate to HOME

3. SignUp Screen
   ├── Already have account? → Tap "Login" → Navigate to LOGIN
   └── Create account → Navigate to HOME
```

---

## 🎯 Next Steps (Recommended)

### 1. **Token Persistence (IMPORTANT!)**
Currently, tokens are not saved. Implement token storage:

```kotlin
// Create a TokenManager using DataStore or SharedPreferences
class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }
    
    suspend fun getAccessToken(): String? {
        return dataStore.data.firstOrNull()?.get(ACCESS_TOKEN_KEY)
    }
    
    suspend fun clearTokens() {
        dataStore.edit { it.clear() }
    }
}
```

### 2. **Auto-Login on App Launch**
Check if user has valid token and navigate accordingly:

```kotlin
// In your MainActivity or AppNavGraph
LaunchedEffect(Unit) {
    val token = tokenManager.getAccessToken()
    if (token != null) {
        // Navigate to HOME
    } else {
        // Navigate to LOGIN
    }
}
```

### 3. **Add Authorization Headers**
Include token in API requests:

```kotlin
// In your Retrofit interceptor
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenManager.getAccessToken() }
        val request = chain.request().newBuilder()
            .apply {
                token?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
            .build()
        return chain.proceed(request)
    }
}
```

### 4. **Logout Feature**
Add logout to AccountScreen:

```kotlin
Button(
    onClick = {
        viewModel.logout() // Clear tokens
        navController.navigate(Routes.LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }
) {
    Text("Logout")
}
```

### 5. **Forgot Password Screen**
Create a password reset flow:
- `ForgotPasswordScreen.kt`
- API endpoint for password reset
- Email verification flow

### 6. **Profile Screen Enhancement**
Show user information:
- Display user email
- Allow profile editing
- Show account creation date

### 7. **Token Refresh**
Implement automatic token refresh:
- Intercept 401 responses
- Use refresh token to get new access token
- Retry failed request with new token

---

## 🐛 Troubleshooting

### Issue: Login returns 401 Unauthorized
**Solution:** Make sure you're using valid credentials. Try creating a new account via SignUp first, then login with those credentials.

### Issue: Network error even with internet
**Solution:** Check your base URL in Retrofit configuration. For Platzi API, it should be `https://api.escuelajs.co/api/v1/`.

### Issue: Tokens not being saved
**Solution:** You need to implement token persistence (see Next Steps #1). Currently, tokens are received but not stored.

### Issue: Can navigate back to login after successful login
**Solution:** This is fixed! The navigation uses `popUpTo` with `inclusive = true` to remove login from the back stack.

### Issue: Screen not appearing in navigation
**Solution:** Rebuild the project (`Ctrl+F9` or `Build > Make Project`) to ensure all new files are compiled.

---

## 📞 Quick Commands

### Navigate to Login:
```kotlin
navController.navigate(Routes.LOGIN)
```

### Navigate to SignUp from Login:
```kotlin
navController.navigate(Routes.SIGNUP) {
    popUpTo(Routes.LOGIN) { inclusive = true }
}
```

### Navigate to Home after login:
```kotlin
navController.navigate(Routes.HOME) {
    popUpTo(Routes.LOGIN) { inclusive = true }
}
```

### Logout and return to login:
```kotlin
navController.navigate(Routes.LOGIN) {
    popUpTo(0) { inclusive = true }
}
```

---

## ✅ What's Been Completed

- [x] LoginViewModel with state management
- [x] LoginScreen with Material Design 3 UI
- [x] Form validation (email, password)
- [x] Error handling (network, HTTP, unknown)
- [x] Navigation integration
- [x] Loading states
- [x] Toast notifications
- [x] Password visibility toggle
- [x] Keyboard actions
- [x] Bi-directional navigation with SignUp

---

## ⚠️ What Still Needs Implementation

- [ ] Token persistence (DataStore/SharedPreferences)
- [ ] Auto-login on app launch
- [ ] Logout functionality
- [ ] Forgot password feature
- [ ] Token refresh mechanism
- [ ] User profile screen
- [ ] Session timeout handling

---

**Your login feature is now fully functional! 🎉**

Users can now login with email and password, and the app will navigate them to the home screen upon success. The login and signup screens work together seamlessly with proper navigation flow.

**Recommended Next Step:** Implement token persistence to save the user's login session (see Next Steps #1).
