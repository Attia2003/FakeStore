# Quick Reference - Add Product Feature

## Key Files Created/Modified

### ✅ New Files (9)
1. `AddProductScreen.kt` - Main UI with form
2. `AddProductViewModel.kt` - State management + business logic
3. `AddProductUseCase.kt` - Use case for creating products
4. `AddProductRepository.kt` - Repository interface
5. `AddProductRepoImpl.kt` - Repository implementation
6. `AddProductUiState.kt` - UI state sealed interface
7. `createProduct.kt` - DTOs (Request & Response)
8. `AddProductRepoModule.kt` - DI for repository
9. `AddProductUseCaseModule.kt` - DI for use case

### 📝 Modified Files (5)
1. `Routes.kt` - Changed CART → ADD_PRODUCT
2. `BottomNavItem.kt` - Removed Cart item
3. `MainScaffold.kt` - Changed FAB from Cart to Add icon
4. `AppNavGraph.kt` - Updated navigation routes
5. `ApiService.kt` - Added POST /products endpoint

### 🗑️ Deleted Files (1)
1. `CartScreen.kt` - Removed completely

---

## How to Use

### 1. **Run the App**
```bash
.\gradlew.bat assembleDebug
```

### 2. **Navigate to Add Product**
- Look for the **+ (Plus)** FAB button at the bottom center
- Click it to open the Add Product form

### 3. **Fill the Form**
- **Title** (required): Enter product name
- **Price** (required): Enter numeric price
- **Description** (required): Enter product description
- **Image URL** (optional): Enter image URL or leave blank for default
- **Category ID** (optional): Enter category ID (defaults to 1)

### 4. **Submit**
- Click "Create Product" button
- Wait for loading indicator
- Success: Toast message + auto-navigate back
- Error: Toast with error message + stay on form

---

## Form Validation Rules

| Field       | Required | Type   | Validation                      |
|-------------|----------|--------|---------------------------------|
| Title       | ✅ Yes   | Text   | Cannot be blank                 |
| Price       | ✅ Yes   | Number | Must be valid Long number       |
| Description | ✅ Yes   | Text   | Cannot be blank                 |
| Image URL   | ❌ No    | Text   | Any string, defaults if empty   |
| Category ID | ❌ No    | Number | Defaults to 1 if invalid        |

---

## API Details

**Endpoint:** `POST /products`

**Headers:**
```
Content-Type: application/json
```

**Request Example:**
```json
{
  "title": "New Smartwatch",
  "price": 299,
  "description": "A modern smartwatch with advanced features",
  "categoryId": 2,
  "images": ["https://example.com/watch.jpg"]
}
```

**Response Example (Success - 201):**
```json
{
  "id": 456,
  "title": "New Smartwatch",
  "price": 299,
  "description": "A modern smartwatch with advanced features",
  "category": {
    "id": 2,
    "name": "Electronics"
  },
  "images": ["https://example.com/watch.jpg"]
}
```

---

## Error Handling

The app handles three types of errors:

### 1. **No Internet (IOException)**
- **Message:** "No internet connection"
- **User Action:** Check network and retry

### 2. **HTTP Error**
- **Message:** "Server error: [code]"
- **Common Codes:**
  - 400: Bad Request (invalid data)
  - 401: Unauthorized
  - 404: Not Found
  - 500: Server Error

### 3. **Unknown Error**
- **Message:** "An error occurred"
- **User Action:** Retry or report issue

---

## Testing Checklist

- [ ] FAB shows Plus icon (not shopping cart)
- [ ] FAB navigates to Add Product screen
- [ ] Form displays all fields correctly
- [ ] Required field validation works
- [ ] Price field only accepts numbers
- [ ] Submit button disabled during loading
- [ ] Loading indicator shows during API call
- [ ] Success toast appears after creation
- [ ] Error toast appears on failure
- [ ] Auto-navigate back on success
- [ ] Close button works

---

## Common Issues & Solutions

### Issue: "Title is required" error
**Solution:** Make sure the title field is not empty

### Issue: "Valid price is required" error
**Solution:** Enter only numeric values (no letters or symbols)

### Issue: Build fails
**Solution:** 
```bash
.\gradlew.bat clean
.\gradlew.bat assembleDebug
```

### Issue: Hilt dependency injection error
**Solution:** Make sure all modules are properly annotated:
- `@HiltViewModel` on ViewModel
- `@Inject` on constructors
- `@Module` and `@InstallIn` on DI modules

---

## Code Snippets for Common Tasks

### Add a new field to the form
```kotlin
// 1. Add state variable
var newField by remember { mutableStateOf("") }

// 2. Add TextField
OutlinedTextField(
    value = newField,
    onValueChange = { newField = it },
    label = { Text("New Field") },
    modifier = Modifier.fillMaxWidth()
)

// 3. Update createProduct call
viewModel.createProduct(
    // ... existing params
    newField = newField
)
```

### Change default category
```kotlin
// In AddProductScreen.kt, change initial state
var categoryId by remember { mutableStateOf("5") } // Change from "1" to "5"
```

### Add custom validation
```kotlin
var customError by remember { mutableStateOf(false) }

// In OutlinedTextField
OutlinedTextField(
    value = customField,
    onValueChange = {
        customField = it
        customError = !it.matches(Regex("your-pattern"))
    },
    isError = customError,
    supportingText = {
        if (customError) Text("Custom error message")
    }
)
```

---

## Architecture Summary

```
UI Layer        ViewModel        UseCase        Repository        API
   │                │                │               │              │
   ├──Form Input───▶│                │               │              │
   │                ├─Call UseCase──▶│               │              │
   │                │                ├─Call Repo────▶│              │
   │                │                │               ├─HTTP Post───▶│
   │                │                │               │◀─Response────│
   │                │                │◀─Return Data──│              │
   │                │◀─Return State──│               │              │
   │◀─Update UI─────│                │               │              │
```

---

## Next Steps

1. ✅ **Done:** Cart removed, Add Product implemented
2. 🔄 **Optional:** Add image picker for local uploads
3. 🔄 **Optional:** Add category dropdown selector
4. 🔄 **Optional:** Add image preview before submit
5. 🔄 **Optional:** Implement edit/delete functionality

---

**Build Status:** ✅ BUILD SUCCESSFUL  
**Last Build:** 37 seconds  
**Tasks Executed:** 17 executed, 24 up-to-date
