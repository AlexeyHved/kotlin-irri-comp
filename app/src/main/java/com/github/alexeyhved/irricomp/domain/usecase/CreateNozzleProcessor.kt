package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.data.NozzleTitle
import com.github.alexeyhved.irricomp.domain.Nozzle
import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import okhttp3.internal.format
import javax.inject.Inject

class CreateNozzleProcessor @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(
        mainScreenState: MainScreenState,
        name: NozzleTitle = NozzleTitle.MP2000
    ): MainScreenState {
        val flow = repo.findNozzleFlowByName(name)
        val nozzleMutableList = mainScreenState.nozzles.toMutableList().apply {
            add(Nozzle.Adjustable(id = repo.getNextNozzleId(), title = name, flow = flow))
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
            nozzles = nozzleMutableList, waterFlow = format("%.2f", curWaterFlow)
        )
    }
}