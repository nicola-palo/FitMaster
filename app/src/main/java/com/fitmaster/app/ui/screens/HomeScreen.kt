package com.fitmaster.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitmaster.app.service.WorkoutServiceManager
import com.fitmaster.app.ui.components.BottomNavigationBar
import com.fitmaster.app.ui.navigation.Screen
import com.fitmaster.app.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val context = LocalContext.current
    val serviceManager = remember { WorkoutServiceManager(context) }
    
    val activePlan by viewModel.activePlan.collectAsState()
    val recentSessions by viewModel.recentSessions.collectAsState()
    val favoritePlans by viewModel.favoritePlans.collectAsState()
    val planDays by viewModel.planDays.collectAsState()
    
    var showDaySelectionDialog by remember { mutableStateOf(false) }
    var showDeactivateConfirmDialog by remember { mutableStateOf(false) }
    
    // Load days when active plan changes
    LaunchedEffect(activePlan) {
        activePlan?.let { plan ->
            viewModel.loadPlanDays(plan.id)
        }
    }
    
    if (showDaySelectionDialog && activePlan != null) {
        DaySelectionDialog(
            days = planDays,
            onDismiss = { showDaySelectionDialog = false },
            onDaySelected = { dayId ->
                navController.navigate(Screen.ActiveWorkout.createRoute(activePlan!!.id, dayId))
                showDaySelectionDialog = false
            }
        )
    }
    
    if (showDeactivateConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeactivateConfirmDialog = false },
            title = { Text("Stop Active Plan") },
            text = { Text("Are you sure you want to deactivate \"${activePlan?.name}\"? This will stop any ongoing workout notifications.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeactivateConfirmDialog = false
                        try {
                            serviceManager.stopTimer()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        viewModel.deactivatePlan()
                    }
                ) {
                    Text("Stop", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeactivateConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FitMaster", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, "home")
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Welcome Back!",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Active Plan Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Active Plan",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            activePlan?.name ?: "No active plan",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        if (activePlan != null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = { showDaySelectionDialog = true },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Start")
                                }
                                OutlinedButton(
                                    onClick = { 
                                        showDeactivateConfirmDialog = true
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    )
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Stop")
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedButton(
                                onClick = { navController.navigate(Screen.WorkoutPlanCreation.route) }
                            ) {
                                Text("Create Workout Plan")
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.Add, contentDescription = null)
                            }
                        }
                    }
                }
            }
            
            // Quick Actions
            item {
                Text(
                    "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.List,
                        title = "Exercises",
                        onClick = { navController.navigate(Screen.ExerciseList.route) }
                    )
                    QuickActionCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Add,
                        title = "New Plan",
                        onClick = { navController.navigate(Screen.WorkoutPlanCreation.route) }
                    )
                }
            }
            
            // Favorite Workout Plans
            if (favoritePlans.isNotEmpty()) {
                item {
                    Text(
                        "Favorite Plans",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(favoritePlans.take(3)) { plan ->
                    var showDeleteDialog by remember { mutableStateOf(false) }
                    
                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Delete Plan") },
                            text = { Text("Are you sure you want to delete '${plan.name}'? This action cannot be undone.") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        viewModel.deletePlan(plan)
                                        showDeleteDialog = false
                                    }
                                ) {
                                    Text("Delete", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }
                    
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { 
                            navController.navigate(Screen.WorkoutPlanEdit.createRoute(plan.id))
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    plan.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    if (plan.isActive) "Active Plan" else "Inactive",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (plan.isActive) 
                                        MaterialTheme.colorScheme.primary 
                                    else 
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (!plan.isActive) {
                                    IconButton(
                                        onClick = { viewModel.setActivePlan(plan.id) }
                                    ) {
                                        Icon(
                                            Icons.Default.PlayArrow,
                                            contentDescription = "Activate Plan",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(
                                        onClick = { showDeleteDialog = true }
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete Plan",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Recent Workouts
            if (recentSessions.isNotEmpty()) {
                item {
                    Text(
                        "Recent Workouts",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(recentSessions.take(5)) { session ->
                    var showDeleteDialog by remember { mutableStateOf(false) }
                    var dayName by remember { mutableStateOf<String?>(null) }
                    
                    // Load day name
                    LaunchedEffect(session.planDayId) {
                        viewModel.getPlanDay(session.planDayId).collect { day ->
                            dayName = day?.dayName
                        }
                    }
                    
                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Delete Workout Session") },
                            text = { Text("Are you sure you want to delete this workout session? This action cannot be undone.") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        viewModel.deleteSession(session)
                                        showDeleteDialog = false
                                    }
                                ) {
                                    Text("Delete", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }
                    
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    dayName ?: "Workout Session",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "${session.durationSeconds / 60} minutes",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                IconButton(
                                    onClick = { showDeleteDialog = true }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete Session",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DaySelectionDialog(
    days: List<com.fitmaster.app.data.entity.WorkoutPlanDay>,
    onDismiss: () -> Unit,
    onDaySelected: (Long) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Workout Day") },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(days) { day ->
                    TextButton(
                        onClick = { onDaySelected(day.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                day.dayName,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                    Divider()
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
