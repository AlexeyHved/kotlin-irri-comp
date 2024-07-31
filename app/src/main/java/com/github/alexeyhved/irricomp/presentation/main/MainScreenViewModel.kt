package com.github.alexeyhved.irricomp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alexeyhved.irricomp.domain.usecase.AddZoneProcessor
import com.github.alexeyhved.irricomp.domain.usecase.ChangeNozzleSectorProcessor
import com.github.alexeyhved.irricomp.domain.usecase.ChangeNozzleTitleProcessor
import com.github.alexeyhved.irricomp.domain.usecase.ChangeValveProcessor
import com.github.alexeyhved.irricomp.domain.usecase.ComputeProcessor
import com.github.alexeyhved.irricomp.domain.usecase.CreateNozzleProcessor
import com.github.alexeyhved.irricomp.domain.usecase.InitLoadData
import com.github.alexeyhved.irricomp.domain.usecase.LocalInnerDiameterValidationProcessor
import com.github.alexeyhved.irricomp.domain.usecase.LocalPipeLengthValidationProcessor
import com.github.alexeyhved.irricomp.domain.usecase.MainInnerDiameterValidationProcessor
import com.github.alexeyhved.irricomp.domain.usecase.MainPipeLengthValidationProcessor
import com.github.alexeyhved.irricomp.domain.usecase.MinusNozzleMultiplyProcessor
import com.github.alexeyhved.irricomp.domain.usecase.PlusNozzleMultiplyProcessor
import com.github.alexeyhved.irricomp.domain.usecase.RefreshBtnProcessor
import com.github.alexeyhved.irricomp.domain.usecase.SwitchValveProcessor
import com.github.alexeyhved.irricomp.domain.usecase.WaterFlowValidationProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val initLoadData: InitLoadData,
    private val computeProcessor: ComputeProcessor,
    private val changeValveProcessor: ChangeValveProcessor,
    private val refreshBtnProcessor: RefreshBtnProcessor,
    private val addZoneProcessor: AddZoneProcessor,
    private val mainPipeLengthValidationProcessor: MainPipeLengthValidationProcessor,
    private val mainInnerDiameterValidationProcessor: MainInnerDiameterValidationProcessor,
    private val waterFlowValidationProcessor: WaterFlowValidationProcessor,
    private val switchValveProcessor: SwitchValveProcessor,
    private val createNozzleProcessor: CreateNozzleProcessor,
    private val changeNozzleTitleProcessor: ChangeNozzleTitleProcessor,
    private val changeNozzleSectorProcessor: ChangeNozzleSectorProcessor,
    private val plusNozzleMultiplyProcessor: PlusNozzleMultiplyProcessor,
    private val minusNozzleMultiplyProcessor: MinusNozzleMultiplyProcessor,
    private val localPipeLengthValidationProcessor: LocalPipeLengthValidationProcessor,
    private val localInnerDiameterValidationProcessor: LocalInnerDiameterValidationProcessor
) : ViewModel() {
    private val _mainScreenState = MutableStateFlow(listOf(MainScreenState()))
    val mainScreenState = _mainScreenState.asStateFlow()

    init {
        _mainScreenState.value = initLoadData(mainScreenState.value)
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.ChangeMainLength -> {
                viewModelScope.launch(Dispatchers.Default) { changeMainLength(event) }
            }

            is MainScreenEvent.ChangeLocalLoses -> {
                viewModelScope.launch(Dispatchers.Default) { changeLocalLoses(event) }
            }

            is MainScreenEvent.RefreshBtn -> {
                 viewModelScope.launch(Dispatchers.Default) { refreshBtn(event) }
            }

            is MainScreenEvent.AddZone -> {
                viewModelScope.launch(Dispatchers.Default) { addZone() }
            }

            is MainScreenEvent.ChangeValve -> {
                viewModelScope.launch(Dispatchers.Default) { changeValve(event) }
            }

            is MainScreenEvent.ComputeBtn -> {
                viewModelScope.launch(Dispatchers.Default) { computeBtn(event) }
            }

            is MainScreenEvent.ChangeWaterFlow -> {
                viewModelScope.launch(Dispatchers.Default) { changeWaterFlow(event) }
            }

            is MainScreenEvent.ChangeMainInnerDiameter -> {
                viewModelScope.launch(Dispatchers.Default) { changeMainInnerDiameter(event) }
            }

            is MainScreenEvent.ValveSwitch -> {
                viewModelScope.launch(Dispatchers.Default) { valveSwitch(event) }
            }

            is MainScreenEvent.AddNozzleBtn -> {
                viewModelScope.launch(Dispatchers.Default) { addNozzleBtn(event) }
            }

            is MainScreenEvent.ChangeNozzleSector -> {
                viewModelScope.launch(Dispatchers.Default) { changeNozzleSector(event) }
            }

            is MainScreenEvent.ChangeNozzleTitle -> {
                viewModelScope.launch(Dispatchers.Default) { changeNozzleTitle(event) }
            }

            is MainScreenEvent.MinusNozzleMultiply -> {
                viewModelScope.launch(Dispatchers.Default) { minusNozzleMultiply(event) }
            }

            is MainScreenEvent.PlusNozzleMultiply -> {
                viewModelScope.launch(Dispatchers.Default) { plusNozzleMultiply(event) }
            }

            is MainScreenEvent.DeleteZoneBtn -> {
                viewModelScope.launch(Dispatchers.Default) { deleteZoneBtn(event) }
            }

            is MainScreenEvent.ChangeLocalInnerDiameter -> {
                viewModelScope.launch(Dispatchers.Default) { changeLocalInnerDiameter(event) }
            }

            is MainScreenEvent.ChangeLocalLength -> {
                viewModelScope.launch(Dispatchers.Default) { changeLocalLength(event) }
            }
        }
    }

    private suspend fun changeLocalLength(event: MainScreenEvent.ChangeLocalLength) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    localPipeLengthValidationProcessor(event.data, it)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun changeLocalInnerDiameter(event: MainScreenEvent.ChangeLocalInnerDiameter) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    localInnerDiameterValidationProcessor(event.data, it)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun deleteZoneBtn(event: MainScreenEvent.DeleteZoneBtn) {
        if (mainScreenState.value.size == 1) return

        _mainScreenState.value = _mainScreenState.value.asFlow()
            .filter { it.zone != event.zone }
            .toList()
    }

    private suspend fun plusNozzleMultiply(event: MainScreenEvent.PlusNozzleMultiply) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    plusNozzleMultiplyProcessor(
                        mainScreenState = it,
                        nozzleId = event.nozzleId,
                        multiply = event.multiply
                    ).let { state ->
                        waterFlowValidationProcessor(
                            value = state.waterFlow,
                            mainScreenState = state
                        )
                    }

                } else {
                    it
                }
            }.toList()
    }

    private suspend fun minusNozzleMultiply(event: MainScreenEvent.MinusNozzleMultiply) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    val newScreenState = minusNozzleMultiplyProcessor(
                        mainScreenState = it,
                        nozzleId = event.nozzleId,
                        multiply = event.multiply
                    )
                    waterFlowValidationProcessor(
                        value = newScreenState.waterFlow,
                        mainScreenState = newScreenState
                    )
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun changeNozzleTitle(event: MainScreenEvent.ChangeNozzleTitle) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    changeNozzleTitleProcessor(
                        mainScreenState = it, nozzleId = event.nozzleId, title = event.title
                    )
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun changeNozzleSector(event: MainScreenEvent.ChangeNozzleSector) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    changeNozzleSectorProcessor(
                        mainScreenState = it,
                        nozzleId = event.nozzleId,
                        sector = event.sector
                    )
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun addNozzleBtn(event: MainScreenEvent.AddNozzleBtn) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    createNozzleProcessor(it).let { state ->
                        waterFlowValidationProcessor(state.waterFlow, state)
                    }
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun valveSwitch(event: MainScreenEvent.ValveSwitch) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    switchValveProcessor(it)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun changeMainInnerDiameter(event: MainScreenEvent.ChangeMainInnerDiameter) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    mainInnerDiameterValidationProcessor(event.data, it)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun changeWaterFlow(event: MainScreenEvent.ChangeWaterFlow) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    waterFlowValidationProcessor(event.data, it)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun computeBtn(event: MainScreenEvent.ComputeBtn) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    computeProcessor(it)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun changeValve(event: MainScreenEvent.ChangeValve) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    changeValveProcessor(mainScreenState = it, valveName = event.data)
                } else {
                    it
                }
            }.toList()
    }

    private fun addZone() {
        val screenStateMutableList = _mainScreenState.value.toMutableList().apply {
            add(addZoneProcessor())
        }
        _mainScreenState.value = screenStateMutableList
    }

    private suspend fun changeMainLength(event: MainScreenEvent.ChangeMainLength) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    mainPipeLengthValidationProcessor(event.data, it)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun changeLocalLoses(event: MainScreenEvent.ChangeLocalLoses) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    it.copy(isLocalLoses = !it.isLocalLoses)
                } else {
                    it
                }
            }.toList()
    }

    private suspend fun refreshBtn(event: MainScreenEvent.RefreshBtn) {
        _mainScreenState.value = _mainScreenState.value.asFlow()
            .map {
                if (it.zone == event.zone) {
                    refreshBtnProcessor(event.zone)
                } else {
                    it
                }
            }.toList()
    }
}