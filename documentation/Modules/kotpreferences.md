This extension allows to to integrate `KotPreferences` into the preferences library.

#### Use a `KotPreferences` preference as dependency

<!-- snippet: Extensions::asDependency -->
```kt
/**
 * simple extension function to plug in a setting as a dependency
 *
 * @param enabled convert the value to a boolean to determine if the setting is enabled
 */
@Composable
fun <T> StorageSetting<T>.asDependency(
    enabled: (T) -> Boolean
): Dependency.State<T>
```
<!-- endSnippet -->

