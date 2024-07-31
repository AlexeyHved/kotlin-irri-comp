package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import com.github.alexeyhved.irricomp.presentation.main.PresentPipe
import javax.inject.Inject

class MainInnerDiameterValidationProcessor @Inject constructor() {
    operator fun invoke(value: String, mainScreenState: MainScreenState): MainScreenState {
        return kotlin.runCatching { value.toDouble() }
            .fold(
                onSuccess = {
                    return when {
                        it <= 0 -> {
                            mainScreenState.copy(
                                mainPipe = mainScreenState.mainPipe.copy(
                                    innerDiameterError = "Incorrect number",
                                    innerDiameter = value
                                )
                            )
                        }

                        else -> {
                            mainScreenState.copy(
                                mainPipe = mainScreenState.mainPipe.copy(
                                    innerDiameterError = "",
                                    innerDiameter = it.toString()
                                ),
                            )
                        }
                    }
                },
                onFailure = {
                    mainScreenState.copy(
                        mainPipe = PresentPipe(
                            innerDiameterError = "Incorrect number",
                            innerDiameter = value
                        )
                    )
                }
            )
    }
}