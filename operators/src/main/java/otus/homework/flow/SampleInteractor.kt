package otus.homework.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

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
    fun task1(): Flow<String> {
        return sampleRepository.produceNumbers()
            .map { it * 5 } // Multiply each number by 5
            .filter { it > 20 } // Filter out numbers <= 20
            .filter { it % 2 != 0 } // Filter out even numbers
            .map { "$it won" } // Add the postfix "won"
            .take(3) // Take the first 3 numbers
    }

    /**
     * Классическая задача FizzBuzz с небольшим изменением.
     * Если входное число делится на 3 - эмитим само число и после него эмитим строку Fizz
     * Если входное число делится на 5 - эмитим само число и после него эмитим строку Buzz
     * Если входное число делится на 15 - эмитим само число и после него эмитим строку FizzBuzz
     * Если число не делится на 3,5,15 - эмитим само число
     */
    @OptIn(FlowPreview::class)
    fun task2(): Flow<String> {
        return sampleRepository.produceNumbers()
            .flatMapConcat { number ->
                flowOf(
                    number.toString(),
                    when {
                        number % 15 == 0 -> "FizzBuzz"
                        number % 3 == 0 -> "Fizz"
                        number % 5 == 0 -> "Buzz"
                        else -> ""
                    }
                ).filter { it.isNotEmpty() }
            }
    }

    /**
     * Реализуйте функцию task3, которая объединяет эмиты из двух flow и возвращает кортеж Pair<String,String>(f1,f2),
     * где f1 айтем из первого флоу, f2 айтем из второго флоу.
     * Если айтемы в одно из флоу кончились то результирующий флоу также должен закончится
     */
    fun task3(): Flow<Pair<String, String>> {
        val flow1 = sampleRepository.produceColors()
        val flow2 = sampleRepository.produceForms()

        return flow1.zip(flow2) { f1, f2 ->
            Pair(f1, f2)
        }
    }

    /**
     * Реализайте функцию task4, которая обрабатывает IllegalArgumentException и в качестве фоллбека
     * эмитит число -1.
     * Если тип эксепшена != IllegalArgumentException, пробросьте его дальше
     * При любом исходе, будь то выброс исключения или успешная отработка функции вызовите метод dotsRepository.completed()
     */
    fun task4(): Flow<Int> {
        return sampleRepository.produceNumbers()
            .catch { e ->
                if (e is IllegalArgumentException) {
                    emit(-1)
                } else {
                    throw e
                }
            }.onCompletion {
                sampleRepository.completed()
            }
    }
}