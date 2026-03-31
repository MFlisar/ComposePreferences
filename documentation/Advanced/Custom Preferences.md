You have 2 options to define custom preferences. If possible, prefer the first one as it will apply all the styling for you.

#### Option 1: Use the `BasePreference` composable

<!-- snippet: demo-custom1 -->
```kt
BasePreference(
    title = "A custom preference",
    subtitle = "Showing an icon button",
    icon = { Icon(Icons.Default.Android, null) }
) {
    IconButton(onClick = {
        showInfo("IconButton clicked")
    }) {
        Icon(Icons.Default.Android, null)
    }
}
```
<!-- endSnippet -->

#### Option 2: Use the `BasePreferenceContainer` composable

> [!NOTE]
> In this case you have to apply the styling yourself - just the most basic stuff is done for you!

<!-- snippet: demo-custom2 -->
```kt
BasePreferenceContainer(
    modifier = Modifier.padding(16.dp)
) { modifier ->
    // you should use the modifier, it handles enabled/disabled state + shaping + animation
    Column(modifier) {
        Text("A custom preference", fontWeight = FontWeight.Bold, color = Color.Red)
        Text("Holding a completely customised layout...")
    }
}
```
<!-- endSnippet -->
