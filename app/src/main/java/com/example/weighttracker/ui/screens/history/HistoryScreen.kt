package com.example.weighttracker.ui.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weighttracker.data.local.WeightEntry
import com.example.weighttracker.ui.components.DefaultButton
import com.example.weighttracker.ui.components.ItemRow
import com.example.weighttracker.ui.components.TextFieldComposable
import com.example.weighttracker.util.toFormattedDate
import com.example.weighttracker.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(navController: NavController, viewModel: HistoryViewModel = hiltViewModel()) {
    val weightEntries by viewModel.weightEntries.collectAsState(initial = emptyList())
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedEntry by remember { mutableStateOf<WeightEntry?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "History",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(weightEntries) { entry ->
                ItemRow(
                    title = "${entry.weight} kg",
                    subtitle = entry.date.toFormattedDate(),
                    mood = entry.mood,
                    onDelete = { viewModel.deleteWeightEntry(entry) },
                    onEdit = {
                        selectedEntry = entry
                        showEditDialog = true
                    }
                )
            }
        }

        if (showEditDialog && selectedEntry != null) {
            EditEntryDialog(
                entry = selectedEntry!!,
                onDismiss = { showEditDialog = false },
                onConfirm = { updatedEntry ->
                    viewModel.updateWeightEntry(updatedEntry)
                    showEditDialog = false
                    selectedEntry = null
                }
            )
        }
    }
}

@Composable
fun EditEntryDialog(entry: WeightEntry, onDismiss: () -> Unit, onConfirm: (WeightEntry) -> Unit) {
    var weight by remember { mutableStateOf(entry.weight.toString()) }
    var mood by remember { mutableStateOf(entry.mood) }

    Surface {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Edit Entry", style = MaterialTheme.typography.titleMedium)
            TextFieldComposable(value = weight, onValueChange = { weight = it }, label = "Weight")
            TextFieldComposable(value = mood, onValueChange = { mood = it }, label = "Mood")
            DefaultButton(text = "Update", onClick = {
                onConfirm(entry.copy(weight = weight.toDoubleOrNull() ?: entry.weight, mood = mood))
            })
        }
    }
}