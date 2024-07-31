package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.data.NozzleTitle
import com.github.alexeyhved.irricomp.domain.Nozzle
import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import okhttp3.internal.format
import javax.inject.Inject

class ChangeNozzleTitleProcessor @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(
        mainScreenState: MainScreenState,
        nozzleId: Int,
        title: NozzleTitle
    ): MainScreenState {
        val flow = repo.findNozzleFlowByName(title)

        val nozzleMutableList = mainScreenState.nozzles.toMutableList().apply {
            replaceAll {
                if (it.id == nozzleId) {
                    return@replaceAll when (it) {
                        is Nozzle.Adjustable -> createNozzle(it.id, title, flow)
                        is Nozzle.Fixed -> createNozzle(it.id, title, flow)
                    }
                }
                it
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

    private fun createNozzle(nozzleId: Int, title: NozzleTitle, flow: Double): Nozzle {
        return when (title) {
            NozzleTitle.SIDE_STRIP, NozzleTitle.LEFT_STRIP, NozzleTitle.RIGHT_STRIP -> {
                Nozzle.Fixed(
                    id = nozzleId, title = title, flow = flow
                )
            }

            else -> {
                Nozzle.Adjustable(
                    id = nozzleId, title = title, flow = flow
                )
            }
        }
    }
}