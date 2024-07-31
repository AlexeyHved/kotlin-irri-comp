package com.github.alexeyhved.irricomp.domain

import com.github.alexeyhved.irricomp.data.NozzleTitle

interface Repo {
    fun getNozzlessNameVsFlow(): Map<NozzleTitle, Double>
    fun getLocalLosesRatio(): Double
    fun getPlasticPipeRoughness(): Double
    fun getWaterViscosity(): Double
    fun getNextZoneId(): Int
    fun findNozzleFlowByName(name: NozzleTitle): Double
    fun getNextNozzleId(): Int
    fun getValveByName(name: String): Valve
    fun getValvesNames(): List<String>
}