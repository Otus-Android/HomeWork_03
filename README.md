# Homework03 - Flow

### Перейти с коллбеков на саспенд функции и корутины

1. Сейчас приложение крашится. Поправьте ошибку.
2. Поменяйте реализацию с `LiveData` на `StateFlow`
3. В случае если возникнет ошибка в стриме, нужно заэмитить айтем `Result.Error`*

*Создать sealed класс `Result`. Унаследовать от него классы `Success<T>`, `Error`. 
Использовать эти классы как стейт необходимый для рендеринга/отображени ошибки

### Реализовать функции с использованием flow операторов

1. В классе `SampleInteractor` реализуйте функции `task1`-`task4` в соответствии с условиями. 
2. Для проверки функций используйте тесты в `SampleInteractorTest`
