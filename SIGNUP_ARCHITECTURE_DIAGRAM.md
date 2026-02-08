# Sign-Up Feature - Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────┐
│                         PRESENTATION LAYER                          │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌──────────────────┐         ┌──────────────────┐                │
│  │  SignUpScreen    │────────▶│ SignUpViewModel  │                │
│  │                  │         │                  │                │
│  │  - Form UI       │         │  - StateFlow     │                │
│  │  - Validation    │         │  - Error Handling│                │
│  │  - Navigation    │         │  - Logging       │                │
│  └──────────────────┘         └────────┬─────────┘                │
│                                        │                           │
│                                        │ uses                      │
│                                        ▼                           │
└────────────────────────────────────────┼───────────────────────────┘
                                         │
┌────────────────────────────────────────┼───────────────────────────┐
│                          DOMAIN LAYER  │                           │
├────────────────────────────────────────┼───────────────────────────┤
│                                        │                           │
│                           ┌────────────▼──────────┐                │
│                           │   SignUpUseCase       │                │
│                           │                       │                │
│                           │  - Business Logic     │                │
│                           │  - call(request)      │                │
│                           └───────────┬───────────┘                │
│                                       │                            │
│                                       │ depends on                 │
│                                       ▼                            │
│                       ┌───────────────────────────┐                │
│                       │  SignUpRepository         │                │
│                       │  (Interface/Contract)     │                │
│                       └───────────┬───────────────┘                │
│                                   │                                │
└───────────────────────────────────┼────────────────────────────────┘
                                    │
┌───────────────────────────────────┼────────────────────────────────┐
│                         DATA LAYER│                                │
├───────────────────────────────────┼────────────────────────────────┤
│                                   │                                │
│                      ┌────────────▼──────────┐                     │
│                      │  SignUpRepoImpl       │                     │
│                      │                       │                     │
│                      │  - Implements Repo    │                     │
│                      └───────────┬───────────┘                     │
│                                  │                                 │
│                                  │ uses                            │
│                                  ▼                                 │
│                         ┌─────────────────┐                        │
│                         │   ApiService    │                        │
│                         │                 │                        │
│                         │  POST /users/   │                        │
│                         └────────┬────────┘                        │
│                                  │                                 │
│                                  │                                 │
│  DTOs:                           │ sends                           │
│  ┌─────────────────┐             ▼                                 │
│  │ SignUpRequest   │────────▶ Network                             │
│  │ - name          │                                               │
│  │ - email         │             │                                 │
│  │ - password      │             │ receives                        │
│  │ - avatar        │             ▼                                 │
│  └─────────────────┘    ┌─────────────────┐                       │
│                         │ SignUpResponse  │                        │
│                         │ - id            │                        │
│                         │ - email         │                        │
│                         │ - name          │                        │
│                         │ - avatar        │                        │
│                         │ - creationAt    │                        │
│                         │ - updatedAt     │                        │
│                         └─────────────────┘                        │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                      DEPENDENCY INJECTION                           │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│                      ┌──────────────────────┐                      │
│                      │  SignUpRepoModule    │                      │
│                      │                      │                      │
│                      │  @Provides           │                      │
│                      │  - SignUpRepository  │                      │
│                      │  - SignUpUseCase     │                      │
│                      │                      │                      │
│                      │  @Singleton          │                      │
│                      └──────────────────────┘                      │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘


UI STATE FLOW:
═══════════════

    Idle ──┐
           │
           ├──▶ Loading ──┐
           │              │
           │              ├──▶ Success ──▶ Navigate to Home
           │              │
           └──────────────┴──▶ Error ──▶ Show Toast & Reset


NAVIGATION FLOW:
════════════════

    Account Screen
         │
         │ (Click "Sign Up")
         ▼
    SignUpScreen
         │
         ├─── Fill Form
         │       │
         │       ├─── Validate locally
         │       │
         │       └─── Submit to API
         │              │
         │              ├─── Success ──▶ Navigate to HOME
         │              │
         │              └─── Error ──▶ Show Error Toast
         │
         └─── Click "Login" ──▶ Navigate to LOGIN (future)


FORM VALIDATION:
════════════════

  Name: Required, Not Blank
  Email: Required, Valid Email Pattern
  Password: Required, Min 6 Characters
  Confirm Password: Required, Must Match Password

```

## Key Design Patterns Used

1. **Clean Architecture**: Separation of concerns across layers
2. **MVVM**: ViewModel manages UI state and business logic
3. **Repository Pattern**: Abstraction over data source
4. **Use Case Pattern**: Single responsibility for business operations
5. **Dependency Injection**: Hilt manages dependencies
6. **Reactive Programming**: StateFlow for reactive UI updates
7. **Single Source of Truth**: ViewModel holds UI state

## Files Created (10 Total)

### Data Layer (4 files)
- `SignUpRequest.kt` - Request DTO
- `SignUpResponse.kt` - Response DTO
- `SignUpRepoImpl.kt` - Repository implementation
- `ApiService.kt` (Modified) - Added signup endpoint

### Domain Layer (2 files)
- `SignUpRepository.kt` - Repository contract
- `SignUpUseCase.kt` - Business logic

### Presentation Layer (2 files)
- `SignUpUiState.kt` - UI state sealed class
- `SignUpViewModel.kt` - State management
- `SignUpScreen.kt` - UI composable

### DI Layer (1 file)
- `SignUpRepoModule.kt` - Hilt module

### Navigation (2 files modified)
- `Routes.kt` - Added SIGNUP route
- `AppNavGraph.kt` - Added signup composable
