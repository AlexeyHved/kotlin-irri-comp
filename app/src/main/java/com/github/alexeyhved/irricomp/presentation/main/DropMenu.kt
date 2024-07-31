package com.github.alexeyhved.irricomp.presentation.main

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropMenu(
    tempValue: String = "",
    options: List<String>,
    label: String,
    onChangeValue: (String) -> Unit
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var _tempValue by rememberSaveable {
        mutableStateOf(tempValue)
    }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }) {
        OutlinedTextField(
            modifier = Modifier.width(150.dp)
                .menuAnchor()
                .widthIn(1.dp),
            label = { Text(text = label) },
            value = _tempValue,
            onValueChange = { },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
        )
        ExposedDropdownMenu(
            modifier = Modifier.widthIn(1.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.widthIn(1.dp),
                    text = { Text(text = option) },
                    onClick = {
                        _tempValue = option
                        isExpanded = false
                        onChangeValue(_tempValue)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropMenu() {
    DropMenu(options = listOf("25", "32", "40"), label = "Диаметр", onChangeValue = {})
}