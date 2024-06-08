package eu.krzdabrowski.starter.basicfeature.domain.repository

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRockets(): Flow<List<Rocket>>
    fun getRocketDetails(rocketId:String): Flow<Rocket>
    suspend fun refreshRockets()
}
