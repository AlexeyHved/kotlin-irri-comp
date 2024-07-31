package com.github.alexeyhved.irricomp.domain

data class Valve(
    val name: String,
    val consumptionVsLose: List<Pair<Double, Double>>
) {

}
