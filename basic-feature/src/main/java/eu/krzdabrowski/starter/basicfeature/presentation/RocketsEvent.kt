package eu.krzdabrowski.starter.basicfeature.presentation

import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable

sealed class RocketsEvent {
    data class OpenDetailScreen(val rocket: RocketDisplayable) : RocketsEvent()

}
