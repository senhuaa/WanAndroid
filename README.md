# 🌟 WanAndroid App  

A **WanAndroid client** built with **Jetpack Compose** + **Hilt** + **Retrofit** + **Room**.  

## 📌 Tech Stack  

### **🔧 Architecture**  
- **MVVM** (Model-View-ViewModel)  

### **🚀 Jetpack Components**  
- **Jetpack Compose** - UI framework  
- **Navigation Compose** - Navigation handling  
- **Lifecycle** - Lifecycle management  
- **Room** - Local database  
- **Hilt** - Dependency injection  

### **📡 Networking**  
- **Retrofit** - HTTP client  
- **Gson Converter** - JSON parsing  
- **Logging Interceptor** - Network logging  

### **📦 Others**  
- **Material Icons** - Icons for UI  

## 📂 Project Structure  
```
📦 WanAndroid
 ┣ 📂 app
 ┃ ┣ 📂 data        # Data layer (Repository, API, Database)
 ┃ ┣ 📂 domain      # Business logic (UseCase, Model)
 ┃ ┣ 📂 ui          # UI layer (Compose UI, ViewModel)
 ┃ ┗ 📂 di          # Dependency Injection (Hilt)
 ┣ 📜 build.gradle
 ┗ 📜 README.md
```

## 🚀 Installation & Running  

### 1️⃣ **Clone the repository**
```sh
git clone https://github.com/your-username/WanAndroid.git
cd WanAndroid
```

### 2️⃣ **Sync dependencies**
```sh
./gradlew clean build
```

### 3️⃣ **Run the project**
```sh
./gradlew assembleDebug
```
Or click **Run** in **Android Studio**.

## 📡 API Configuration  

This project uses the **WanAndroid API**, no additional setup required.  
- [https://wanandroid.com/](https://wanandroid.com/)  

## 🏗️ Architecture  

### **📌 Data Layer**  
- **Retrofit + Room** for network requests and local storage  
- **Repository** handles data retrieval logic  

### **📌 Domain Layer**  
- **UseCase** for business logic  
- **Model** handles data transformation (API -> UI)  

### **📌 UI Layer**  
- **Jetpack Compose** for UI  
- **ViewModel** provides UI state  
- **StateFlow** for state management

## 🛠️ Features  

- ✅ **Article List** - Fetch and display articles from WanAndroid  
- ✅ **Article Favorites** - Save/remove articles from favorites  
- ✅ **Offline Caching** - Store articles using Room  
- ✅ **Dark Mode Support**  
