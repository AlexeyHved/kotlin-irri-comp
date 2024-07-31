package com.github.alexeyhved.irricomp.data

import com.github.alexeyhved.irricomp.domain.Repo
import com.github.alexeyhved.irricomp.domain.Valve
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoIMpl @Inject constructor() : Repo {
    private var zoneId: Int = 1
    private var nozzleId: Int = 1
    private val localLosesRatio: Double = 1.05
    private val plasticPipeRoughness: Double = 0.0007
    private val waterViscosity: Double = 1.004E-6
    private val valves: List<Valve> = listOf(
        Valve(
            name = "PGV-101",
            consumptionVsLose = listOf( // m3/h, bar
                Pair(0.3, 0.08),
                Pair(1.0, 0.11),
                Pair(2.5, 0.13),
                Pair(3.5, 0.16),
                Pair(4.5, 0.23),
                Pair(5.5, 0.43),
                Pair(6.5, 0.62),
                Pair(8.0, 1.10),
                Pair(9.0, 1.48),

            )
        ),
        Valve(
            name = "PGV-151",
            consumptionVsLose = listOf( // m3/h, bar
                Pair(4.5, 0.2),
                Pair(5.5, 0.2),
                Pair(6.5, 0.2),
                Pair(8.0, 0.2),
                Pair(9.0, 0.2),
                Pair(11.0, 0.3),
                Pair(13.5, 0.3),
                Pair(18.0, 0.4),
                Pair(22.5, 0.6),
                Pair(27.0, 0.8),
            )
        ),
        Valve(
            name = "PGV-201",
            consumptionVsLose = listOf( // m3/h, bar
                Pair(4.5, 0.1),
                Pair(5.5, 0.1),
                Pair(6.5, 0.1),
                Pair(8.0, 0.1),
                Pair(9.0, 0.1),
                Pair(11.0, 0.1),
                Pair(13.5, 0.1),
                Pair(18.0, 0.2),
                Pair(22.5, 0.3),
                Pair(27.0, 0.4),
            )
        )
    )

    private val nozzlesNameVsFlow: Map<NozzleTitle, Double> = mapOf(
        Pair(NozzleTitle.MP800, 0.10),
        Pair(NozzleTitle.MP815, 0.21),
        Pair(NozzleTitle.MP1000, 0.10),
        Pair(NozzleTitle.MP2000, 0.18),
        Pair(NozzleTitle.MP3000, 0.42),
        Pair(NozzleTitle.MP3500, 0.42),
        Pair(NozzleTitle.SIDE_STRIP, 0.09),
        Pair(NozzleTitle.LEFT_STRIP, 0.04),
        Pair(NozzleTitle.RIGHT_STRIP, 0.04),
    )

    override fun getNextZoneId(): Int = ++zoneId
    override fun getNextNozzleId(): Int = ++nozzleId
    override fun getWaterViscosity(): Double = waterViscosity
    override fun getPlasticPipeRoughness(): Double = plasticPipeRoughness
    override fun getNozzlessNameVsFlow(): Map<NozzleTitle, Double> = nozzlesNameVsFlow
    override fun findNozzleFlowByName(name: NozzleTitle): Double = nozzlesNameVsFlow[name]!!
    override fun getLocalLosesRatio(): Double = localLosesRatio
    override fun getValveByName(name: String): Valve = valves.first { it.name == name }.copy()
    override fun getValvesNames() = valves.map { it.name }.toList()
}