package com.example.weighttracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weighttracker.ui.theme.AppTheme
import com.example.weighttracker.ui.theme.darkBackground
import com.example.weighttracker.ui.theme.darkOnBackground

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary)
    ) {
        Text(text = text, color = AppTheme.colorScheme.onPrimary)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    Column {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .clip(RectangleShape),
            singleLine = singleLine,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = AppTheme.colorScheme.background,
                textColor = AppTheme.colorScheme.onBackground,
                focusedIndicatorColor = AppTheme.colorScheme.primary,
                unfocusedIndicatorColor = AppTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun ItemRow(
    date: String,
    weight: String,
    mood: String?,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Date: $date", style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = "Weight: $weight kg")
                if (!mood.isNullOrEmpty()) {
                    Text(text = "Mood: $mood")
                }
            }
            Column {
                DefaultButton(text = "Delete", onClick = onDelete, isEnabled = true)
                DefaultButton(text = "Edit", onClick = onEdit, isEnabled = true)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultButtonPreview() {
    AppTheme {
        DefaultButton(text = "Click Me", onClick = { /*TODO*/ })
    }
}

@Preview(showBackground = true)
@Composable
fun StyledTextFieldPreview() {
    AppTheme {
        StyledTextField(
            value = "Sample Text",
            onValueChange = {},
            label = "Label"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowPreview() {
    AppTheme {
        ItemRow(
            date = "2024-07-27",
            weight = "75.5",
            mood = "Happy",
            onDelete = {},
            onEdit = {}
        )
    }
}