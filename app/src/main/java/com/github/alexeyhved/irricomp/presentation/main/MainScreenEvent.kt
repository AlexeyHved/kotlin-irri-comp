package com.github.alexeyhved.irricomp.presentation.main

import com.github.alexeyhved.irricomp.data.NozzleSector
import com.github.alexeyhved.irricomp.data.NozzleTitle

sealed class MainScreenEvent {
    data object AddZone : MainScreenEvent()
    data class AddNozzleBtn(val zone: Int) : MainScreenEvent()
    data class RefreshBtn(val zone: Int) : MainScreenEvent()
    data class ComputeBtn(val zone: Int) : MainScreenEvent()
    data class DeleteZoneBtn(val zone: Int) : MainScreenEvent()
    data class ValveSwitch(val zone: Int) : MainScreenEvent()
    data class ChangeLocalLoses(val zone: Int) : MainScreenEvent()
    data class ChangeMainLength(val zone: Int, val data: String) : MainScreenEvent()
    data class ChangeMainInnerDiameter(val zone: Int, val data: String) : MainScreenEvent()
    data class ChangeLocalLength(val zone: Int, val data: String) : MainScreenEvent()
    data class ChangeLocalInnerDiameter(val zone: Int, val data: String) : MainScreenEvent()
    data class ChangeWaterFlow(val zone: Int, val data: String) : MainScreenEvent()
    data class ChangeValve(val zone: Int, val data: String) : MainScreenEvent()
    data class ChangeNozzleTitle(val zone: Int, val nozzleId: Int, val title: NozzleTitle) : MainScreenEvent()
    data class ChangeNozzleSector(val zone: Int, val nozzleId: Int, val sector: NozzleSector) : MainScreenEvent()
    data class PlusNozzleMultiply(val zone: Int, val nozzleId: Int, val multiply: Int) : MainScreenEvent()
    data class MinusNozzleMultiply(val zone: Int, val nozzleId: Int, val multiply: Int) : MainScreenEvent()
}
