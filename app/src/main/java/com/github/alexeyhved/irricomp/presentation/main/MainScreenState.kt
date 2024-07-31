package com.github.alexeyhved.irricomp.presentation.main

import com.github.alexeyhved.irricomp.domain.DomainData
import com.github.alexeyhved.irricomp.domain.Nozzle
import com.github.alexeyhved.irricomp.domain.Pipe
import com.github.alexeyhved.irricomp.domain.Valve

data class MainScreenState(
    val zone: Int = 1,
    val mainPipe: PresentPipe = PresentPipe(),
    val localPipe: PresentPipe = PresentPipe(),
    val waterFlow: String = "", //m3/h
    val waterFlowError: String = "",
    val isLocalLoses: Boolean = true,
    val computeBtnEnabled: Boolean = true,
    val valve: Valve? = null,
    val valvesNames: List<String> = listOf("PGV-101", "PGV-151", "PGV-201"),
    val isValveEnabled: Boolean = false,
    val nozzles: List<Nozzle> = listOf(),
    val msg: Msg = Msg(),
)

fun MainScreenState.convertToDomainData(): DomainData {
    var valve: Valve? = null
    if (this.valve != null) {
        valve = Valve(
            name = this.valve.name,
            consumptionVsLose = this.valve.consumptionVsLose
                .map { Pair(it.first, it.second / 0.0980665) }
                .toList()
        )
    }

    var localPipe: Pipe? = null
    if (this.localPipe.length != "" || this.localPipe.innerDiameter != "") {
        localPipe = Pipe(
            innerDiameter = this.localPipe.innerDiameter.toDouble() / 1000.0,
            length = this.localPipe.length.toDouble()
        )
    }

    return DomainData(
        mainPipe = Pipe(
            innerDiameter = this.mainPipe.innerDiameter.toDouble() / 1000.0,
            length = this.mainPipe.length.toDouble()
        ),
        localPipe = localPipe,
        waterFlow = this.waterFlow.toDouble() / 3600,
        valve = valve
    )
}