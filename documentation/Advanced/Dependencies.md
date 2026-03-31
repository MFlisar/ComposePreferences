All preferences allow you to enable/disable and show/hide them based on dependencies. This allows you to create complex preference screens with dependencies between preferences.

#### Example

Following shows a simple example where the second preference directly depends on the first preference. The dependency is used for the enabled state.

<!-- snippet: demo-dependency -->
```kt
PreferenceBool(
    style = PreferenceBool.Style.Switch,
    value = main1.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("main1", it)
        }
    },
    title = "1 MAIN SWITCH",
    subtitle = "This switch does control the enabled states of the next 2 preferences",
    icon = { Icon(Icons.Default.Info, null) },
    itemStyle = PreferenceStyleDefaults.primaryContainer()
)
PreferenceInfo(
    title = "Sub Item 1.1...",
    subtitle = "Enabled if main switch is enabled",
    icon = { Icon(Icons.Default.Info, null) },
    enabled = Dependency.State(main1) { it }
)
```
<!-- endSnippet -->

Using it for the visibility state works the same way.

<!-- snippet: demo-dependency2 -->
```kt
PreferenceInfo(
    title = "Sub Item 2.2...",
    subtitle = "Only visible if parent switch is enabled...",
    icon = { Icon(Icons.Default.Info, null) },
    visible = Dependency.State(main2) { it }
)
```
<!-- endSnippet -->

#### Depending on arbitrary data

The dependency class allows you to depend on any data, you just have to convert the data to a boolean. Following shows an example where the dependency depends on an `Int` state and derives the enabled state from it.

<!-- snippet: demo-dependency3a -->
```kt
val intDependency1 = dataStore.getInt("intDependency1", 0).collectAsState(initial = 0)
```
<!-- endSnippet -->
<!-- snippet: demo-dependency3b -->
```kt
val intDependency2 = dataStore.getInt("intDependency2", 0).collectAsState(initial = 0)
PreferenceNumber(
    style = PreferenceNumber.Style.Slider(),
    value = intDependency2.value,
    onValueChange = {
        scope.launch(DispatcherIO) {
            dataStore.update("intDependency2", it)
        }
    },
    min = 0,
    max = 10,
    stepSize = 1,
    title = "Sub Number 1",
    subtitle = "Only enabled, if main number >= 5",
    enabled = Dependency.State(intDependency1) {
        it >= 5
    }
)
```
<!-- endSnippet -->
