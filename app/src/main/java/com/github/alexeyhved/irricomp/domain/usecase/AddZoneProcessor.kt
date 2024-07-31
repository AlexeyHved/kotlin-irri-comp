package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class AddZoneProcessor @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke() : MainScreenState {
        return MainScreenState(
            zone = repo.getNextZoneId(),
        )
    }
}