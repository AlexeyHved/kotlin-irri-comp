package com.github.alexeyhved.irricomp.domain

import com.github.alexeyhved.irricomp.data.NozzleSector
import com.github.alexeyhved.irricomp.data.NozzleTitle

sealed class Nozzle(
    open val id: Int,
    open val title: NozzleTitle,
    open val multiply: Int
) {
    data class Adjustable(
        override val id: Int,
        override val title: NozzleTitle,
        override val multiply: Int = 1,
        val flow: Double, // m3/h
        val sector: NozzleSector = NozzleSector.`180`,

    ) : Nozzle(id, title, multiply)

    data class Fixed(
        override val id: Int,
        override val title: NozzleTitle,
        override val multiply: Int = 1,
        val flow: Double // m3/h
    ) : Nozzle(id, title, multiply)
}
