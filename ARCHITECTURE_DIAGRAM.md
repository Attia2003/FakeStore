## Add Product Feature - Clean Architecture Flow

```
┌─────────────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                              │
├─────────────────────────────────────────────────────────────────────┤
│                                                                       │
│  ┌──────────────────────┐          ┌──────────────────────┐         │
│  │  AddProductScreen    │          │ AddProductViewModel  │         │
│  │  (Composable UI)     │◄─────────│  (State Management)  │         │
│  └──────────────────────┘          └──────────────────────┘         │
│          │                                    │                      │
│          │  Form Input                        │ StateFlow            │
│          │  - Title                           │ (Idle/Loading/       │
│          │  - Price                           │  Success/Error)      │
│          │  - Description                     │                      │
│          │  - Image URL                       │                      │
│          │  - Category ID                     │                      │
│          │                                    ▼                      │
│          └───────────────────────►  createProduct()                  │
│                                                │                      │
└────────────────────────────────────────────────┼──────────────────────┘
                                                 │
                                                 │ Calls
                                                 ▼
┌─────────────────────────────────────────────────────────────────────┐
│                       DOMAIN LAYER                                   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                       │
│  ┌────────────────────────┐        ┌──────────────────────────┐     │
│  │  AddProductUseCase     │        │ AddProductRepository     │     │
│  │  (Business Logic)      │───────▶│   (Interface/Contract)   │     │
│  └────────────────────────┘        └──────────────────────────┘     │
│           │                                     ▲                    │
│           │ call(request)                       │                    │
│           ▼                                     │ implements         │
└──────────────────────────────────────────────────┼──────────────────┘
                                                   │
┌──────────────────────────────────────────────────┼──────────────────┐
│                        DATA LAYER                │                   │
├──────────────────────────────────────────────────┼──────────────────┤
│                                                  │                   │
│  ┌────────────────────────┐                     │                   │
│  │ AddProductRepoImpl     │◄────────────────────┘                   │
│  │  (Implementation)      │                                          │
│  └────────────────────────┘                                          │
│           │                                                          │
│           │ api.createProduct()                                      │
│           ▼                                                          │
│  ┌────────────────────────┐                                          │
│  │     ApiService         │                                          │
│  │  (Retrofit Interface)  │                                          │
│  └────────────────────────┘                                          │
│           │                                                          │
│           │ POST /products                                           │
│           ▼                                                          │
│  ┌────────────────────────────────────────┐                          │
│  │  CreateProductRequest (DTO)            │                          │
│  │  {                                     │                          │
│  │    title: String                       │                          │
│  │    price: Long                         │                          │
│  │    description: String                 │                          │
│  │    categoryId: Int                     │                          │
│  │    images: List<String>                │                          │
│  │  }                                     │                          │
│  └────────────────────────────────────────┘                          │
│           │                                                          │
│           │ HTTP Request                                             │
│           ▼                                                          │
│  ╔════════════════════════════════════════╗                          │
│  ║      External API Service              ║                          │
│  ║    (FakeStore API Backend)             ║                          │
│  ╚════════════════════════════════════════╝                          │
│           │                                                          │
│           │ HTTP Response                                            │
│           ▼                                                          │
│  ┌────────────────────────────────────────┐                          │
│  │  CreateProductResponse (DTO)           │                          │
│  │  {                                     │                          │
│  │    id: Int                             │                          │
│  │    title: String                       │                          │
│  │    price: Long                         │                          │
│  │    description: String                 │                          │
│  │    category: Category                  │                          │
│  │    images: List<String>                │                          │
│  │  }                                     │                          │
│  └────────────────────────────────────────┘                          │
│                                                                       │
└───────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                    DEPENDENCY INJECTION (Hilt)                       │
├─────────────────────────────────────────────────────────────────────┤
│                                                                       │
│  AddProductUseCaseModule  ──▶  Provides AddProductUseCase           │
│  AddProductRepoModule     ──▶  Binds AddProductRepository            │
│                                                                       │
└───────────────────────────────────────────────────────────────────────┘
```

## Navigation Flow

```
┌─────────────────┐       Click FAB        ┌──────────────────────┐
│   HomeScreen    │ ────────────────────▶   │  AddProductScreen    │
│  (with FAB +)   │                         │   (Form Screen)      │
└─────────────────┘                         └──────────────────────┘
                                                      │
                                                      │ Fill Form
                                                      │ Click Submit
                                                      ▼
                                            ┌──────────────────────┐
                                            │  Loading State       │
                                            │  (Progress Indicator)│
                                            └──────────────────────┘
                                                      │
                                        ┌─────────────┴──────────────┐
                                        │                            │
                                   Success                        Error
                                        │                            │
                                        ▼                            ▼
                        ┌──────────────────────┐    ┌──────────────────────┐
                        │  Toast: "Success!"   │    │  Toast: Error Msg    │
                        │  Navigate Back       │    │  Stay on Screen      │
                        └──────────────────────┘    └──────────────────────┘
                                        │
                                        ▼
                        ┌──────────────────────┐
                        │   Return to Home     │
                        └──────────────────────┘
```

## UI Components

```
┌───────────────────────────────────────────────────────────────┐
│  ╔═══════════════════════════════════════════════════════╗    │
│  ║  Add New Product                                  [X] ║    │
│  ╚═══════════════════════════════════════════════════════╝    │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ Product Title *                                     │     │
│  │ [Text Input Field]                                  │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ Price *                                             │     │
│  │ $ [Number Input Field]                              │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ Description *                                       │     │
│  │ [Multi-line Text Input]                             │     │
│  │                                                     │     │
│  │                                                     │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ Image URL                                           │     │
│  │ [Text Input - https://example.com/image.jpg]        │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ Category ID                                         │     │
│  │ [Number Input Field]                                │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐     │
│  │            [Create Product Button]                  │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                               │
│  * Required fields                                            │
│                                                               │
└───────────────────────────────────────────────────────────────┘
```

## State Management

```
AddProductUiState (Sealed Interface)
│
├── Idle          ──▶  Initial state, form is empty
│
├── Loading       ──▶  API call in progress, show CircularProgressIndicator
│
├── Success       ──▶  Product created successfully
│   │                  - Show success toast
│   │                  - Navigate back to Home
│   └──▶ Data: CreateProductResponse
│
└── Error         ──▶  Something went wrong
    │                  - Show error toast with message
    │                  - Stay on form for retry
    └──▶ Data: UiError (NoInternet | Http | Unknown)
```
