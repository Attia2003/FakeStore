# Sign-Up Feature - Quick Integration Guide

## ✅ Setup Complete!

The sign-up feature has been fully integrated into your navigation system. Here's how to use it:

---

## 🚀 How to Navigate to Sign-Up Screen

### Option 1: From Any Screen with NavController
```kotlin
navController.navigate(Routes.SIGNUP)
```

### Option 2: Add a Button in AccountScreen
If you want to add a "Sign Up" button to your Account screen:

```kotlin
Button(
    onClick = { navController.navigate(Routes.SIGNUP) },
    modifier = Modifier.fillMaxWidth()
) {
    Text("Create Account")
}
```

### Option 3: Set as Start Destination (for first-time users)
If you want the signup screen to be the first screen users see:

In `AppNavGraph.kt`, change the start destination:
```kotlin
NavHost(
    navController = navController,
    startDestination = Routes.SIGNUP,  // Changed from Routes.HOME
    modifier = Modifier.padding(paddingValues)
)
```

---

## 🔄 Navigation Flow

```
SignUpScreen
    ├── On Success → Navigate to HOME (with popUpTo to clear back stack)
    └── On "Login" click → Navigate to LOGIN (when you create login screen)
```

---

## 🧪 Testing the Sign-Up Feature

### 1. **Manual Testing:**
Simply navigate to the signup screen and fill in the form:
- **Name:** John Doe
- **Email:** john.doe@example.com
- **Password:** password123
- **Confirm Password:** password123

### 2. **Test Cases to Verify:**

#### ✅ Validation Tests:
- [ ] Empty name shows error
- [ ] Empty email shows error
- [ ] Invalid email format shows error
- [ ] Empty password shows error
- [ ] Password less than 6 characters shows error
- [ ] Passwords don't match shows error

#### ✅ UI/UX Tests:
- [ ] Password visibility toggle works
- [ ] Confirm password visibility toggle works
- [ ] Loading indicator appears during signup
- [ ] Button disables during loading
- [ ] Toast appears on success
- [ ] Toast appears on error
- [ ] Keyboard "Next" navigates to next field
- [ ] Keyboard "Done" submits form

#### ✅ Navigation Tests:
- [ ] Successful signup navigates to home
- [ ] "Login" link is clickable (will navigate when login screen is created)
- [ ] Back button from signup works properly

---

## 📱 Screenshot Testing

The signup screen features:
- ✨ Gradient background (primary color fadeout to background)
- 🎨 Material Design 3 components
- 🔒 Password visibility toggles
- ✅ Real-time form validation
- 📱 Responsive scrollable layout
- 🌙 Dark mode support (automatic based on theme)

---

## 🔌 API Endpoint

The signup feature uses:
- **Endpoint:** `POST /users/`
- **Request Body:**
  ```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "password123",
    "avatar": "https://api.lorem.space/image/face?w=640&h=480"
  }
  ```
- **Response:**
  ```json
  {
    "id": 123,
    "email": "john.doe@example.com",
    "name": "John Doe",
    "avatar": "https://api.lorem.space/image/face?w=640&h=480",
    "creationAt": "2026-02-06T05:07:55.000Z",
    "updatedAt": "2026-02-06T05:07:55.000Z"
  }
  ```

---

## 🎯 Next Steps (Optional)

1. **Create Login Screen:**
   - Follow the same structure as SignUpScreen
   - Create `LoginScreen.kt`, `LoginViewModel.kt`, `LoginUiState.kt`
   - Add to navigation with `Routes.LOGIN`

2. **Add "Already have account?" link to Account screen:**
   ```kotlin
   Text(
       text = "Already have an account? Login",
       modifier = Modifier.clickable { 
           navController.navigate(Routes.LOGIN) 
       }
   )
   ```

3. **Persist User Session:**
   - Use DataStore or SharedPreferences to save auth token
   - Add auto-login on app launch
   - Add logout functionality

4. **Profile Screen:**
   - Display user information from SignUpResponse
   - Allow users to edit their profile

---

## 🐛 Troubleshooting

### Issue: Navigation not working
**Solution:** Make sure you're passing the navController to where you're calling the navigate function.

### Issue: API returns 404
**Solution:** Verify your base URL in Retrofit configuration. The endpoint should be `POST /users/`.

### Issue: Validation messages not showing
**Solution:** Make sure you're testing with invalid data. Try empty fields or passwords less than 6 characters.

### Issue: Screen not appearing in navigation
**Solution:** Rebuild the project (`Ctrl+F9` or `Build > Make Project`) to ensure all new files are compiled.

---

## 📞 Quick Commands

### Navigate to Signup:
```kotlin
navController.navigate(Routes.SIGNUP)
```

### Navigate to Home after signup:
```kotlin
navController.navigate(Routes.HOME) {
    popUpTo(Routes.SIGNUP) { inclusive = true }
}
```

---

**That's it! Your signup feature is ready to use! 🎉**
