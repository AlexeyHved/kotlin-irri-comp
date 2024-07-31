package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class InitLoadData @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(stateList: List<MainScreenState>): List<MainScreenState> {
        val valvesNames = repo.getValvesNames()
        return stateList
            .map { it.copy(valvesNames = valvesNames) }
            .toList()
    }
}