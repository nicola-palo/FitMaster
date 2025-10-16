# 🏋️ FitMaster - Complete Workout Tracking App# FitMaster - Android Workout App



> **Una completa app Android nativa per il fitness con tracking degli allenamenti, gestione esercizi personalizzati, piani workout su misura, timer background e monitoraggio progressi corporei con grafici.**Una completa app Android nativa per il fitness con tracking degli allenamenti, gestione esercizi e monitoraggio progressi.



[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)## 🏋️ Caratteristiche

[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)

[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-brightgreen.svg)](https://developer.android.com/jetpack/compose)### ✅ Implementate

[![Min SDK](https://img.shields.io/badge/Min%20SDK-26-orange.svg)](https://developer.android.com/about/versions/oreo)- **Home Dashboard**: Visualizzazione piano attivo e sessioni recenti

[![Target SDK](https://img.shields.io/badge/Target%20SDK-33-blue.svg)](https://developer.android.com/about/versions/13)- **Lista Esercizi**: Catalogo completo con filtri per gruppo muscolare e difficoltà

- **Dettagli Esercizio**: Descrizioni dettagliate, tips tecnici e immagini

---- **Creazione Piano Workout**: Creazione piani personalizzati per 7 giorni settimanali

- **Workout Attivo**: Timer workout in tempo reale con tracking serie e ripetizioni

## 📱 Screenshots & Features Overview- **Timer Riposo**: Timer countdown con opzioni preimpostate (30s, 60s, 90s)

- **Profilo Utente**: Gestione dati personali e misurazioni corporee

### 🏠 Home Dashboard- **Database Locale**: Room Database con tutte le entità necessarie

- **Piano Attivo**: Visualizzazione piano workout corrente con pulsanti Start/Stop- **Tracking Progressi**: Salvataggio misurazioni corpo nel tempo

- **Quick Actions**: Accesso rapido a Esercizi e Creazione Nuovi Piani

- **⭐ Piani Preferiti**: Fino a 3 piani marcati come favoriti (gestione dalla pagina Plans)### 📊 Architettura

- **📊 Recent Workouts**: Ultimi 5 workout completati con durata e nome giorno

- **Eliminazione Workout**: Possibilità di eliminare workout completati con conferma- **Pattern**: MVVM (Model-View-ViewModel)

- **UI**: Jetpack Compose

### 💪 Exercise Management- **Database**: Room (SQLite locale)

- **Lista Completa**: Catalogo esercizi predefiniti + esercizi custom- **Navigation**: Jetpack Navigation Component

- **Filtri Dinamici**: Filtraggio per gruppo muscolare e livello difficoltà (derivati dal database)- **Async**: Kotlin Coroutines + Flow

- **🔍 Ricerca**: Ricerca in tempo reale per nome esercizio- **Dependency Injection**: Manual DI con Application class

- **CRUD Completo**: 

  - ✏️ **Modifica**: Tutti gli esercizi (predefiniti e custom)### 📦 Struttura Database

  - 🗑️ **Elimina**: Tutti gli esercizi con conferma

  - ➕ **Aggiungi**: Nuovi esercizi personalizzati con immagine URL#### Entità Principali:

- **Dettagli Esercizio**: Descrizioni complete, muscoli target, difficoltà, tips tecnici- `Exercise`: Esercizi (predefiniti e custom)

- `WorkoutPlan`: Piani di allenamento

### 📅 Workout Plans- `WorkoutPlanDay`: Giorni del piano (Lunedì-Domenica)

- **Creazione Wizard**: Flow a step per creare piani personalizzati- `WorkoutPlanExercise`: Esercizi per ogni giorno

  - Step 1: Nome piano e selezione giorni settimanali- `WorkoutSession`: Sessioni di allenamento completate

  - Step 2: Selezione esercizi per ogni giorno- `WorkoutSessionExercise`: Dettagli esercizi in ogni sessione

  - Step 3: Configurazione serie, ripetizioni e **tempo di riposo personalizzato** per esercizio- `BodyMeasurement`: Misurazioni corporee (peso, circonferenze, etc)

- **⭐ Sistema Preferiti**: - `UserProfile`: Profilo utente

  - Marca fino a 3 piani come preferiti (stellina piena/vuota)

  - Gestione stelline SOLO dalla pagina Plans## 🚀 Compilazione e Installazione

  - Visualizzazione piani preferiti in Home (senza stelline)

- **Attivazione Piano**: Un piano attivo alla volta con pulsante Play### Prerequisiti

- **Eliminazione**: Rimozione piani con conferma (elimina anche giorni ed esercizi associati)- Android Studio Hedgehog (2023.1.1) o superiore

- JDK 17

### 🏃 Active Workout- Android SDK API 26+ (minSdk)

- **Selezione Giorno**: Dialog per scegliere quale giorno del piano eseguire- Android SDK API 34 (compileSdk)

- **Tracking Tempo Reale**: 

  - Visualizzazione esercizio corrente con immagine### Passi per Build

  - Contatore serie completate (es. "Serie 1/3")

  - Input ripetizioni effettive per ogni serie1. **Apri il progetto in Android Studio**

  - Timer riposo automatico tra le serie   ```

- **⏱️ Background Timer Service**:    File > Open > seleziona cartella 'android'

  - Timer funziona anche con app chiusa/schermo bloccato   ```

  - **Notifica Persistente** con countdown in tempo reale (aggiornamento ogni secondo)

  - Deep linking: tap sulla notifica per tornare al workout attivo2. **Sync Gradle**

  - Tipo servizio: `dataSync` con permessi FOREGROUND_SERVICE   - Android Studio sincronizzerà automaticamente le dipendenze

- **Pulsante Stop**: Possibilità di fermare il piano attivo dalla Home con conferma   - Aspetta il completamento del sync

- **Salvataggio Sessione**: Workout completati salvati con durata e dettagli esercizi

3. **Build il progetto**

### 👤 User Profile   ```

- **🎯 Obiettivi Personalizzati**:   Build > Make Project

  - Peso target (kg)   ```

  - % Grassa target

  - Salvataggio permanente nel profilo4. **Esegui su Emulatore o Device**

- **📊 Statistiche Correnti**:   ```

  - Peso attuale   Run > Run 'app'

  - % Grassa attuale   ```

  - Massa Magra (calcolata automaticamente)   - Seleziona un device/emulatore con API 26+

- **➕ Aggiungi Misurazione** (FAB Button):   - L'app si installerà e partirà automaticamente

  - Peso (obbligatorio)

  - % Grassa (opzionale)### Build da Terminale

  - **📅 Data Modificabile** con DatePicker Material3

  - Calcolo automatico: Massa Grassa e Massa Magra```powershell

- **📈 Storico Misurazioni**:cd android

  - Lista completa con data, peso, % grassa, massa magra./gradlew assembleDebug

  - Grafico progressi con trend (↗️ aumento / ↘️ diminuzione)```

  - Range peso min-max

  - Eliminazione misurazioni con confermaL'APK sarà in: `app/build/outputs/apk/debug/app-debug.apk`

- **Informazioni Personali**: Nome, età, sesso, obiettivo fitness

## 📱 Schermate

---

1. **Home** - Dashboard principale con quick actions

## 🎯 Funzionalità Complete2. **Exercise List** - Lista esercizi con ricerca e filtri

3. **Exercise Detail** - Dettagli completi esercizio

### ✅ Gestione Esercizi4. **Workout Plan Creation** - Creazione/modifica piano

- [x] Lista esercizi con ricerca e filtri dinamici5. **Active Workout** - Esecuzione workout con timer

- [x] CRUD completo per TUTTI gli esercizi (predefiniti + custom)6. **Rest Timer** - Timer riposo tra serie

- [x] Aggiunta campo immagine URL7. **User Profile** - Profilo e misurazioni

- [x] Dettagli esercizio completi con tips

- [x] Filtri derivati da database (muscle groups e difficulty)## 🎨 Design System



### ✅ Piani Workout- **Colore Primario**: #0D7FF2 (Blu)

- [x] Wizard creazione piano multi-step- **Font**: Lexend (fallback su Default)

- [x] Configurazione giorni settimanali- **Dark Mode**: ✅ Supportato

- [x] Assegnazione esercizi per giorno- **Material Design 3**: ✅ Implementato

- [x] **Timer riposo personalizzato** per ogni esercizio (default 60s)

- [x] Sistema preferiti (max 3 piani con stellina)## 📝 Dati Iniziali

- [x] Gestione stelline dalla pagina Plans

- [x] Visualizzazione preferiti in HomeL'app viene popolata automaticamente con 8 esercizi predefiniti al primo avvio:

- [x] Attivazione/Disattivazione piani- Push-ups

- Squats

### ✅ Workout Attivo- Lunges

- [x] Tracking serie e ripetizioni in tempo reale- Plank

- [x] Timer riposo automatico (configurabile per esercizio)- Pull-ups

- [x] **Background Foreground Service** con notifica persistente- Deadlift

- [x] Notifica aggiornata ogni secondo con countdown- Bench Press

- [x] Deep linking da notifica a workout attivo- Bicep Curls

- [x] Stop piano dalla Home con conferma

- [x] Salvataggio sessioni completate## 🔧 Dipendenze Principali

- [x] Fix crash quando si ferma il piano attivo

```kotlin

### ✅ Profilo & Misurazioni- Jetpack Compose BOM 2023.10.01

- [x] Obiettivi peso e % grassa- Room 2.6.1

- [x] Statistiche correnti (peso, % grassa, massa magra)- Navigation Compose 2.7.5

- [x] Aggiunta misurazioni con data modificabile (DatePicker)- Kotlin Coroutines 1.7.3

- [x] Calcolo automatico massa grassa/magra- Lifecycle ViewModel Compose 2.6.2

- [x] Storico misurazioni con grafici progressi- Coil (Image Loading) 2.5.0

- [x] Trend visuale (↗️↘️) e range peso- MPAndroidChart 3.1.0 (per grafici)

- [x] Eliminazione misurazioni```



### ✅ UI/UX## 📂 Struttura Progetto

- [x] Material Design 3

- [x] Dark Mode supportato```

- [x] Bottom Navigationapp/src/main/java/com/fitmaster/app/

- [x] FAB per azioni rapide├── data/

- [x] Dialog di conferma per eliminazioni│   ├── entity/          # Entità Room Database

- [x] Feedback visivi (saved messages, loading states)│   ├── dao/             # Data Access Objects

- [x] Icone intuitive per ogni azione│   ├── database/        # Database Room

│   ├── repository/      # Repository Pattern

---│   ├── model/           # UI Models

│   └── converter/       # Type Converters

## 🏗️ Architettura├── ui/

│   ├── screens/         # Schermate Compose

### Pattern & Principi│   ├── components/      # Componenti riutilizzabili

- **MVVM** (Model-View-ViewModel)│   ├── viewmodel/       # ViewModels

- **Repository Pattern** per astrazione data layer│   ├── navigation/      # Navigation setup

- **Single Source of Truth** con Room Database│   └── theme/           # Tema Material 3

- **Unidirectional Data Flow** con StateFlow├── MainActivity.kt      # Activity principale

- **Separation of Concerns**├── FitMasterApplication.kt  # Application class

└── SampleData.kt        # Dati iniziali

### Tech Stack```



#### Frontend## 🐛 Note di Sviluppo

- **Kotlin** 1.9.20

- **Jetpack Compose** (Material3)- **Room Database**: Inizializzato automaticamente al primo avvio

- **Compose BOM** 2023.06.01- **ViewModels**: Creati tramite Factory pattern

- **Navigation**: Setup completo con argomenti per navigation

#### Database- **State Management**: StateFlow per UI state

- **Room** 2.5.2- **Coroutines**: IO dispatcher per operazioni DB

- **SQLite** locale

- **Type Converters** per Date e custom types## 🔜 Possibili Estensioni Future



#### Navigation- [ ] Grafici progressi (già setup MPAndroidChart)

- **Jetpack Navigation Compose** 2.7.5- [ ] Export/Import piani workout

- **Deep linking** per notifiche- [ ] Notifiche programmate

- [ ] Widget home screen

#### Async- [ ] Backup cloud

- **Kotlin Coroutines** 1.7.3- [ ] Social sharing

- **Flow** & **StateFlow**- [ ] Video tutorial esercizi

- **ViewModelScope** per lifecycle-aware coroutines- [ ] Piano alimentazione



#### Background Processing## 📄 Licenza

- **Foreground Service** con notifiche persistenti

- **WorkoutTimerService** per timer backgroundProgetto sviluppato per scopi educativi.

- **Service Binding** per comunicazione app-service

## 👨‍💻 Tech Stack Summary

#### Image Loading

- **Coil** 2.5.0 per caricamento immagini asincrone**Frontend**: Kotlin + Jetpack Compose  

**Database**: Room (SQLite)  

#### Charts (Configurato)**Architecture**: MVVM  

- **MPAndroidChart** 3.1.0 per grafici futuri avanzati**Navigation**: Jetpack Navigation  

**Async**: Coroutines + Flow  

---

---

## 📊 Database Schema

**Versione**: 1.0  

### Entità (8 Tabelle)**Min SDK**: 26 (Android 8.0)  

**Target SDK**: 34 (Android 14)

#### 1️⃣ **Exercise**
```kotlin
- id: Long (PK)
- name: String
- description: String
- muscleGroup: String
- difficulty: String
- tips: String
- imageUrl: String?
- isCustom: Boolean
```

#### 2️⃣ **WorkoutPlan**
```kotlin
- id: Long (PK)
- name: String
- createdAt: Date
- isActive: Boolean
- isFavorite: Boolean  // ⭐ NEW: Sistema preferiti
```

#### 3️⃣ **WorkoutPlanDay**
```kotlin
- id: Long (PK)
- planId: Long (FK)
- dayOfWeek: Int (1-7)
- dayName: String
```

#### 4️⃣ **WorkoutPlanExercise**
```kotlin
- id: Long (PK)
- planDayId: Long (FK)
- exerciseId: Long (FK)
- sets: Int
- reps: Int
- restSeconds: Int  // ⏱️ Configurabile per esercizio
- orderIndex: Int
```

#### 5️⃣ **WorkoutSession**
```kotlin
- id: Long (PK)
- planId: Long (FK)
- planDayId: Long (FK)
- startTime: Date
- endTime: Date?
- durationSeconds: Long
- isCompleted: Boolean
```

#### 6️⃣ **WorkoutSessionExercise**
```kotlin
- id: Long (PK)
- sessionId: Long (FK)
- exerciseId: Long (FK)
- plannedSets: Int
- completedSets: Int
- plannedReps: Int
- actualReps: Int?
- restSeconds: Int
- completedAt: Date?
```

#### 7️⃣ **BodyMeasurement**
```kotlin
- id: Long (PK)
- date: Date
- weight: Float
- bodyFatPercentage: Float?
- leanMass: Float?  // 🆕 Calcolata automaticamente
- fatMass: Float?   // 🆕 Calcolata automaticamente
- height: Float?
- chest: Float?
- waist: Float?
- biceps: Float?
- thighs: Float?
- notes: String?
```

#### 8️⃣ **UserProfile**
```kotlin
- id: Long (PK, always 1)
- name: String
- username: String?
- age: Int
- sex: String
- goal: String
- targetWeight: Float?              // 🆕 Obiettivo peso
- targetBodyFatPercentage: Float?   // 🆕 Obiettivo % grassa
- profileImageUrl: String?
- createdAt: Date
- updatedAt: Date
```

**Database Version**: 3 (aggiornato con nuovi campi)

---

## 🚀 Setup & Installazione

### Prerequisiti
- **Android Studio** Hedgehog (2023.1.1) o superiore
- **JDK 17** (Eclipse Adoptium)
  - Path: `C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot`
- **Android SDK**:
  - Min SDK: 26 (Android 8.0 Oreo)
  - Target SDK: 33 (Android 13)
  - Compile SDK: 33

### Build Instructions

#### Da Android Studio

1. **Apri il progetto**
   ```
   File → Open → seleziona la cartella 'android'
   ```

2. **Configura JDK 17**
   ```
   File → Project Structure → SDK Location
   JDK location: C:\Program Files\Eclipse Adoptium\jdk-17.0.16.8-hotspot
   ```

3. **Sync Gradle**
   - Android Studio sincronizzerà automaticamente
   - Attendi completamento (può richiedere qualche minuto al primo avvio)

4. **Build & Run**
   ```
   Build → Make Project
   Run → Run 'app'
   ```
   - Seleziona emulatore (API 26+) o device fisico
   - L'app si installerà e avvierà automaticamente

#### Da Terminale (Windows PowerShell)

```powershell
# Naviga alla cartella del progetto
cd C:\Users\nicol\Desktop\Wa\sport-app\android

# Build debug APK
./gradlew assembleDebug

# Output APK
# app/build/outputs/apk/debug/app-debug.apk
```

#### Installazione APK su Device

```powershell
# Via ADB
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Primo Avvio

Al primo avvio, l'app:
1. ✅ Inizializza il database Room
2. ✅ Popola con 8 esercizi predefiniti
3. ✅ Crea struttura tabelle (versione 3)
4. ✅ È pronta all'uso!

---

## 📁 Struttura Progetto

```
android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fitmaster/app/
│   │   │   │   ├── data/
│   │   │   │   │   ├── entity/           # 8 Entità Room
│   │   │   │   │   ├── dao/              # 4 DAOs
│   │   │   │   │   ├── database/         # Room Database
│   │   │   │   │   ├── repository/       # 4 Repositories
│   │   │   │   │   ├── model/            # UI Models
│   │   │   │   │   └── converter/        # Type Converters
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/          # 9 Schermate Compose
│   │   │   │   │   ├── components/       # Componenti Riutilizzabili
│   │   │   │   │   ├── viewmodel/        # 6 ViewModels
│   │   │   │   │   ├── navigation/       # Navigation Setup
│   │   │   │   │   └── theme/            # Material 3 Theme
│   │   │   │   ├── service/              # Background Services
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── FitMasterApplication.kt
│   │   │   │   └── SampleData.kt
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── build.gradle.kts
└── README.md
```

---

## 🔧 Dipendenze Principali

### Core Android
```kotlin
androidx.core:core-ktx:1.12.0
androidx.lifecycle:lifecycle-runtime-ktx:2.6.2
androidx.activity:activity-compose:1.8.1
```

### Jetpack Compose
```kotlin
androidx.compose:compose-bom:2023.06.01
androidx.compose.material3:material3
androidx.compose.material:material-icons-extended
```

### Navigation
```kotlin
androidx.navigation:navigation-compose:2.7.5
```

### Room Database
```kotlin
androidx.room:room-runtime:2.5.2
androidx.room:room-ktx:2.5.2
```

### Coroutines
```kotlin
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
```

### Image Loading
```kotlin
io.coil-kt:coil-compose:2.5.0
```

---

## 🎨 Design System

### Colori
- **Primary**: `#0D7FF2` (Blu vibrante)
- **Secondary**: `#03DAC6` (Teal)
- **Error**: `#F44336` (Rosso)
- **Surface**: Dinamico (Light/Dark mode)

### Typography
- **Font Family**: Lexend (fallback su Sans-Serif)
- **Stili**: Material 3 Type Scale completo

### Icons
- **Material Icons Extended**
- Icone custom per azioni specifiche:
  - ⭐ Star / StarOutline per preferiti
  - 🏋️ FitnessCenter per logo app
  - 📅 CalendarToday per date picker

---

## 📝 Dati Iniziali

### Esercizi Predefiniti (8)

1. **Push-ups** - Chest, Intermediate
2. **Squats** - Legs, Beginner
3. **Lunges** - Legs, Intermediate
4. **Plank** - Core, Beginner
5. **Pull-ups** - Back, Advanced
6. **Deadlift** - Legs & Back, Advanced
7. **Bench Press** - Chest, Intermediate
8. **Bicep Curls** - Arms, Beginner

---

## 🔐 Permessi Android

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_DATA_SYNC" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.VIBRATE" />
```

---

## 📄 Changelog

### Version 1.3 (Current - 16 Oct 2025)
- ✅ DatePicker modificabile per misurazioni corporee
- ✅ Calcolo automatico massa grassa/magra
- ✅ Grafici progressi peso con trend

### Version 1.2 (15 Oct 2025)
- ✅ Sistema preferiti workout plans (max 3)
- ✅ Gestione stelline dalla pagina Plans
- ✅ Profilo completo con obiettivi peso/% grassa
- ✅ Storico misurazioni con eliminazione

### Version 1.1 (14 Oct 2025)
- ✅ Timer riposo personalizzato per esercizio
- ✅ Background Foreground Service per timer
- ✅ Notifica persistente con countdown
- ✅ Stop piano dalla Home
- ✅ Fix crash piano attivo

### Version 1.0 (13 Oct 2025)
- ✅ Release iniziale
- ✅ CRUD esercizi completo
- ✅ Wizard creazione piani
- ✅ Tracking workout real-time
- ✅ Database Room completo

---

## 👨‍💻 Tech Stack Summary

| Layer | Technology |
|-------|-----------|
| **Language** | Kotlin 1.9.20 |
| **UI Framework** | Jetpack Compose (Material3) |
| **Architecture** | MVVM + Repository Pattern |
| **Database** | Room (SQLite) |
| **Async** | Coroutines + Flow |
| **Navigation** | Jetpack Navigation Compose |
| **Image Loading** | Coil |
| **Background** | Foreground Service + Notifications |
| **Min SDK** | 26 (Android 8.0 Oreo) |
| **Target SDK** | 33 (Android 13) |

---

## 🌟 Highlights

### Why This App Stands Out

1. **🏗️ Production-Ready Architecture**: MVVM, Repository Pattern, Clean Code
2. **💪 Complete Feature Set**: Dalla creazione esercizi al tracking avanzato
3. **⚡ Modern Tech Stack**: 100% Jetpack Compose, Kotlin Coroutines, Room
4. **🎨 Beautiful UI**: Material Design 3 con Dark Mode
5. **📱 Background Processing**: Timer funziona anche con app chiusa
6. **📊 Data Persistence**: Database completo con relazioni complesse
7. **🔧 Configurability**: Timer personalizzabili, obiettivi custom, piani flessibili
8. **📈 Progress Tracking**: Grafici e statistiche per monitorare miglioramenti

---

**Made with ❤️ using Kotlin & Jetpack Compose**

**Version 1.3** | **Last Updated**: 16 October 2025

---

**Happy Coding! 💪🏋️‍♂️**
