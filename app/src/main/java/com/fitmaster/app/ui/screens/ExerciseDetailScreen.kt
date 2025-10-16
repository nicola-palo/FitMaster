package com.fitmaster.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fitmaster.app.ui.components.TopAppBarWithBack
import com.fitmaster.app.ui.viewmodel.ExerciseDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    navController: NavController,
    viewModel: ExerciseDetailViewModel
) {
    val exercise by viewModel.exercise.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = "Exercise Details",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        exercise?.let { ex ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    ex.name,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
                
                // Tags
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = { },
                        label = { Text(ex.category) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                    AssistChip(
                        onClick = { },
                        label = { Text(ex.difficulty) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
                
                // Image/Video
                ex.imageUrl?.let { url ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = url,
                            contentDescription = ex.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        // Play button overlay
                        FilledIconButton(
                            onClick = { /* Play video */ },
                            modifier = Modifier.size(64.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                            )
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                
                // Description
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Description",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            ex.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Technical Tips
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Technical Tips",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ex.technicalTips.split("\n").forEach { tip ->
                            if (tip.isNotBlank()) {
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Text("• ", style = MaterialTheme.typography.bodyLarge)
                                    Text(
                                        tip.trim(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
