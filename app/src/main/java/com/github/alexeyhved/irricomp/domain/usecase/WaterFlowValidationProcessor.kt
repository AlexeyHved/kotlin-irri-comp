package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class WaterFlowValidationProcessor @Inject constructor() {
    operator fun invoke(value: String, mainScreenState: MainScreenState): MainScreenState {
        return kotlin.runCatching { value.toDouble() }
            .fold(
                onSuccess = {
                    return when {
                        it <= 0 -> {
                            mainScreenState.copy(
                                waterFlowError = "Incorrect number",
                                waterFlow = value
                            )
                        }

                        else -> {
                            mainScreenState.copy(
                                waterFlowError = "",
                                waterFlow = it.toString()
                            )
                        }
                    }
                },
                onFailure = {
                    mainScreenState.copy(
                        waterFlowError = "Incorrect number",
                        waterFlow = value
                    )
                }
            )
    }
}