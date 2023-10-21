### About

[![Release](https://jitpack.io/v/MFlisar/ComposePreferences.svg)](https://jitpack.io/#MFlisar/ComposePreferences)
![License](https://img.shields.io/github/license/MFlisar/ComposePreferences)

This library offers you simple setting screens for compose. Additionally it also supports my [KotPreferences](https://github.com/MFlisar/ComposePreferences) library which allows you to simply define preferences via delegates and simply use them as compose states.

Made for **Compose M3**.

### Dependencies

| Dependency |        Version |
|:-------------------------------------------------------------------- |---------------:|
| [Compose BOM](https://developer.android.com/jetpack/compose/bom/bom) |   `2023.10.00` |
| Material3 | `1.1.2` |

Compose Mappings for BOM file: [Mapping](https://developer.android.com/jetpack/compose/bom/bom-mapping)

### Gradle (via [JitPack.io](https://jitpack.io/))

1. add jitpack to your project's `build.gradle`:

```groovy
repositories {
    maven { url "https://jitpack.io" }
}
```

2. add the compile statement to your module's `build.gradle`:

```groovy
dependencies {

  val debugDrawer = "<LATEST-VERSION>"

  // core module
  implementation("com.github.MFlisar.ComposePreferences:core:$debugDrawer")
  
  // modules
  implementation("com.github.MFlisar.ComposePreferences:bool:$debugDrawer")
  
  // plugins for other libraries
  implementation("com.github.MFlisar.ComposePreferences:kotpreferences:$debugDrawer")
}
```

The latest release can be found [here](https://github.com/MFlisar/ComposePreferences/releases/latest)

### Example

It works as simple as following:

```kotlin
...
```

### Screenshots

| | | | |
| :---: | :---: | :---: | :---: |
| ![Demo](screenshots/demo1.jpg?raw=true "Demo") | ![Demo](screenshots/demo2.jpg?raw=true "Demo") | ![Demo](screenshots/demo3.jpg?raw=true "Demo") | ![Demo](screenshots/demo4.jpg?raw=true "Demo") |
| ![Demo](screenshots/demo5.jpg?raw=true "Demo") | ![Demo](screenshots/demo6.jpg?raw=true "Demo") | ![Demo](screenshots/demo7.jpg?raw=true "Demo") |  |
| ![Demo](screenshots/demo-theme-1.jpg?raw=true "Demo") | ![Demo](screenshots/demo-theme-2.jpg?raw=true "Demo") | ![Demo](screenshots/demo-theme-3.jpg?raw=true "Demo") | |