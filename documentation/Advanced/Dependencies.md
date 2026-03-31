All preferences allow you to enable/disable and show/hide them based on dependencies. This allows you to create complex preference screens with dependencies between preferences.

#### Example

Following shows a simple example where the second preference directly depends on the first preference. The dependency is used for the enabled state.

snippet: demo-dependency

Using it for the visibility state works the same way.

snippet: demo-dependency2

#### Depending on arbitrary data

The dependency class allows you to depend on any data, you just have to convert the data to a boolean. Following shows an example where the dependency depends on an `Int` state and derives the enabled state from it.

snippet: demo-dependency3a
snippet: demo-dependency3b
