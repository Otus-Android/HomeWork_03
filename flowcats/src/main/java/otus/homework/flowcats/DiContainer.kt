package otus.homework.flowcats

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com/facts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service: CatsService by lazy { retrofit.create(CatsService::class.java) }

    val repository by lazy { CatsRepository(service) }
}