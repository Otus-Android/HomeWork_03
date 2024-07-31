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
    private val _catsData = MutableStateFlow<Result>(Initial)
    val catsData = _catsData.asStateFlow()


    init {
        viewModelScope.launch {
            catsRepository.listenForCatFacts()
                .catch { _catsData.value = Error(message = it.message ?: "ERROR") }
                .collect {
                    _catsData.value = Success(it)
                }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
    }