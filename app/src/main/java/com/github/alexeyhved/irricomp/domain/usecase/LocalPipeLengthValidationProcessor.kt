package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import javax.inject.Inject

class LocalPipeLengthValidationProcessor @Inject constructor() {
    operator fun invoke(value: String, mainScreenState: MainScreenState): MainScreenState {
        return kotlin.runCatching { value.toDouble() }
            .fold(
                onSuccess = {
                    return when {
                        it <= 0 -> {
                            mainScreenState.copy(
                                localPipe = mainScreenState.localPipe.copy(
                                    lengthError = "Incorrect number",
                                    length = value
                                ),

                            )
                        }

                        else -> {
                            mainScreenState.copy(
                                localPipe = mainScreenState.localPipe.copy(
                                    lengthError = "",
                                    length = it.toString()
                                )
                            )
                        }
                    }
                },
                onFailure = {
                    mainScreenState.copy(
                        localPipe = mainScreenState.localPipe.copy(
                            lengthError = "Incorrect number",
                            length = value
                        )
                    )
                }
            )
    }
}