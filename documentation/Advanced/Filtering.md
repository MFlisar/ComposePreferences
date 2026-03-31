Filtering is an optional feature. It allows to filter the preferences and render the result as a flat list or just filters and still keeps the hierarchy intact.

#### Setup

You just have to define a filter mode...

snippet: filter-modes

... then create a filter setup ...

snippet: filter

... and finally pass on the filter to the screen:

snippet: filter2

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

snippet: DefaultPreferenceFilter::constructor

### Custom filter

If desired, you can implement a full custom implementation of the `PreferenceFilter` interface. Just check out the `DefaultPreferenceFilter` to find an example on how this is done.


