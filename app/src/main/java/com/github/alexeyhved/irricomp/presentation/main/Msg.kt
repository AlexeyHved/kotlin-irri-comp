package com.github.alexeyhved.irricomp.presentation.main

data class Msg(
    val mainFlowSpeed: String = "",
    val mainPressureLoss: String = "",
    val localFlowSpeed: String = "",
    val localPressureLoss: String = "",
    val valveLoss: String = "",
    val totalLoss: String = ""
) {
    fun isNotEmpty(): Boolean {
        return when {
            this.mainFlowSpeed.isNotEmpty() || this.mainPressureLoss.isNotEmpty()
                    || this.localFlowSpeed.isNotEmpty() || this.localPressureLoss.isNotEmpty()
                    || this.valveLoss.isNotEmpty() || this.totalLoss.isNotEmpty() -> true

            else -> false
        }
    }
}
