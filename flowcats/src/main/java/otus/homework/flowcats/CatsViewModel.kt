package otus.homework.flowcats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _cats: MutableStateFlow<Result> =
        MutableStateFlow(Success(Fact("", false, "", "", "", false, "", "", "")))
    val cats = _cats.asStateFlow()

    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { _cats.value = Error }
                .collect {
                _cats.value = Success(it)
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class Result
data class Success(val data: Fact) : Result()
object Error : Result()