package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import com.github.alexeyhved.irricomp.presentation.main.PresentPipe
import javax.inject.Inject

class MainPipeLengthValidationProcessor @Inject constructor() {
    operator fun invoke(value: String, mainScreenState: MainScreenState): MainScreenState {
        return kotlin.runCatching { value.toDouble() }
            .fold(
                onSuccess = {
                    return when {
                        it <= 0 -> {
                            mainScreenState.copy(
                                mainPipe = mainScreenState.mainPipe.copy(
                                    lengthError = "Incorrect number",
                                    length = value
                                ),

                            )
                        }

                        else -> {
                            mainScreenState.copy(
                                mainPipe = mainScreenState.mainPipe.copy(
                                    lengthError = "",
                                    length = it.toString()
                                )
                            )
                        }
                    }
                },
                onFailure = {
                    mainScreenState.copy(
                        mainPipe = PresentPipe(
                            lengthError = "Incorrect number",
                            length = value
                        )
                    )
                }
            )
    }
}