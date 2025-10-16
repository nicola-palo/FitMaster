package com.fitmaster.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitmaster.app.data.entity.BodyMeasurement
import com.fitmaster.app.ui.components.BottomNavigationBar
import com.fitmaster.app.ui.components.TopAppBarWithBack
import com.fitmaster.app.ui.viewmodel.UserProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val latestMeasurement by viewModel.latestMeasurement.collectAsState()
    val allMeasurements by viewModel.allMeasurements.collectAsState()
    
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("Male") }
    var goal by remember { mutableStateOf("Muscle Gain") }
    
    // Goals
    var targetWeight by remember { mutableStateOf("") }
    var targetBodyFat by remember { mutableStateOf("") }
    
    // Show dialogs
    var showAddMeasurementDialog by remember { mutableStateOf(false) }
    var showSavedMessage by remember { mutableStateOf(false) }
    var measurementToDelete by remember { mutableStateOf<BodyMeasurement?>(null) }
    
    // Show saved confirmation
    LaunchedEffect(showSavedMessage) {
        if (showSavedMessage) {
            kotlinx.coroutines.delay(2000)
            showSavedMessage = false
        }
    }
    
    // Update fields when profile data loads
    LaunchedEffect(userProfile) {
        userProfile?.let { profile ->
            name = profile.name
            age = profile.age.toString()
            sex = profile.sex
            goal = profile.goal
            targetWeight = profile.targetWeight?.toString() ?: ""
            targetBodyFat = profile.targetBodyFatPercentage?.toString() ?: ""
        }
    }
    
    if (showAddMeasurementDialog) {
        AddMeasurementDialog(
            onDismiss = { showAddMeasurementDialog = false },
            onAdd = { weight, bodyFat, date ->
                viewModel.addMeasurement(
                    weight = weight,
                    bodyFatPercentage = bodyFat,
                    date = date
                )
                showAddMeasurementDialog = false
            }
        )
    }
    
    measurementToDelete?.let { measurement ->
        AlertDialog(
            onDismissRequest = { measurementToDelete = null },
            title = { Text("Delete Measurement") },
            text = { Text("Are you sure you want to delete this measurement?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteMeasurement(measurement)
                        measurementToDelete = null
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { measurementToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = "Profile",
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, "user_profile")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddMeasurementDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Measurement")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = RoundedCornerShape(50.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Icon(
                            Icons.Default.FitnessCenter,
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "FitMaster",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        name.ifEmpty { "Your Name" },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            // Current Stats Card
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Current Stats",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        latestMeasurement?.let { m ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                StatItem("Weight", "${m.weight} kg")
                                m.bodyFatPercentage?.let {
                                    StatItem("Body Fat", "$it%")
                                }
                                m.leanMass?.let {
                                    StatItem("Lean Mass", "${String.format("%.1f", it)} kg")
                                }
                            }
                        } ?: run {
                            Text(
                                "No measurements yet. Add your first measurement!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Goals Section
            item {
                Text(
                    "Your Goals",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = targetWeight,
                                onValueChange = { targetWeight = it },
                                label = { Text("Target Weight (kg)") },
                                leadingIcon = { Icon(Icons.Default.FitnessCenter, null) },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = targetBodyFat,
                                onValueChange = { targetBodyFat = it },
                                label = { Text("Target Body Fat (%)") },
                                leadingIcon = { Icon(Icons.Default.TrendingDown, null) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            
            // Personal Information
            item {
                Text(
                    "Personal Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Name") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = age,
                                onValueChange = { age = it },
                                label = { Text("Age") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        // Sex Selection
                        Text("Sex", style = MaterialTheme.typography.bodyMedium)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = sex == "Male",
                                onClick = { sex = "Male" },
                                label = { Text("Male") },
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                selected = sex == "Female",
                                onClick = { sex = "Female" },
                                label = { Text("Female") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        // Goal Selection
                        Text("Goal", style = MaterialTheme.typography.bodyMedium)
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Muscle Gain", "Fat Loss", "Maintenance").forEach { g ->
                                FilterChip(
                                    selected = goal == g,
                                    onClick = { goal = g },
                                    label = { Text(g) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
            
            // Save Button
            item {
                Button(
                    onClick = {
                        viewModel.updateProfile(
                            name = name,
                            age = age.toIntOrNull() ?: 0,
                            sex = sex,
                            goal = goal,
                            targetWeight = targetWeight.toFloatOrNull(),
                            targetBodyFatPercentage = targetBodyFat.toFloatOrNull()
                        )
                        showSavedMessage = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Profile", style = MaterialTheme.typography.titleMedium)
                }
            }
            
            if (showSavedMessage) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Profile saved successfully!",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
            
            // Measurement History
            if (allMeasurements.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Measurement History",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${allMeasurements.size} entries",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Simple Chart
                item {
                    MeasurementChart(measurements = allMeasurements)
                }
                
                items(allMeasurements.reversed()) { measurement ->
                    MeasurementCard(
                        measurement = measurement,
                        onDelete = { measurementToDelete = measurement }
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MeasurementCard(
    measurement: BodyMeasurement,
    onDelete: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    dateFormat.format(measurement.date),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        Text(
                            "${measurement.weight} kg",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Weight",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    measurement.bodyFatPercentage?.let { bf ->
                        Column {
                            Text(
                                "$bf%",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Body Fat",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    measurement.leanMass?.let { lm ->
                        Column {
                            Text(
                                "${String.format("%.1f", lm)} kg",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Lean Mass",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun MeasurementChart(measurements: List<BodyMeasurement>) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Weight Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            // Simple line chart visualization
            if (measurements.isNotEmpty()) {
                val maxWeight = measurements.maxOf { it.weight }
                val minWeight = measurements.minOf { it.weight }
                val range = maxWeight - minWeight
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    // Y-axis labels
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${String.format("%.1f", maxWeight)} kg",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            "${String.format("%.1f", minWeight)} kg",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    
                    // Simple text representation
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 48.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "📈 Tracking ${measurements.size} measurements",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "Range: ${String.format("%.1f", minWeight)} - ${String.format("%.1f", maxWeight)} kg",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (measurements.size >= 2) {
                            val diff = measurements.last().weight - measurements.first().weight
                            val trend = if (diff > 0) "↗️ +${String.format("%.1f", diff)}" 
                                       else "↘️ ${String.format("%.1f", diff)}"
                            Text(
                                "Trend: $trend kg",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (diff > 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMeasurementDialog(
    onDismiss: () -> Unit,
    onAdd: (Float, Float?, Date) -> Unit
) {
    var weight by remember { mutableStateOf("") }
    var bodyFat by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Date()) }
    var showDatePicker by remember { mutableStateOf(false) }
    
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    val calendar = remember { Calendar.getInstance() }
    
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.time
        )
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = Date(millis)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Measurement") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    leadingIcon = { Icon(Icons.Default.FitnessCenter, null) },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = bodyFat,
                    onValueChange = { bodyFat = it },
                    label = { Text("Body Fat % (optional)") },
                    leadingIcon = { Icon(Icons.Default.TrendingDown, null) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Date selector
                OutlinedCard(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    "Date",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    dateFormat.format(selectedDate),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Change date",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    weight.toFloatOrNull()?.let { w ->
                        onAdd(w, bodyFat.toFloatOrNull(), selectedDate)
                    }
                },
                enabled = weight.toFloatOrNull() != null
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
