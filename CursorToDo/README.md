# Todo App - MVVM + Clean Architecture

A modern Android Todo application built with Jetpack Compose, following MVVM + Clean Architecture principles with Room database for local storage.

## 📋 Features

- ✅ **Add Todo**: Create new todo items with title and description
- ✏️ **Edit Todo**: Update existing todo items
- 🔄 **Update Todo**: Toggle completion status with checkbox
- 🗑️ **Delete Todo**: Remove todo items from the list
- 💾 **Local Storage**: All data persisted using Room database
- 🔄 **Reactive Updates**: Real-time UI updates using Kotlin Flow
- 🎨 **Modern UI**: Beautiful Material3 design with Jetpack Compose
- ⚡ **MVVM Architecture**: Clean separation of concerns
- 🔌 **Dependency Injection**: Hilt for scalable dependency management

## 🏗️ Architecture

This app follows **Clean Architecture** with three distinct layers:

### Data Layer
- **Room Database**: Local persistence with `TodoDatabase`
- **Entity**: `TodoEntity` - Database table representation
- **DAO**: `TodoDao` - Data access operations with Flow
- **Repository Implementation**: `TodoRepositoryImpl` - Bridges data and domain layers

### Domain Layer
- **Model**: `Todo` - Business logic model
- **Repository Interface**: `TodoRepository` - Contract for data operations
- **Use Cases**:
  - `GetTodosUseCase` - Fetch all todos
  - `AddTodoUseCase` - Add new todo
  - `UpdateTodoUseCase` - Update existing todo
  - `DeleteTodoUseCase` - Delete todo

### Presentation Layer
- **ViewModel**: `TodoViewModel` - Manages UI state with StateFlow
- **UI State**: `TodoUiState` - Represents current UI state
- **Composables**:
  - `TodoScreen` - Main screen with list and FAB
  - `TodoItem` - Individual todo item component
  - `TodoDialog` - Add/Edit dialog

### Dependency Injection (Hilt)
- **Application**: `TodoApplication` - Hilt entry point
- **Modules**:
  - `DatabaseModule` - Provides Room database and DAO
  - `AppModule` - Binds repository interface to implementation

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Database**: Room (SQLite)
- **DI**: Hilt
- **Async**: Kotlin Coroutines + Flow
- **Design**: Material3

## 📦 Dependencies

### Core Android & Compose
- Jetpack Compose BOM
- Material3
- Material Icons Extended
- Activity Compose
- Lifecycle ViewModel Compose

### Room Database
```kotlin
implementation(libs.room.runtime)
implementation(libs.room.ktx)
ksp(libs.room.compiler)
```

### Hilt (Dependency Injection)
```kotlin
implementation(libs.hilt.android)
ksp(libs.hilt.compiler)
implementation(libs.hilt.navigation.compose)
```

### Coroutines
```kotlin
implementation(libs.kotlinx.coroutines.android)
implementation(libs.kotlinx.coroutines.core)
```

## 🚀 Getting Started

### Prerequisites

