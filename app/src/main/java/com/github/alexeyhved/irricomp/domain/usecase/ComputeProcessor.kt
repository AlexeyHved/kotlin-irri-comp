package com.github.alexeyhved.irricomp.domain.usecase

import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.domain.Valve
import com.github.alexeyhved.irricomp.presentation.main.MainScreenState
import com.github.alexeyhved.irricomp.presentation.main.Msg
import com.github.alexeyhved.irricomp.presentation.main.convertToDomainData
import okhttp3.internal.format
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.pow

class ComputeProcessor @Inject constructor(
    private val repo: Repo
) {
    operator fun invoke(mainScreenState: MainScreenState): MainScreenState {
        val domainData = mainScreenState.convertToDomainData()
        val mainFlowSpeed = calcFlowSpeed(
            flow = domainData.waterFlow, innerDiameter = domainData.mainPipe.innerDiameter
        )
        val mainLambda = calcLambda(
            flowSpeed = mainFlowSpeed,
            innerDiameter = domainData.mainPipe.innerDiameter,
            waterViscosity = repo.getWaterViscosity()
        )
        var mainPressureLoss = calcPressureLoss(
            lambda = mainLambda,
            pipeLength = domainData.mainPipe.length,
            flowSpeed = mainFlowSpeed,
            innerDiameter = domainData.mainPipe.innerDiameter
        )
        if (mainScreenState.isLocalLoses) {
            mainPressureLoss *= 1.05
        }

        val valveLossRes = findValveLoss(
            valve = domainData.valve,
            waterFlow = domainData.waterFlow * 3600
        )

        var msg = buildMainMsg(mainFlowSpeed = mainFlowSpeed, mainPressureLoss = mainPressureLoss, valveLossRes = valveLossRes)

        if (domainData.localPipe != null) {
            val localFlowSpeed = calcFlowSpeed(
                flow = domainData.waterFlow, innerDiameter = domainData.localPipe.innerDiameter
            )
            val localLambda = calcLambda(
                flowSpeed = localFlowSpeed,
                innerDiameter = domainData.localPipe.innerDiameter,
                waterViscosity = repo.getWaterViscosity()
            )
            var localPressureLoss = calcPressureLoss(
                lambda = localLambda,
                pipeLength = domainData.localPipe.length,
                flowSpeed = localFlowSpeed,
                innerDiameter = domainData.localPipe.innerDiameter
            )
            if (mainScreenState.isLocalLoses) {
                localPressureLoss *= 1.05
            }

            msg = buildLocalMsg(
                mainFlowSpeed = mainFlowSpeed,
                mainPressureLoss = mainPressureLoss,
                localFlowSpeed = localFlowSpeed,
                localPressureLoss = localPressureLoss,
                valveLossRes = valveLossRes
            )
        }

        return mainScreenState.copy(msg = msg)
    }

    private fun calcFlowSpeed(
        flow: Double,
        innerDiameter: Double
    ): Double = (4 * flow) / (PI * innerDiameter.pow(2))

    private fun calcLambda(
        flowSpeed: Double,
        innerDiameter: Double,
        waterViscosity: Double
    ): Double {
        val Re = (flowSpeed * innerDiameter) / waterViscosity
        return when {
            0 < Re && Re <= 2300 -> {
                64 / Re
            }

            2300 < Re && Re <= 4000 -> {
                0.03
            }

            4000 < Re -> {
                0.3164 / Re.pow(0.25)
            }

            else -> {
                -1.0
            }
        }
    }

    private fun calcPressureLoss(
        lambda: Double,
        pipeLength: Double,
        flowSpeed: Double,
        innerDiameter: Double
    ): Double {
        return (lambda * pipeLength * flowSpeed.pow(2)) / (2 * innerDiameter * 9.81)
    }

    private fun findValveLoss(valve: Valve?, waterFlow: Double): Result<Double> {
        return when {
            valve == null -> {
                return Result.failure(IllegalStateException("-"))
            }
            waterFlow < valve.consumptionVsLose.first().first -> {
                Result.failure(NoSuchElementException("-"))
            }

            waterFlow > valve.consumptionVsLose.last().first -> {
                Result.failure(NoSuchElementException("Water flow is too hard for this valve"))
            }

            else -> {
                kotlin.runCatching {
                    valve.consumptionVsLose
                        .first { waterFlow <= it.first }
                        .second
                }
            }
        }
    }

    private fun buildMainMsg(mainFlowSpeed: Double, mainPressureLoss: Double, valveLossRes: Result<Double>): Msg {
        var valveLoses: String = ""
        var totalLoses: String = ""

        valveLossRes.onSuccess {
            valveLoses = "Valve loss: ${format("%.2f", it)} m"
            totalLoses = "Total loss: ${format("%.2f", mainPressureLoss + it)} m"
        }
            .onFailure {
                totalLoses = "Total loss: ${format("%.2f", mainPressureLoss)} m"
            }
        return Msg(
            mainFlowSpeed = "Main flow speed: ${format("%.2f", mainFlowSpeed)} m/sec",
            mainPressureLoss = "Main pressure loss: ${format("%.2f", mainPressureLoss)} m",
            valveLoss = valveLoses,
            totalLoss = totalLoses
        )
    }

    private fun buildLocalMsg(
        mainFlowSpeed: Double,
        mainPressureLoss: Double,
        localFlowSpeed: Double,
        localPressureLoss: Double,
        valveLossRes: Result<Double>,
    ): Msg {
        var valveLoses: String = ""
        var totalLoses: String = ""

        valveLossRes.onSuccess {
            valveLoses = "Valve loss: ${format("%.2f", it)} m"
            totalLoses = "Total loss: ${format("%.2f", mainPressureLoss + localPressureLoss + it)} m"
        }
            .onFailure {
                totalLoses = "Total loss: ${format("%.2f", mainPressureLoss)} m"
            }

        return Msg(
            mainFlowSpeed = "Main flow speed: ${format("%.2f", mainFlowSpeed)} m/sec",
            mainPressureLoss = "Main pressure loss: ${format("%.2f", mainPressureLoss)} m",
            localFlowSpeed = "Local flow speed: ${format("%.2f", localFlowSpeed)} m/sec",
            localPressureLoss = "Local pressure loss: ${format("%.2f", localPressureLoss)} m",
            valveLoss = valveLoses,
            totalLoss = totalLoses
        )
    }
}