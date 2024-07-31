package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class LocalInnerDiameterValidationProcessor @Inject constructor() {
    operator fun invoke(value: String, mainScreenState: MainScreenState): MainScreenState {
        return kotlin.runCatching { value.toDouble() }
            .fold(
                onSuccess = {
                    return when {
                        it <= 0 -> {
                            mainScreenState.copy(
                                localPipe = mainScreenState.localPipe.copy(
                                    innerDiameterError = "Incorrect number",
                                    innerDiameter = value
                                )
                            )
                        }

                        else -> {
                            mainScreenState.copy(
                                localPipe = mainScreenState.localPipe.copy(
                                    innerDiameterError = "",
                                    innerDiameter = it.toString()
                                ),
                            )
                        }
                    }
                },
                onFailure = {
                    mainScreenState.copy(
                        localPipe = mainScreenState.localPipe.copy(
                            innerDiameterError = "Incorrect number",
                            innerDiameter = value
                        )
                    )
                }
            )
    }
}