# 📈 StockPriceTracker

<!-- License & Status -->
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![Status](https://img.shields.io/badge/Status-Maintained-brightgreen.svg)
![Unit Tests](https://img.shields.io/badge/Unit%20Tests-Passing-brightgreen.svg?logo=github)
![UI Tests](https://img.shields.io/badge/UI%20Tests-Passing-brightgreen.svg?logo=android)
![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android)
![Architecture](https://img.shields.io/badge/Architecture-MVI%20%2B%20Clean-blueviolet.svg)

<!-- Language & Core -->
![Kotlin](https://img.shields.io/badge/Kotlin-2.x-7F52FF.svg?logo=kotlin&logoColor=white)
![Min SDK](https://img.shields.io/badge/Min%20SDK-24-informational.svg?logo=android)
![Target SDK](https://img.shields.io/badge/Target%20SDK-36-informational.svg?logo=android)

<!-- Jetpack Compose -->
![Compose BOM](https://img.shields.io/badge/Compose%20BOM-managed-4285F4.svg?logo=jetpackcompose&logoColor=white)
![Material3](https://img.shields.io/badge/Material3-BOM%20Managed-4285F4.svg?logo=material-design&logoColor=white)
![Navigation Compose](https://img.shields.io/badge/Navigation%20Compose-✓-4285F4.svg)
![Activity Compose](https://img.shields.io/badge/Activity%20Compose-✓-4285F4.svg)
![Lifecycle Compose](https://img.shields.io/badge/Lifecycle%20Compose-✓-4285F4.svg)

<!-- DI -->
![Koin Core](https://img.shields.io/badge/Koin%20Core-✓-E07B39.svg)
![Koin Android](https://img.shields.io/badge/Koin%20Android-✓-E07B39.svg)
![Koin Compose](https://img.shields.io/badge/Koin%20Compose-✓-E07B39.svg)

<!-- Coroutines -->
![Coroutines](https://img.shields.io/badge/Coroutines-✓-7F52FF.svg?logo=kotlin&logoColor=white)

<!-- Storage & Logging -->
![DataStore](https://img.shields.io/badge/DataStore%20Preferences-✓-4285F4.svg)
![Timber](https://img.shields.io/badge/Timber-✓-8BC34A.svg)
![LeakCanary](https://img.shields.io/badge/LeakCanary-debug-FF6B6B.svg)

<!-- Testing -->
![JUnit5](https://img.shields.io/badge/JUnit%205-✓-25A162.svg)
![Mockito](https://img.shields.io/badge/Mockito%20Kotlin-✓-25A162.svg)
![Coroutines Test](https://img.shields.io/badge/Coroutines%20Test-✓-25A162.svg)
![Espresso](https://img.shields.io/badge/Espresso-✓-25A162.svg?logo=android)
![Compose Test](https://img.shields.io/badge/Compose%20UI%20Test-BOM%20Managed-25A162.svg)

---

> A production-grade Android application for real-time stock price monitoring, built with **MVI architecture**, **Jetpack Compose**, and **Clean Architecture** principles.

---

## 📦 Libraries & Dependencies

All versions are centrally managed via `gradle/libs.versions.toml` (Gradle Version Catalog).

### Core Android
| Library | Artifact | Purpose |
|---------|----------|---------|
| AndroidX Core KTX | `androidx.core:core-ktx` | Kotlin extensions for Android core APIs |
| AndroidX AppCompat | `androidx.appcompat:appcompat` | Backward-compatible UI components |

### Jetpack Compose
| Library | Artifact | Purpose |
|---------|----------|---------|
| Compose BOM | `androidx.compose:compose-bom` | Bill of Materials — manages all Compose versions |
| Compose UI | `androidx.compose.ui:ui` | Core Compose UI primitives |
| Compose UI Graphics | `androidx.compose.ui:ui-graphics` | Graphics & drawing APIs |
| Compose Material3 | `androidx.compose.material3:material3` | Material Design 3 components |
| Material Icons Extended | `androidx.compose.material:material-icons-extended` | Full Material icon set |
| Compose UI Tooling Preview | `androidx.compose.ui:ui-tooling-preview` | `@Preview` support in IDE |
| Navigation Compose | `androidx.navigation:navigation-compose` | Compose navigation graph |
| Activity Compose | `androidx.activity:activity-compose` | `setContent {}` & Compose Activity integration |

### Lifecycle & ViewModel
| Library | Artifact | Purpose |
|---------|----------|---------|
| Lifecycle Runtime KTX | `androidx.lifecycle:lifecycle-runtime-ktx` | `lifecycleScope`, `repeatOnLifecycle` |
| Lifecycle Runtime Compose | `androidx.lifecycle:lifecycle-runtime-compose` | `collectAsStateWithLifecycle` |
| Lifecycle ViewModel Compose | `androidx.lifecycle:lifecycle-viewmodel-compose` | `viewModel()` in Compose |

### Dependency Injection — Koin
| Library | Artifact | Purpose |
|---------|----------|---------|
| Koin Core | `io.insert-koin:koin-core` | Koin DI core |
| Koin Android | `io.insert-koin:koin-android` | Android-specific Koin support |
| Koin AndroidX Compose | `io.insert-koin:koin-androidx-compose` | `koinViewModel()` in Compose |

### Coroutines
| Library | Artifact | Purpose |
|---------|----------|---------|
| Coroutines Core | `org.jetbrains.kotlinx:kotlinx-coroutines-core` | Coroutines foundation |
| Coroutines Android | `org.jetbrains.kotlinx:kotlinx-coroutines-android` | Android Main dispatcher |

### Persistence
| Library | Artifact | Purpose |
|---------|----------|---------|
| DataStore Preferences | `androidx.datastore:datastore-preferences` | Key-value storage via Kotlin Flow |

### Logging & Debug
| Library | Artifact | Purpose |
|---------|----------|---------|
| Timber | `com.jakewharton.timber:timber` | Extensible logging utility |
| LeakCanary | `com.squareup.leakcanary:leakcanary-android` | Memory leak detection (debug only) |

### Testing — Unit
| Library | Artifact | Purpose |
|---------|----------|---------|
| JUnit 4 | `junit:junit` | Base unit testing framework |
| JUnit Jupiter (JUnit 5) | `org.junit.jupiter:junit-jupiter` | Modern JUnit 5 API |
| JUnit Jupiter Engine | `org.junit.jupiter:junit-jupiter-engine` | JUnit 5 test engine (runtime) |
| JUnit Platform Launcher | `org.junit.platform:junit-platform-launcher` | Test launcher (runtime) |
| Mockito Core | `org.mockito:mockito-core` | Mocking framework |
| Mockito JUnit Jupiter | `org.mockito:mockito-junit-jupiter` | Mockito JUnit 5 extension |
| Mockito Kotlin | `org.mockito.kotlin:mockito-kotlin` | Kotlin-friendly Mockito DSL |
| Coroutines Test | `org.jetbrains.kotlinx:kotlinx-coroutines-test` | Testing coroutines & `TestDispatcher` |

### Testing — Instrumented
| Library | Artifact | Purpose |
|---------|----------|---------|
| AndroidX JUnit | `androidx.test.ext:junit` | AndroidX JUnit runner |
| Espresso Core | `androidx.test.espresso:espresso-core` | UI instrumentation testing |
| Compose UI Test JUnit4 | `androidx.compose.ui:ui-test-junit4` | Compose UI testing (BOM-managed) |

### Debug Instrumentation
| Library | Artifact | Purpose |
|---------|----------|---------|
| Compose UI Tooling | `androidx.compose.ui:ui-tooling` | Layout inspector & preview (debug) |
| Compose UI Test Manifest | `androidx.compose.ui:ui-test-manifest` | Test activity manifest (debug) |

---

## 🎬 Demo

### 📱 Application Walkthrough
> *(Replace with your screen recording or GIF)*

```
[▶️ Watch App Demo Video]
app_demo.mp4 — Add your video to the /media folder and embed here
```

### 🧪 Unit Test Run
> *(Replace with your test execution recording)*

```
[▶️ Watch Unit Test Run]
unit_tests_demo.mp4 — Run via: ./gradlew test and record output
```

### 🖥️ Jetpack Compose UI Tests
> *(Replace with your UI test recording)*

```
[▶️ Watch Compose UI Test Run]
compose_ui_tests_demo.mp4 — Run via: ./gradlew connectedAndroidTest
```

---

## 🏗️ Architecture — MVI (Model-View-Intent)

This project follows the **MVI (Model-View-Intent)** pattern, layered on top of **Clean Architecture** with strict separation of concerns across three layers.

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                   │
│                                                         │
│   ┌──────────┐    Intent     ┌─────────────┐           │
│   │  Compose │  ──────────►  │  ViewModel  │           │
│   │   UI     │               │  (MVI Core) │           │
│   │          │  ◄──────────  │             │           │
│   └──────────┘   ViewState   └──────┬──────┘           │
│                                     │ UiState / Effect  │
└─────────────────────────────────────┼───────────────────┘
                                      │
┌─────────────────────────────────────▼───────────────────┐
│                      DOMAIN LAYER                       │
│                                                         │
│   ┌──────────────┐        ┌──────────────────────┐     │
│   │  Use Cases   │ ──────►│  Repository Interface │     │
│   │  (Business   │        │  (Contract / Port)   │     │
│   │   Logic)     │        └──────────────────────┘     │
│   └──────────────┘                                      │
└─────────────────────────────────────────────────────────┘
                                      │
┌─────────────────────────────────────▼───────────────────┐
│                       DATA LAYER                        │
│                                                         │
│   ┌────────────────┐      ┌──────────────────────┐     │
│   │  Repository    │      │   Remote Data Source  │     │
│   │  Implementation│─────►│   (Retrofit / API)    │     │
│   │                │      └──────────────────────┘     │
│   │                │      ┌──────────────────────┐     │
│   │                │─────►│   Local Data Source   │     │
│   └────────────────┘      │   (Room / Cache)      │     │
│                            └──────────────────────┘     │
└─────────────────────────────────────────────────────────┘
```

### MVI Data Flow

```
User Action
    │
    ▼
Intent (sealed class)
    │
    ▼
ViewModel.handleIntent()
    │
    ├──► Use Case (Domain Logic)
    │         │
    │         ▼
    │    Repository → API / DB
    │         │
    │         ▼
    │    Result (Success / Error)
    │
    ▼
UiState (immutable data class)
    │
    ▼
Compose UI re-renders reactively
```

### Key MVI Components

| Component | Role |
|-----------|------|
| `Intent` | Sealed class representing all possible user actions |
| `UiState` | Immutable data class representing the complete screen state |
| `SideEffect` | One-time events (navigation, toasts, errors) via `Channel` |
| `ViewModel` | Processes intents, invokes use cases, emits new state |
| `Composable` | Observes `StateFlow<UiState>`, sends intents on interaction |

---

## 🧱 SOLID Principles Applied

### 1. Single Responsibility Principle (SRP)
Each class has one reason to change. `GetStockPriceUseCase` handles only the business rule of fetching stock prices. `StockRepository` handles only data access. The ViewModel handles only state management — never direct API calls.

### 2. Open/Closed Principle (OCP)
The `Repository` interface is open for extension (add a new data source) but closed for modification. New quote providers can be added without touching existing code, simply by implementing the `StockDataSource` interface.

### 3. Liskov Substitution Principle (LSP)
`StockRepositoryImpl` is a drop-in substitute for `StockRepository` interface. The domain layer never needs to know which concrete implementation it receives — fully tested via fakes in unit tests.

### 4. Interface Segregation Principle (ISP)
Data sources are split into focused interfaces: `RemoteStockDataSource` and `LocalStockDataSource`. The repository only depends on the interface relevant to each operation, never a bloated all-in-one interface.

### 5. Dependency Inversion Principle (DIP)
High-level modules (ViewModel, Use Cases) depend on abstractions (interfaces), not concrete implementations. All dependencies are injected via **Hilt**, ensuring the dependency graph is inverted and testable.

---

## 🔄 Application Flow

```
App Launch
    │
    ▼
MainActivity (setContent → StockTrackerApp)
    │
    ▼
NavHost → StockListScreen (default destination)
    │
    ▼
StockListViewModel.init {
    handleIntent(StockIntent.LoadStocks)
}
    │
    ├──► Emits UiState.Loading → Compose shows shimmer/progress
    │
    ▼
GetStocksUseCase.execute()
    │
    ▼
StockRepositoryImpl.getStocks()
    │
    ├──► RemoteDataSource → Retrofit → Stock API
    │         │
    │         ├── Success → Map DTO → Domain Model
    │         │         └──► Cache in Room (LocalDataSource)
    │         │
    │         └── Error → Emit UiState.Error(message)
    │
    ▼
Flow<List<Stock>> collected in ViewModel
    │
    ▼
Emit UiState.Success(stocks)
    │
    ▼
Compose re-renders → StockList displayed
    │
    ▼
User taps a Stock Card
    │
    ▼
StockIntent.NavigateToDetail(symbol) sent
    │
    ▼
SideEffect.Navigate(route) → NavController.navigate()
    │
    ▼
StockDetailScreen → StockDetailViewModel
    │
    ▼
GetStockDetailUseCase → fetch chart data, live price
    │
    ▼
UiState.Success → Render price chart + statistics
```

### State Machine

```
         ┌─────────┐
         │  Idle   │
         └────┬────┘
              │ LoadStocks Intent
              ▼
         ┌─────────┐
         │ Loading │ ◄──── Refresh Intent
         └────┬────┘
         ┌────┴────┐
         │         │
         ▼         ▼
    ┌─────────┐  ┌───────┐
    │ Success │  │ Error │
    └────┬────┘  └───┬───┘
         │           │ Retry Intent
         └─────┬─────┘
               ▼
           ┌─────────┐
           │ Loading │  (loop)
           └─────────┘
```

---

## 📁 Package Structure

```
com.mirzaadil.stockpricetracker/
│
├── di/                          # Hilt modules
│   ├── NetworkModule.kt
│   ├── RepositoryModule.kt
│   └── UseCaseModule.kt
│
├── data/
│   ├── remote/
│   │   ├── api/StockApiService.kt
│   │   ├── dto/StockDto.kt
│   │   └── datasource/RemoteStockDataSource.kt
│   ├── local/
│   │   ├── dao/StockDao.kt
│   │   ├── entity/StockEntity.kt
│   │   └── datasource/LocalStockDataSource.kt
│   ├── mapper/StockMapper.kt
│   └── repository/StockRepositoryImpl.kt
│
├── domain/
│   ├── model/Stock.kt
│   ├── repository/StockRepository.kt   ← interface
│   └── usecase/
│       ├── GetStocksUseCase.kt
│       └── GetStockDetailUseCase.kt
│
└── presentation/
    ├── stocklist/
    │   ├── StockListScreen.kt           ← Composable
    │   ├── StockListViewModel.kt
    │   ├── StockListIntent.kt           ← sealed class
    │   ├── StockListUiState.kt          ← data class
    │   └── StockListEffect.kt           ← sealed class
    ├── stockdetail/
    │   ├── StockDetailScreen.kt
    │   ├── StockDetailViewModel.kt
    │   ├── StockDetailIntent.kt
    │   └── StockDetailUiState.kt
    ├── navigation/
    │   └── StockNavGraph.kt
    └── components/
        ├── StockCard.kt
        ├── PriceChart.kt
        └── LoadingShimmer.kt
```

---


## 🧪 Testing Strategy

### Unit Tests (`/test`)
- ViewModel intent handling — verifies state transitions using `Turbine`
- Use case business logic — isolated with MockK fakes
- Repository logic — mocked remote and local sources
- Mapper correctness — DTO → Domain → UI model mappings

### Jetpack Compose UI Tests (`/androidTest`)
- Screen rendering with predefined `UiState`
- User interaction simulation (`performClick`, `performTextInput`)
- Navigation assertion between screens
- Accessibility checks

---

## 👨‍💻 About the Author

**Mirza Adil** — Senior Android Engineer with 10+ years of experience building scalable, high-performance mobile applications across ride-hailing, e-commerce, and fintech domains.

🔗 [GitHub](https://github.com/mirza-adil) [GitHub](https://github.com/mirzaadil)  &nbsp;|&nbsp; [LinkedIn](https://linkedin.com/in/mirza-adil)

