package com.github.alexeyhved.irricomp.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.alexeyhved.irricomp.R
import com.github.alexeyhved.irricomp.data.NozzleSector
import com.github.alexeyhved.irricomp.data.NozzleTitle
import com.github.alexeyhved.irricomp.domain.Nozzle
import com.github.alexeyhved.irricomp.domain.Valve
import com.github.alexeyhved.irricomp.ui.theme.IrriCompTheme

@Composable
fun MainScreen(
    states: List<MainScreenState>,
    paddingValues: PaddingValues,
    onEvent: (MainScreenEvent) -> Unit
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.back2),
        contentScale = ContentScale.Crop,
        alpha = 0.2f,
        contentDescription = null
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.Transparent),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.94f),
            contentPadding = PaddingValues(
                start = 5.dp,
                end = 5.dp,
                top = 5.dp,
                bottom = 5.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = states, key = { it.zone }) { state ->
                Card(
                    border = BorderStroke(width = 1.dp, color = Color.DarkGray.copy(alpha = 0.5f)),
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Column {
                                Text(
                                    text = "Zone ${state.zone}",
                                    fontFamily = FontFamily.SansSerif,
                                    //fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "Main pipeline",
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            }
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                IconButton(onClick = {
                                    onEvent(MainScreenEvent.DeleteZoneBtn(zone = state.zone))
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = null,
                                        tint = Color.DarkGray.copy(alpha = 0.5f)
                                    )
                                }
                            }

                        }

                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.widthIn(1.dp),
                            singleLine = true,
                            label = { Text(text = "Pipe length") },
                            suffix = { Text(text = "m") },
                            isError = state.mainPipe.lengthError != "",
                            supportingText = {
                                Text(text = state.mainPipe.lengthError, textAlign = TextAlign.End)
                            },
                            value = state.mainPipe.length,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = {
                                onEvent(MainScreenEvent.ChangeMainLength(state.zone, it))
                            }
                        )
                        OutlinedTextField(
                            modifier = Modifier.widthIn(1.dp),
                            singleLine = true,
                            label = { Text(text = "Inner diameter") },
                            suffix = { Text(text = "mm") },
                            isError = state.mainPipe.innerDiameterError != "",
                            supportingText = {
                                Text(
                                    text = state.mainPipe.innerDiameterError,
                                    textAlign = TextAlign.End
                                )
                            },
                            value = state.mainPipe.innerDiameter,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = {
                                onEvent(MainScreenEvent.ChangeMainInnerDiameter(state.zone, it))
                            })
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.widthIn(1.dp),
                            singleLine = true,
                            readOnly = state.nozzles.isNotEmpty(),
                            label = { Text(text = "Water flow") },
                            suffix = { Text(text = "m3/h") },
                            isError = state.waterFlowError != "",
                            supportingText = {
                                Text(text = state.waterFlowError, textAlign = TextAlign.End)
                            },
                            value = state.waterFlow,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = {
                                onEvent(MainScreenEvent.ChangeWaterFlow(state.zone, it))
                            })
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Local loses",
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Switch(
                                checked = state.isLocalLoses,
                                onCheckedChange = {
                                    onEvent(MainScreenEvent.ChangeLocalLoses(state.zone))
                                })
                        }

                    }
                    if (state.msg.mainFlowSpeed.isNotEmpty() && state.msg.mainPressureLoss.isNotEmpty()) {
                        Text(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            text = state.msg.mainFlowSpeed
                        )
                        Text(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            text = state.msg.mainPressureLoss
                        )
                    }
                    if (state.valve != null || state.nozzles.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Local pipeline",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.valve != null) {
                            DropMenu(
                                tempValue = state.valvesNames[0],
                                options = state.valvesNames,
                                label = "Valve",
                                onChangeValue = {
                                    onEvent(MainScreenEvent.ChangeValve(state.zone, it))
                                }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Valve",
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Switch(
                                checked = state.isValveEnabled,
                                onCheckedChange = {
                                    onEvent(MainScreenEvent.ValveSwitch(state.zone))
                                })
                        }

                    }
                    if (state.msg.valveLoss.isNotEmpty()) {
                        Text(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            text = state.msg.valveLoss
                        )
                    }
                    if (state.valve != null && state.nozzles.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.widthIn(1.dp),
                                singleLine = true,
                                label = { Text(text = "Pipe length") },
                                suffix = { Text(text = "m") },
                                isError = state.localPipe.lengthError != "",
                                supportingText = {
                                    Text(
                                        text = state.localPipe.lengthError,
                                        textAlign = TextAlign.End
                                    )
                                },
                                value = state.localPipe.length,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                onValueChange = {
                                    onEvent(MainScreenEvent.ChangeLocalLength(state.zone, it))
                                }
                            )
                            OutlinedTextField(
                                modifier = Modifier.widthIn(1.dp),
                                singleLine = true,
                                label = { Text(text = "Inner diameter") },
                                suffix = { Text(text = "mm") },
                                isError = state.localPipe.innerDiameterError != "",
                                supportingText = {
                                    Text(
                                        text = state.localPipe.innerDiameterError,
                                        textAlign = TextAlign.End
                                    )
                                },
                                value = state.localPipe.innerDiameter,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                onValueChange = {
                                    onEvent(
                                        MainScreenEvent.ChangeLocalInnerDiameter(
                                            state.zone,
                                            it
                                        )
                                    )
                                })
                        }
                    }
                    if (state.msg.localFlowSpeed.isNotEmpty() && state.msg.localPressureLoss.isNotEmpty()) {
                        Column {
                            Text(
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp,
                                text = state.msg.localFlowSpeed
                            )
                            Text(
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp,
                                text = state.msg.localPressureLoss
                            )
                        }
                    }
                    if (state.nozzles.isNotEmpty()) {
                        state.nozzles.forEach { nozzle: Nozzle ->
                            DropMenuNozzles(
                                nozzle = nozzle,
                                multiply = nozzle.multiply,
                                onChangeTitle = { nozzleId: Int, title: NozzleTitle ->
                                    onEvent(
                                        MainScreenEvent.ChangeNozzleTitle(
                                            zone = state.zone,
                                            nozzleId = nozzleId,
                                            title = title
                                        )
                                    )
                                },
                                onChangeSector = { nozzleId: Int, sector: NozzleSector ->
                                    onEvent(
                                        MainScreenEvent.ChangeNozzleSector(
                                            zone = state.zone,
                                            nozzleId = nozzleId,
                                            sector = sector
                                        )
                                    )
                                },
                                onPlusMultiply = { nozzleId: Int, multiply: Int ->
                                    onEvent(
                                        MainScreenEvent.PlusNozzleMultiply(
                                            zone = state.zone,
                                            nozzleId = nozzleId,
                                            multiply = multiply
                                        )
                                    )
                                },
                                onMinusMultiply = { nozzleId: Int, multiply: Int ->
                                    onEvent(
                                        MainScreenEvent.MinusNozzleMultiply(
                                            zone = state.zone,
                                            nozzleId = nozzleId,
                                            multiply = multiply
                                        )
                                    )
                                }
                            )
                        }
                    }
                    if (state.msg.isNotEmpty()) {
                        Column {
                            Text(
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 16.sp,
                                text = state.msg.totalLoss
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        ElevatedButton(
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            onClick = {
                                onEvent(MainScreenEvent.RefreshBtn(state.zone))
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = null
                            )
                        }

                        ElevatedButton(
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ), onClick = {
                                onEvent(MainScreenEvent.AddNozzleBtn(state.zone))
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                            Text(text = "Nozzle")
                        }

                        ElevatedButton(
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            enabled = state.mainPipe.lengthError == ""
                                    && state.mainPipe.length.isNotEmpty()
                                    && state.mainPipe.innerDiameterError == ""
                                    && state.mainPipe.innerDiameter.isNotEmpty()
                                    && state.waterFlowError == ""
                                    && state.waterFlow.isNotEmpty(),
                            onClick = {
                                onEvent(MainScreenEvent.ComputeBtn(state.zone))
                            }) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
        /*BottomAppBar(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ) {*/
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    onEvent(MainScreenEvent.AddZone)
                }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                Text(text = "Add zone")
            }
        }

        //}

    }
}


