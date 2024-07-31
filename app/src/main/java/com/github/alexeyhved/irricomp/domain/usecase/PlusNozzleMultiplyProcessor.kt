package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.domain.Nozzle
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import okhttp3.internal.format
import javax.inject.Inject

class PlusNozzleMultiplyProcessor @Inject constructor() {
    operator fun invoke(
        mainScreenState: MainScreenState,
        nozzleId: Int,
        multiply: Int
    ): MainScreenState {
        val nozzleMutableList = mainScreenState.nozzles.toMutableList().apply {
            replaceAll {
                if (it.id == nozzleId) {
                    when (it) {
                        is Nozzle.Adjustable -> {
                            it.copy(multiply = multiply + 1)
                        }

                        is Nozzle.Fixed -> {
                            it.copy(multiply = multiply + 1)
                        }
                    }
                } else {
                    it
                }
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