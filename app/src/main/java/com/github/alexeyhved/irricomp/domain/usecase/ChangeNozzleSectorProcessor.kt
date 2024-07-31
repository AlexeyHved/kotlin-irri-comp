package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.data.NozzleSector
import com.github.alexeyhved.irricomp.domain.Nozzle
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import okhttp3.internal.format
import javax.inject.Inject

class ChangeNozzleSectorProcessor @Inject constructor() {
    operator fun invoke(mainScreenState: MainScreenState, nozzleId: Int, sector: NozzleSector): MainScreenState {
        val newNozzle = mainScreenState.nozzles.asSequence()
            .filter { it.id == nozzleId }
            .map {
                when (it) {
                    is Nozzle.Adjustable -> {
                        it.copy(
                            sector = sector
                        )
                    }
                    is Nozzle.Fixed -> { it}
                }
            }
            .first()

        val nozzleMutableList = mainScreenState.nozzles.toMutableList()

        nozzleMutableList.forEachIndexed { index, nozzle ->
            if (nozzle.id == nozzleId) {
                nozzleMutableList[index] = newNozzle
            }
        }

        val curWaterFlow = nozzleMutableList.sumOf {
            when (it) {
                is Nozzle.Adjustable -> {
                    (it.flow / 180) * it.sector.value * it.multiply
                }

                is Nozzle.Fixed -> {
                    it.flow * it.multiply
                }
            }
        }
        return mainScreenState.copy(
            nozzles = nozzleMutableList,
            waterFlow = format("%.2f", curWaterFlow)
        )
    }

}