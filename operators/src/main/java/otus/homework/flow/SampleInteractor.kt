package otus.homework.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip

@ExperimentalCoroutinesApi
class SampleInteractor(
    private val sampleRepository: SampleRepository
) {

    /**
     * Реализуйте функцию task1 которая последовательно:
     * 1) умножает числа на 5
     * 2) убирает чила <= 20
     * 3) убирает четные числа
     * 4) добавляет постфикс "won"
     * 5) берет 3 первых числа
     * 6) возвращает результат
     */
    fun task1(): Flow<String> =
        sampleRepository.produceNumbers().map { it * 5 }.filter { it > 20 && it % 2 != 0 }
            .map { "$it won" }.take(3)


    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    @OptIn(FlowPreview::class)
    fun task2(): Flow<String> =
        sampleRepository.produceNumbers().flatMapConcat {
            flow {
                emit("$it")
                when {
                    (it % 3 == 0 && it % 5 == 0) -> {
                        emit("FizzBuzz")
                    }

                    (it % 3 == 0) -> {
                        emit("Fizz")
                    }

                    (it % 5 == 0) -> {
                        emit("Buzz")
                    }
                }
            }
        }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одно из флоу кончились то результирующий флоу также должен закончится
     */
    fun task3(): Flow<Pair<String, String>> =
        sampleRepository.produceColors().zip(
            sampleRepository.produceForms(),
            transform = { f1, f2 ->
                Pair(f1, f2)
            }
        )

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> =
        sampleRepository.produceNumbers().catch {
            when (it) {
                is IllegalArgumentException -> emit(-1)
                else -> throw it
            }
        }.onCompletion { sampleRepository.completed() }
}