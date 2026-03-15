# 🛍️ FakeStore

A modern Android shopping demo app built with **Jetpack Compose**, **MVVM + Clean Architecture**, **Hilt DI**, and **Retrofit**. Consumes the [EscuelaJS Fake Store API](https://api.escuelajs.co/api/v1/) to demonstrate product browsing, category filtering, authentication, and product creation.

---

## ✨ Features

### Authentication
- Email/password login via the API
- New user registration flow

### Home Feed
- Scrollable product grid with live API data
- Horizontal category carousel for quick filtering
- Tap any product to view its detail screen

### Product Details
- Fetches full product info by ID
- Displays images, title, price, description, and category

### Create Product
- Form-based product creation that POSTs to the API

### Navigation
- Compose Navigation with named routes: `Login → SignUp → Home → Details → AddProduct → Account`

---

## 🏗️ Architecture

The app follows a **MVVM + Repository + Use Case** pattern, split into three layers:

```
UI Layer          →  Screens, ViewModels, UI State (Compose)
Domain Layer      →  Use Cases, Repository Contracts
Data Layer        →  Repository Implementations, DTOs, Retrofit API
```

Hilt wires everything together across the `core/di` module.

---

## 📁 Project Structure

```
app/src/main/java/com/example/fakestore/
├── core/
│   ├── data/           # DTOs, Retrofit interface, repository implementations
│   ├── domain/         # Repository contracts + use cases
│   ├── di/             # Hilt modules (network, repositories, use cases)
│   └── peresention/    # Screens, components, navigation, ViewModels, UI state
```

> **Note:** The source package uses `peresention` (a known typo for `presentation`).

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | ViewModel + Use Cases + Repository |
| DI | Hilt |
| Networking | Retrofit + Gson + OkHttp Logging Interceptor |
| Image Loading | Coil 3 |
| Async | Kotlin Coroutines + Flow |
