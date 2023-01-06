package otus.homework.flowcats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.resultObservable.collect { result ->
                    result?.let {
                        when (result) {
                            is CatsViewModel.Result.Success<*> -> {
                                if (result.data is Fact) {
                                    view.populate(result.data)
                                }
                            }
                            is CatsViewModel.Result.Error -> {
                                result.throwable.message?.let {
                                    view.showToast(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}