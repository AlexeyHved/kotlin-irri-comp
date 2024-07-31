package com.github.alexeyhved.irricomp.domain

data class DomainData(
    val mainPipe: Pipe,
    val localPipe: Pipe?,
    val waterFlow: Double, // m3/s
    val valve: Valve?
)
