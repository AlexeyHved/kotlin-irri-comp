package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class SwitchValveProcessor @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(mainScreenState: MainScreenState): MainScreenState {
        return when  {
            mainScreenState.valve == null -> {
                mainScreenState.copy(
                    valve = repo.getValveByName("PGV-101"),
                    isValveEnabled = true
                )
            }

            else -> {
                mainScreenState.copy(valve = null, isValveEnabled = false)
            }
        }
    }
}