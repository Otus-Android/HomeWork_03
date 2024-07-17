package otus.homework.flowcats

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun listenForCatFacts() = flow {
        while (true) {
            val latestNews = try {
                Result.Success<Fact>(body = catsService.getCatFact())
            } catch (e: Throwable) {
                Result.Error
            }
            emit(latestNews)
            delay(refreshIntervalMs)
        }
    }.flowOn(ioDispatcher)
}