@Preview
@Composable
fun PreviewMain() {
    IrriCompTheme(
        darkTheme = false
    ) {

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(
                paddingValues = innerPadding,
                states = listOf(
                    MainScreenState(
                        valvesNames = listOf("PGV-101", "PGV-151", "PGV-201"),
                        nozzles = listOf(
                            Nozzle.Adjustable(
                                id = 1, title = NozzleTitle.MP1000, flow = 0.18
                            )
                        ),
                        valve = Valve(
                            name = "PGV-101",
                            consumptionVsLose = listOf(
                                // m3/h, bar
                                Pair(0.3, 0.08),
                                Pair(1.0, 0.11),
                                Pair(2.5, 0.13),
                                Pair(3.5, 0.16),
                                Pair(4.5, 0.23),
                                Pair(5.5, 0.43),
                                Pair(6.5, 0.62),
                                Pair(8.0, 1.10),
                                Pair(9.0, 1.48),

                                )
                        ),
                        msg = Msg(
                            mainFlowSpeed = "main flow speeed",
                            mainPressureLoss = "main pressure loss",
                            valveLoss = "valve loss",
                            localFlowSpeed = "local flow speed",
                            localPressureLoss = "local pressure loss",
                            totalLoss = "total loss"
                        )
                    ),
                    MainScreenState(
                        zone = 2,
                        nozzles = listOf(
                            Nozzle.Adjustable(
                                id = 1, title = NozzleTitle.MP1000, flow = 0.18
                            )
                        ),
                        valve = Valve(
                            name = "PGV-101",
                            consumptionVsLose = listOf(
                                // m3/h, bar
                                Pair(0.3, 0.08),
                                Pair(1.0, 0.11),
                                Pair(2.5, 0.13),
                                Pair(3.5, 0.16),
                                Pair(4.5, 0.23),
                                Pair(5.5, 0.43),
                                Pair(6.5, 0.62),
                                Pair(8.0, 1.10),
                                Pair(9.0, 1.48),

                                )
                        )
                    )
                ),
                onEvent = {},
            )
        }
    }
}