package com.example.weighttracker.ui.screens.dashboard

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weighttracker.R
import com.example.weighttracker.data.local.WeightEntry
import com.example.weighttracker.data.preferences.UserPreferences
import com.example.weighttracker.model.MotivationalMessages
import com.example.weighttracker.util.calculateProgress
import com.example.weighttracker.util.calculateStreak
import com.example.weighttracker.util.getFormattedDate
import com.example.weighttracker.util.toFormattedString
import com.example.weighttracker.viewmodel.DashboardViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel) {
    val context = LocalContext.current
    val weightEntries by viewModel.weightEntries.observeAsState(emptyList())
    val userPreferences by viewModel.userPreferences.observeAsState(UserPreferences())

    LaunchedEffect(Unit) {
        viewModel.loadWeightEntries()
        viewModel.loadUserPreferences()
    }

    DashboardContent(
        weightEntries = weightEntries,
        userPreferences = userPreferences,
        context = context
    )
}

@Composable
fun DashboardContent(
    weightEntries: List<WeightEntry>,
    userPreferences: UserPreferences,
    context: Context
) {
    val progress = calculateProgress(
        currentWeight = weightEntries.lastOrNull()?.weight ?: userPreferences.currentWeight,
        initialWeight = userPreferences.currentWeight,
        goalWeight = userPreferences.goalWeight,
        goal = userPreferences.goal
    )
    val streak = calculateStreak(weightEntries)

    val unit = userPreferences.unit

    val chartData = weightEntries.map {
        Entry(it.date.toFloat(), it.weight.toFloat())
    }

    val motivationalMessage = rememberMotivationalMessage(userPreferences.goal, progress)

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.dashboard_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (chartData.isNotEmpty()) {
            WeightTrendGraph(chartData, unit)
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Text(
                text = stringResource(R.string.no_data_available),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Text(
            text = stringResource(R.string.progress_towards_goal, progress, userPreferences.goal),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.current_streak, streak),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Text(
                text = motivationalMessage,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun WeightTrendGraph(chartData: List<Entry>, unit: String) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            LineChart(it).apply {
                description.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter =
                    com.github.mikephil.charting.formatter.IndexAxisValueFormatter(
                        chartData.map { entry ->
                            getFormattedDate(entry.x.toLong())
                        }
                    )
                xAxis.labelRotationAngle = -45f
                axisRight.isEnabled = false
                legend.isEnabled = false
                extraBottomOffset = 30f
            }
        },
        update = { chart ->
            val dataSet = LineDataSet(chartData, "Weight Trend").apply {
                color = MaterialTheme.colorScheme.primary.toArgb()
                valueTextColor = MaterialTheme.colorScheme.onSurface.toArgb()
                valueTextSize = 12f
                mode = LineDataSet.Mode.LINEAR
                setDrawFilled(true)
                fillColor = MaterialTheme.colorScheme.primary.toArgb()
                fillAlpha = 50
                circleRadius = 5f
                setCircleColor(MaterialTheme.colorScheme.primary.toArgb())
            }

            chart.data = LineData(dataSet)
            chart.animateX(300)
            chart.invalidate()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun rememberMotivationalMessage(goal: String, progress: Int): String {
    val messages = MotivationalMessages.getMessagesForGoal(goal)
    val index = (progress % messages.size)
    return messages[index]
}