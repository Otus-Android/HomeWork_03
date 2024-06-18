package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import otus.homework.flowcats.model.Result

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts(): Flow<Result> = flow {
        while (true) {
            try {
                val latestNews = catsService.getCatFact()
                emit(Result.Success(latestNews))
                delay(refreshIntervalMs)
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }
}