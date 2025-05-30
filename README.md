# Compose REST API Playground

A demonstration of modern Android development practices showcasing efficient REST API integration
and concurrent data handling using Jetpack Compose.

## 🚀 Features

### 1. Search API Integration

![Search Use Case Demo](search_usecase.gif)

- Real-time search with debouncing
- Query highlighting in search results
- Loading state handling
- Error handling
- Clean architecture implementation

### 2. Concurrent API Calls

![Concurrent API Calls Demo](async_requests_usecase.gif)

- Multiple simultaneous API requests
- Parallel data processing
- Single recomposition for all results
- Real-time UI updates

## 🛠️ Technologies

- **Jetpack Compose** - Modern UI toolkit
- **Ktor** - HTTP client for REST API calls
- **Kotlin Coroutines** - Asynchronous programming with Flow
- **Hilt** - Dependency injection
- **Material 3** - Material Design components

## 📱 Implementation Details

### Search Feature

- Debounced search input (prevents excessive API calls)
- Query highlighting in results
- State management using StateFlow
- Error handling with empty results
- Clean architecture with Use Cases

### Concurrent API Feature

- Multiple API endpoints integration
- Parallel data fetching
- Single UI update for all results
- Error handling for individual requests

## 🧪 Testing

Comprehensive test coverage including:

- Unit tests for Use Cases
- Repository tests
- ViewModel tests with Turbine
- Coroutines testing
- Error handling scenarios