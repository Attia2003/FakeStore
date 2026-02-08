# Bottom Navigation with FAB Cutout Implementation

## Overview
This implementation provides a Jetpack Compose bottom navigation bar with a **real notch/cutout** for the centered FAB using **Material2's BottomAppBar** (for the `cutoutShape` feature) while keeping the rest of the UI in **Material3**.

## Architecture

### Key Components

#### 1. **Routes.kt**
Defines all navigation routes in the application:
- `HOME` - Home screen with products
- `CART` - Shopping cart (accessed via FAB)
- `ACCOUNT` - User account settings
- `DETAILS` - Product detail screen

#### 2. **BottomNavItem.kt**
Sealed class structure defining bottom navigation items:
- **Home** (left) - Home icon
- **Cart** - Shopping cart icon (handled by FAB)
- **Account** (right) - Person icon

#### 3. **MainScaffold.kt**
The core component implementing the hybrid Material2/Material3 approach:
- Uses Material2's `Scaffold` and `BottomAppBar` for the cutout feature
- Applies Material3 colors throughout for visual consistency
- Centers FAB with `FabPosition.Center` and `isFloatingActionButtonDocked = true`
- Creates notch using `cutoutShape = CircleShape`

#### 4. **AppNavGraph.kt**
Main navigation controller that:
- Wraps NavHost with MainScaffold
- Defines all navigation destinations
- Handles navigation state

#### 5. **Screen Components**
- **HomeScreen** - Existing product list screen
- **CartScreen** - New placeholder cart screen
- **AccountScreen** - New placeholder account screen

## How It Works

### Material2 + Material3 Hybrid Approach

The implementation uses:
- **Material2** (`androidx.compose.material`) for `BottomAppBar` and `Scaffold` only
  - Reason: Material3 doesn't support `cutoutShape` for FAB notches
- **Material3** (`androidx.compose.material3`) for all other UI components
  - Theme colors from Material3 are applied to Material2 components

### Navigation Flow

```
MainScaffold (Material2)
├── BottomAppBar with cutoutShape
│   ├── Home icon (left)
│   └── Account icon (right)
├── FloatingActionButton (center, docked)
│   └── Cart icon
└── NavHost
    ├── Home Screen
    ├── Cart Screen
    ├── Account Screen
    └── Details Screen
```

### FAB Navigation
When clicking the centered FAB:
1. Navigates to Cart screen
2. Clears back stack to start destination
3. Saves/restores state for smooth transitions
4. Prevents duplicate destinations

## Gradle Dependencies

### Required Version Catalog (libs.versions.toml)

```toml
[versions]
navigationCompose = "2.8.5"
material = "1.7.5"  # Material2 for BottomAppBar cutoutShape

[libraries]
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
androidx-compose-material = { group = "androidx.compose.material", name = "material", version.ref = "material" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
```

### App build.gradle.kts

```kotlin
dependencies {
    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.9.7")
    
    // Material2 for BottomAppBar with cutoutShape (not available in Material3)
    implementation(libs.androidx.compose.material)
    
    // Material3 for the rest of the UI
    implementation(libs.androidx.compose.material3)
    
    // Other Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
}
```

## File Structure

```
app/src/main/java/com/example/fakestore/
├── core/
│   └── presentation/
│       └── screens/
│           ├── navigation/
│           │   ├── AppNavGraph.kt       # Main navigation setup
│           │   ├── BottomNavItem.kt     # Bottom nav items definition
│           │   ├── MainScaffold.kt      # Scaffold with bottom bar + FAB
│           │   └── Routes.kt            # Navigation routes
│           ├── HomeScreen.kt            # Existing home screen
│           ├── CartScreen.kt            # New cart screen
│           └── AccountScreen.kt         # New account screen
└── ui/
    └── theme/
        └── Theme.kt                     # Material3 theme
```

## Usage

The bottom navigation is automatically integrated when you call `AppNavGraph()` in your `MainActivity`:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavGraph()
        }
    }
}
```

## Customization

### Change Bottom Navigation Items
Edit `bottomNavItems` list in `BottomNavItem.kt`:
```kotlin
val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Account
    // Add more items (but keep Cart for FAB)
)
```

### Change FAB Icon
Edit the `FloatingActionButton` in `MainScaffold.kt`:
```kotlin
Icon(
    imageVector = Icons.Default.YourIcon,
    contentDescription = "Your description"
)
```

### Modify Colors
Colors are automatically pulled from Material3 theme in `Theme.kt`:
- `primary` - Selected items and FAB background
- `onPrimary` - FAB icon color
- `surface` - Bottom bar background
- `onSurface` - Unselected item color

## Key Features

✅ **Real Notch/Cutout** - Uses Material2 `cutoutShape` for authentic FAB integration  
✅ **Material3 Theming** - Consistent colors throughout the app  
✅ **Clean Architecture** - Separated concerns with proper package structure  
✅ **Navigation Compose** - Modern navigation with state management  
✅ **Centered FAB** - Navigates to Cart with proper back stack handling  
✅ **Three Destinations** - Home (left), Cart (FAB), Account (right)  
✅ **Extensible** - Easy to add more screens and features

## Notes

- Material3 currently doesn't support `cutoutShape`, so Material2 is required for this feature
- All Material2 components use Material3 colors for consistency
- The navigation maintains state across screen switches
- The implementation follows Android best practices for Compose navigation
