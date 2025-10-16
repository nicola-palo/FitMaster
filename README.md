# FitMaster - Android Workout App

Una completa app Android nativa per il fitness con tracking degli allenamenti, gestione esercizi e monitoraggio progressi.

## 🏋️ Caratteristiche

### ✅ Implementate
- **Home Dashboard**: Visualizzazione piano attivo e sessioni recenti
- **Lista Esercizi**: Catalogo completo con filtri per gruppo muscolare e difficoltà
- **Dettagli Esercizio**: Descrizioni dettagliate, tips tecnici e immagini
- **Creazione Piano Workout**: Creazione piani personalizzati per 7 giorni settimanali
- **Workout Attivo**: Timer workout in tempo reale con tracking serie e ripetizioni
- **Timer Riposo**: Timer countdown con opzioni preimpostate (30s, 60s, 90s)
- **Profilo Utente**: Gestione dati personali e misurazioni corporee
- **Database Locale**: Room Database con tutte le entità necessarie
- **Tracking Progressi**: Salvataggio misurazioni corpo nel tempo

### 📊 Architettura

- **Pattern**: MVVM (Model-View-ViewModel)
- **UI**: Jetpack Compose
- **Database**: Room (SQLite locale)
- **Navigation**: Jetpack Navigation Component
- **Async**: Kotlin Coroutines + Flow
- **Dependency Injection**: Manual DI con Application class

### 📦 Struttura Database

#### Entità Principali:
- `Exercise`: Esercizi (predefiniti e custom)
- `WorkoutPlan`: Piani di allenamento
- `WorkoutPlanDay`: Giorni del piano (Lunedì-Domenica)
- `WorkoutPlanExercise`: Esercizi per ogni giorno
- `WorkoutSession`: Sessioni di allenamento completate
- `WorkoutSessionExercise`: Dettagli esercizi in ogni sessione
- `BodyMeasurement`: Misurazioni corporee (peso, circonferenze, etc)
- `UserProfile`: Profilo utente

## 🚀 Compilazione e Installazione

### Prerequisiti
- Android Studio Hedgehog (2023.1.1) o superiore
- JDK 17
- Android SDK API 26+ (minSdk)
- Android SDK API 34 (compileSdk)

### Passi per Build

1. **Apri il progetto in Android Studio**
   ```
   File > Open > seleziona cartella 'android'
   ```

2. **Sync Gradle**
   - Android Studio sincronizzerà automaticamente le dipendenze
   - Aspetta il completamento del sync

3. **Build il progetto**
   ```
   Build > Make Project
   ```

4. **Esegui su Emulatore o Device**
   ```
   Run > Run 'app'
   ```
   - Seleziona un device/emulatore con API 26+
   - L'app si installerà e partirà automaticamente

### Build da Terminale

```powershell
cd android
./gradlew assembleDebug
```

L'APK sarà in: `app/build/outputs/apk/debug/app-debug.apk`

## 📱 Schermate

1. **Home** - Dashboard principale con quick actions
2. **Exercise List** - Lista esercizi con ricerca e filtri
3. **Exercise Detail** - Dettagli completi esercizio
4. **Workout Plan Creation** - Creazione/modifica piano
5. **Active Workout** - Esecuzione workout con timer
6. **Rest Timer** - Timer riposo tra serie
7. **User Profile** - Profilo e misurazioni

## 🎨 Design System

- **Colore Primario**: #0D7FF2 (Blu)
- **Font**: Lexend (fallback su Default)
- **Dark Mode**: ✅ Supportato
- **Material Design 3**: ✅ Implementato

## 📝 Dati Iniziali

L'app viene popolata automaticamente con 8 esercizi predefiniti al primo avvio:
- Push-ups
- Squats
- Lunges
- Plank
- Pull-ups
- Deadlift
- Bench Press
- Bicep Curls

## 🔧 Dipendenze Principali

```kotlin
- Jetpack Compose BOM 2023.10.01
- Room 2.6.1
- Navigation Compose 2.7.5
- Kotlin Coroutines 1.7.3
- Lifecycle ViewModel Compose 2.6.2
- Coil (Image Loading) 2.5.0
- MPAndroidChart 3.1.0 (per grafici)
```

## 📂 Struttura Progetto

```
app/src/main/java/com/fitmaster/app/
├── data/
│   ├── entity/          # Entità Room Database
│   ├── dao/             # Data Access Objects
│   ├── database/        # Database Room
│   ├── repository/      # Repository Pattern
│   ├── model/           # UI Models
│   └── converter/       # Type Converters
├── ui/
│   ├── screens/         # Schermate Compose
│   ├── components/      # Componenti riutilizzabili
│   ├── viewmodel/       # ViewModels
│   ├── navigation/      # Navigation setup
│   └── theme/           # Tema Material 3
├── MainActivity.kt      # Activity principale
├── FitMasterApplication.kt  # Application class
└── SampleData.kt        # Dati iniziali
```

## 🐛 Note di Sviluppo

- **Room Database**: Inizializzato automaticamente al primo avvio
- **ViewModels**: Creati tramite Factory pattern
- **Navigation**: Setup completo con argomenti per navigation
- **State Management**: StateFlow per UI state
- **Coroutines**: IO dispatcher per operazioni DB

## 🔜 Possibili Estensioni Future

- [ ] Grafici progressi (già setup MPAndroidChart)
- [ ] Export/Import piani workout
- [ ] Notifiche programmate
- [ ] Widget home screen
- [ ] Backup cloud
- [ ] Social sharing
- [ ] Video tutorial esercizi
- [ ] Piano alimentazione

## 📄 Licenza

Progetto sviluppato per scopi educativi.

## 👨‍💻 Tech Stack Summary

**Frontend**: Kotlin + Jetpack Compose  
**Database**: Room (SQLite)  
**Architecture**: MVVM  
**Navigation**: Jetpack Navigation  
**Async**: Coroutines + Flow  

---

**Versione**: 1.0  
**Min SDK**: 26 (Android 8.0)  
**Target SDK**: 34 (Android 14)
