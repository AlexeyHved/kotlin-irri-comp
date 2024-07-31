package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class ChangeValveProcessor @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(mainScreenState: MainScreenState, valveName: String): MainScreenState {
        val valve = repo.getValveByName(valveName)
        return mainScreenState.copy(
            valve = valve
        )
    }
}