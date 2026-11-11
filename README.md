# Правила именования (Мобильный хоспис)

Основные соглашения по неймингу в проекте для разработчиков.

## Код (Kotlin)

* **Пакеты**: только нижний регистр, без подчеркиваний (`repository`, `viewModel`). Если слов несколько - слитный lowercase или `camelCase`.
* **Классы и объекты**: `PascalCase` с большой буквы (`DeclarationProcessor`, `EmptyDeclarationProcessor`).
* **Функции, переменные и свойства**: `camelCase` с маленькой буквы (`processDeclarations()`, `var declarationCount = 1`).
  * *Исключение*: фабричные функции для создания экземпляров могут совпадать с именем абстрактного типа (`fun Foo(): Foo { return FooImpl() }`).
* **Константы**: `SCREAMING_SNAKE_CASE` (`const val MAX_COUNT = 8`, `val USER_NAME_FIELD = "UserName"`). Применяется для `const`, глобальных неизменяемых `val` и элементов `Enum`.
* **Изменяемые объекты/Singleton**: свойства верхнего уровня с изменяемыми данными или поведением пишутся в `camelCase` (`val mutableCollection: MutableSet<String> = HashSet()`). Ссылки на синглтоны могут копировать стиль объекта (`val PersonComparator: Comparator<Person>`).
* **Backing properties (закрытые свойства)**: префикс `_` для приватного поля при наличии публичного API:

  ```kotlin
  class C {
      private val _elementList = mutableListOf<Element>()
      val elementList: List<Element> get() = _elementList
  }
  ```

## XML ресурсы

* Использовать `snake_case`.
* В конце ID обязательно указывается тип View полностью.
* Примеры:

```xml
<ImageView android:id="@+id/line_divider_image_view"/>
<string name="date_not_set">Date not set</string>
<color name="light_grey">#F5F5F5</color>
```

## Общие смысловые правила

* Классы: существительное или фраза (`List`, `PersonReader`).
* Методы: глагол или глагольная фраза (`close`, `readPersons`). Метод должен отражать мутацию или возврат нового объекта (`sort` - на месте, `sorted` - копия).
* Без слов-пустышек (`Manager`, `Wrapper`).
* Аббревиатуры: из 2 букв - все заглавные (`IOStream`), длиннее 2 букв - только первая заглавная (`XmlFormatter`, `HttpInputStream`).
