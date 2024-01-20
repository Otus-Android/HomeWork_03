package otus.homework.flowcats

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
         while (true){
                val latestNews = catsService.getCatFact()
                Log.d("repository", " New Fact  $latestNews")
                emit(latestNews)
                delay(refreshIntervalMs)
        }
    }
}