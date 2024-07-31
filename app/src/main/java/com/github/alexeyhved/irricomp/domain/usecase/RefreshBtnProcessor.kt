package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class RefreshBtnProcessor @Inject constructor() {
    operator fun invoke(zoneId: Int): MainScreenState {
        return MainScreenState(
            zone = zoneId
        )
    }
}