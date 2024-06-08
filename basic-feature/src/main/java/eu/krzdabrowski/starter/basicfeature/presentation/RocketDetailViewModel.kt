package eu.krzdabrowski.starter.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketDetailsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.RefreshRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState.PartialState
import eu.krzdabrowski.starter.basicfeature.presentation.mapper.toPresentationModel
import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable
import eu.krzdabrowski.starter.core.presentation.mvi.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

import javax.inject.Inject
@HiltViewModel
class RocketDetailViewModel @Inject constructor(
    private val getRocketDetailsUseCase: GetRocketDetailsUseCase,
    savedStateHandle: SavedStateHandle,
    rocketsInitialState: RocketsUiState,
) : BaseViewModel<RocketsUiState, PartialState, RocketsEvent, RocketsIntent>(
    savedStateHandle,
    rocketsInitialState,
) {
    private val rocketId: String? = savedStateHandle["rocketId"]

    init {
        rocketId?.let { observeRocketDetails(it) }
    }

    override fun mapIntents(intent: RocketsIntent): Flow<PartialState> {
        TODO("Not yet implemented")
    }

    override fun reduceUiState(
        previousState: RocketsUiState,
        partialState: PartialState
    ): RocketsUiState = when (partialState) {
        is PartialState.Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )
        is PartialState.Fetched -> previousState.copy(
            isLoading = false,
            rockets = partialState.list,
            isError = false,
        )
        is PartialState.Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
        is PartialState.FetchedDetails -> previousState.copy(
            isLoading = false,
            rocketDetails = partialState.rocket,
            isError = false,
        )
    }

    private fun observeRocketDetails(rocketId: String) = acceptChanges(
        getRocketDetailsUseCase.invoke(rocketId)
            .map { result ->
                result.fold(
                    onSuccess = { rocket ->
                        PartialState.FetchedDetails(rocket.toPresentationModel())
                    },
                    onFailure = {
                        PartialState.Error(it)
                    },
                )
            }
            .onStart {
                emit(PartialState.Loading)
            },
    )
}
