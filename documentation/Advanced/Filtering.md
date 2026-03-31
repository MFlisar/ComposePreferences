Filtering is an optional feature. It allows to filter the preferences and render the result as a flat list or just filters and still keeps the hierarchy intact.

#### Setup

You just have to define a filter mode...

<!-- snippet: filter-modes -->
```kt
val filterModes = listOf(
    DefaultPreferenceFilter.Mode.ContainsText,
    DefaultPreferenceFilter.Mode.AllWords(false),
    DefaultPreferenceFilter.Mode.AnyWord(false)
)
```
<!-- endSnippet -->

... then create a filter setup ...

<!-- snippet: filter -->
```kt
val filter = rememberDefaultPreferenceFilter(
    highlightSpan = SpanStyle(color = Color.Red),
    mode = filterModes[0]
)
```
<!-- endSnippet -->

... and finally pass on the filter to the screen:

<!-- snippet: filter2 -->
```kt
PreferenceScreen(
    modifier = Modifier.weight(1f).fillMaxWidth(),
    settings = settings,
    filter = filter,
    state = state
)
```
<!-- endSnippet -->

#### Filter

Afterwards simple adjust the `filter.search` state in your `TextField` or wherever you want and the screen will automatically update the list of preferences based on your filter settings.

```kotlin
TextField(
    value = filter.search.value,
    onValueChange = { filter.search.value = it }
)
```

#### Other customisations

Additionally the `filter` object does provide some states to make more adjustments if desired.

<!-- snippet: DefaultPreferenceFilter::constructor -->
```kt
/**
 * Remember a DefaultPreferenceFilter with the given parameters.
 *
 * @param search the search string to filter by
 * @param flattenResult Whether to flatten the result of the filter
 * @param mode the mode to use for the filter
 * @param ignoreCase whether to ignore the case of the search string
 * @param highlightSpan the SpanStyle to use for highlighting the search string
 */
class DefaultPreferenceFilter internal constructor(
    override val search: MutableState<String>,
    override val flattenResult: MutableState<Boolean>,
    val mode: MutableState<Mode>,
    private val ignoreCase: MutableState<Boolean>,
    private val highlightSpan: SpanStyle
) : PreferenceFilter
```
<!-- endSnippet -->

### Custom filter

If desired, you can implement a full custom implementation of the `PreferenceFilter` interface. Just check out the `DefaultPreferenceFilter` to find an example on how this is done.


