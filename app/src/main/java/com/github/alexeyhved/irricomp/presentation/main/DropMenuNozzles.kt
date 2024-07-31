package com.github.alexeyhved.irricomp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.alexeyhved.irricomp.R
import com.github.alexeyhved.irricomp.data.NozzleSector
import com.github.alexeyhved.irricomp.data.NozzleTitle
import com.github.alexeyhved.irricomp.domain.Nozzle
import com.github.alexeyhved.irricomp.ui.theme.IrriCompTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropMenuNozzles(
    nozzle: Nozzle,
    multiply: Int,
    onChangeTitle: (Int, NozzleTitle) -> Unit,
    onChangeSector: (Int, NozzleSector) -> Unit,
    onPlusMultiply: (Int, Int) -> Unit,
    onMinusMultiply: (Int, Int) -> Unit,
) {
    var titleIsExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var sectorIsExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdownMenuBox(
            expanded = titleIsExpanded,
            onExpandedChange = { titleIsExpanded = it }) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .widthIn(1.dp),
                label = { Text(text = "Nozzle") },
                value = nozzle.title.name,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sectorIsExpanded) }
            )
            ExposedDropdownMenu(
                modifier = Modifier.widthIn(1.dp),
                expanded = titleIsExpanded,
                onDismissRequest = { titleIsExpanded = false }) {
                NozzleTitle.entries.forEach { title ->
                    DropdownMenuItem(
                        modifier = Modifier.widthIn(1.dp),
                        text = { Text(text = title.name) },
                        onClick = {
                            titleIsExpanded = false
                            onChangeTitle(nozzle.id, title)
                        }
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = { onMinusMultiply(nozzle.id, multiply) }) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter =  painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = null
                )
            }
            Text(text = "x$multiply", fontSize = 16.sp, textAlign = TextAlign.Center)
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = { onPlusMultiply(nozzle.id, multiply) }) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter =  painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
        if (nozzle is Nozzle.Adjustable) {
            ExposedDropdownMenuBox(
                expanded = sectorIsExpanded,
                onExpandedChange = { sectorIsExpanded = it }) {
                OutlinedTextField(
                    modifier = Modifier
                        .width(97.dp)
                        .menuAnchor()
                        .widthIn(1.dp),
                    label = { Text(text = "Sector") },
                    value = nozzle.sector.name,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sectorIsExpanded) }
                )
                ExposedDropdownMenu(
                    modifier = Modifier.widthIn(1.dp),
                    expanded = sectorIsExpanded,
                    onDismissRequest = { sectorIsExpanded = false }) {
                    NozzleSector.entries.forEach { sector ->
                        DropdownMenuItem(
                            modifier = Modifier.widthIn(1.dp),
                            text = { Text(text = sector.name) },
                            onClick = {
                                sectorIsExpanded = false
                                onChangeSector(nozzle.id, sector)
                            }
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewDropMenuNozzles() {
    IrriCompTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            DropMenuNozzles(
                nozzle = (Nozzle.Adjustable(id = 0, title = NozzleTitle.MP1000, flow = 0.0)),
                multiply = 3,
                onChangeTitle = { id, t -> {} },
                onChangeSector = { id, s -> {} },
                onMinusMultiply = {id, mult -> {}},
                onPlusMultiply = {id, mult -> {}}
            )
        }

    }

}