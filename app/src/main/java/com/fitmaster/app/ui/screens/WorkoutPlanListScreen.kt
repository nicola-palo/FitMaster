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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitmaster.app.data.entity.WorkoutPlan
import com.fitmaster.app.ui.components.BottomNavigationBar
import com.fitmaster.app.ui.navigation.Screen
import com.fitmaster.app.ui.viewmodel.WorkoutPlanListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanListScreen(
    navController: NavController,
    viewModel: WorkoutPlanListViewModel
) {
    val allPlans by viewModel.allPlans.collectAsState()
    val activePlan by viewModel.activePlan.collectAsState()
    
    var showDeleteDialog by remember { mutableStateOf<WorkoutPlan?>(null) }
    var showMaxFavoritesDialog by remember { mutableStateOf(false) }
    
    showDeleteDialog?.let { plan ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Plan") },
            text = { Text("Are you sure you want to delete '${plan.name}'? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deletePlan(plan)
                        showDeleteDialog = null
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    if (showMaxFavoritesDialog) {
        AlertDialog(
            onDismissRequest = { showMaxFavoritesDialog = false },
            title = { Text("Maximum Favorites Reached") },
            text = { Text("You can only have up to 3 favorite workout plans. Please remove one to add another.") },
            confirmButton = {
                TextButton(onClick = { showMaxFavoritesDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Plans", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.WorkoutPlanCreation.route) }) {
                        Icon(Icons.Default.Add, contentDescription = "Create Plan")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, "workout_plan_list")
        }
    ) { padding ->
        if (allPlans.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.FitnessCenter,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No Workout Plans Yet",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Tap + to create your first plan",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate(Screen.WorkoutPlanCreation.route) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Create Plan")
                    }
                }
            }
        } else {
            // Plans List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "All Plans (${allPlans.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(allPlans) { plan ->
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
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (plan.isActive) {
                                        Surface(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = MaterialTheme.shapes.small
                                        ) {
                                            Text(
                                                "ACTIVE",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    } else {
                                        Text(
                                            "Inactive",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { 
                                        viewModel.toggleFavorite(plan) {
                                            showMaxFavoritesDialog = true
                                        }
                                    }
                                ) {
                                    Icon(
                                        if (plan.isFavorite) Icons.Default.Star else Icons.Outlined.StarOutline,
                                        contentDescription = "Toggle Favorite",
                                        tint = if (plan.isFavorite) 
                                            MaterialTheme.colorScheme.primary 
                                        else 
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (!plan.isActive) {
                                    IconButton(
                                        onClick = { 
                                            viewModel.setActivePlan(plan.id)
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.PlayArrow,
                                            contentDescription = "Set as active",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(
                                        onClick = { showDeleteDialog = plan }
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete plan",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = "Edit plan",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