- **Java**: JDK 21 (configured in `gradle.properties`)
- **Android Studio**: Hedgehog or newer
- **Min SDK**: 35 (Android 15)
- **Target SDK**: 36
- **Gradle**: 8.13+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd CursorToDo
   ```

2. **Open in Android Studio**
   - File → Open → Select project directory

3. **Sync Gradle**
   - Android Studio will automatically sync dependencies

4. **Run the app**
   - Click Run or press `Shift + F10`
   - Select an emulator or connected device

## 📁 Project Structure

```
com.poc.cursortodo/
├── data/                          # Data Layer
│   ├── local/                     # Database components
│   │   ├── TodoEntity.kt         # Room entity
│   │   ├── TodoDao.kt            # Data access object
│   │   └── TodoDatabase.kt       # Database definition
│   └── repository/                # Repository implementation
│       └── TodoRepositoryImpl.kt # Room repository
│
├── domain/                        # Domain Layer (Business Logic)
│   ├── model/                     # Domain models
│   │   └── Todo.kt               # Todo domain model
│   ├── repository/                # Repository contracts
│   │   └── TodoRepository.kt     # Repository interface
│   └── usecase/                   # Use cases
│       ├── GetTodosUseCase.kt    # Fetch all todos
│       ├── AddTodoUseCase.kt     # Add new todo
│       ├── UpdateTodoUseCase.kt  # Update todo
│       └── DeleteTodoUseCase.kt  # Delete todo
│
├── presentation/                  # Presentation Layer
│   ├── TodoScreen.kt             # Main screen composable
│   ├── TodoState.kt              # UI state and events
│   ├── TodoViewModel.kt          # ViewModel with StateFlow
│   └── components/               # UI components
│       ├── TodoItem.kt           # Todo list item
│       └── TodoDialog.kt         # Add/Edit dialog
│
├── di/                           # Dependency Injection
│   ├── DatabaseModule.kt         # Room database module
│   └── AppModule.kt              # App-level bindings
│
├── MainActivity.kt               # Entry point
├── TodoApplication.kt            # Application class
└── ui/theme/                     # Material3 theme
```

## 🔄 Data Flow

1. **User Action** → UI (Composable) triggers event
2. **ViewModel** → Receives event, calls appropriate use case
3. **Use Case** → Executes business logic, calls repository
4. **Repository** → Interacts with Room DAO
5. **DAO** → Returns Flow with data
6. **Flow** → Emits data changes to repository
7. **Repository** → Maps to domain models
8. **Use Case** → Returns domain data
9. **ViewModel** → Updates StateFlow
10. **UI** → Observes StateFlow, recomposes automatically

## 🎨 UI Screenshots

### Main Screen
- Lists all todos with checkbox, title, and description
- Floating Action Button to add new todos
- Empty state when no todos exist
- Loading indicator during initial load

### Add/Edit Dialog
- Title input field (required)
- Description input field (optional)
- Save and Cancel buttons

### Todo Item
- Checkbox to toggle completion
- Strikethrough text for completed todos
- Edit button (click on card)
- Delete button (trash icon)

## 🔧 Key Implementation Details

### Room Database
```kotlin
@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
```

### Reactive Data with Flow
```kotlin
@Query("SELECT * FROM todos ORDER BY createdAt DESC")
fun getAllTodos(): Flow<List<TodoEntity>>
```

### State Management
```kotlin
private val _uiState = MutableStateFlow(TodoUiState(isLoading = true))
val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()
```

### Dependency Injection
```kotlin
@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    // ... other use cases
) : ViewModel()
```

## ✅ Build Verification

The project has been verified to build successfully:

```bash
./gradlew clean build
```

**Result**: BUILD SUCCESSFUL ✅

**APK Location**: `app/build/outputs/apk/debug/app-debug.apk`

## 🧪 Testing

### Manual Testing
- Add multiple todos with different titles and descriptions
- Edit existing todos
- Toggle todo completion status
- Delete todos
- Verify data persists after app restart

### Future Testing Opportunities
- Unit tests for ViewModel
- Unit tests for Use Cases
- Instrumented tests for UI components
- Repository tests with test database

## 🚧 Future Enhancements

- [ ] Add categories/tags to todos
- [ ] Implement search functionality
- [ ] Add due dates and reminders
- [ ] Priority levels (High, Medium, Low)
- [ ] Swipe gestures for delete
- [ ] Dark mode support
- [ ] Backup & restore functionality
- [ ] Firebase sync for multi-device support
- [ ] Widget support

## 📝 Code Quality

- **Architecture**: Clean Architecture with SOLID principles
- **Separation of Concerns**: Clear layer boundaries
- **Reactive Programming**: Flow for data streams
- **Dependency Injection**: Hilt for loose coupling
- **Material Design**: Material3 components
- **Type Safety**: Strong Kotlin typing

## 🤝 Contributing

This is a learning project demonstrating Android development best practices.

## 📄 License

This project is created for educational purposes.

## 🙏 Acknowledgments

Built following Android best practices and Clean Architecture principles.

---

**Created with ❤️ using Kotlin, Jetpack Compose, and Clean Architecture**

