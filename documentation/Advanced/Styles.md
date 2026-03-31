You can apply two predefined styles with a log of customisations on top of it. `Default` and `Modern`. The *modern style* does group sections and also applies custom edges to the groups whereas the *default style* is a flat and more simply style.

Additionally you can customise how texts are rendered as well as how the preferences are styled. Check out following for more details.

All those customisations are supported by *all* preference composables.

## PreferenceStyle

| Default                                             | Modern                                             |
|-----------------------------------------------------|----------------------------------------------------|
| ![Screenshot](../screenshots/core/root-default.jpg) | ![Screenshot](../screenshots/core/root-modern.jpg) |

#### Default

<!-- snippet: DefaultStyle::create -->
```kt
/*
 * Creates a new DefaultStyle instance with the given parameters.
 *
 * This style does simply apply the defined styles to the items and sections.
 *
 * @param backgroundColor the background color of the preference items
 * @param foregroundColor the foreground color of the preference items (title color)
 * @param sectionBackgroundColor the background color of the preference sections
 * @param sectionForegroundColor the foreground color of the preference sections (title color)
 * @param shape the shape of the preference items
 * @param alphaVariant the alpha variant of the preference items (used for the content area and and subtitles in combination with the foreground color)
 * @param innerPadding the inner padding of the preference items
 * @param outerPadding the outer padding of the preference items
 * @param titleTextStyle the text style of the preference item titles
 * @param subtitleTextStyle the text style of the preference item subtitles
 * @param sectionTitleTextStyle the text style of the preference section titles
 * @param sectionSubtitleTextStyle the text style of the preference section subtitles
 * @param spacing the spacing between preference items
 */
@Composable
fun create(
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    foregroundColor: Color = MaterialTheme.colorScheme.onBackground,
    sectionBackgroundColor: Color = MaterialTheme.colorScheme.background,
    sectionForegroundColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
    alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT,
    innerPadding: PaddingValues = PreferenceStyleDefaults.DEFAULT_INNER_PADDING,
    outerPadding: PaddingValues = PreferenceStyleDefaults.DEFAULT_OUTER_PADDING,
    titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    sectionTitleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    sectionSubtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    spacing: Dp = 0.dp
) : DefaultStyle
```
<!-- endSnippet -->

#### Modern

<!-- snippet: ModernStyle::create -->
```kt
/*
* Creates a new ModernStyle instance with the given parameters.
*
* @param cornerSize the corner size for preference items (used for selector as well as for the grouping style of items inside sections)
* @param backgroundColor the background color of the preference items
* @param foregroundColor the foreground color of the preference items (title color)
* @param sectionBackgroundColor the background color of the preference sections
* @param sectionForegroundColor the foreground color of the preference sections (title color)
* @param sectionGroupItemBackgroundColor the background color used for items inside sections
* @param sectionGroupItemForegroundColor the foreground color used for items inside sections
* @param alphaVariant the alpha variant of the preference items (used for the content area and and subtitles in combination with the foreground color)
* @param innerPadding the inner padding of the preference items
* @param outerPadding the outer padding of the preference items
* @param titleTextStyle the text style of the preference item titles
* @param subtitleTextStyle the text style of the preference item subtitles
* @param sectionTitleTextStyle the text style of the preference section titles
* @param sectionSubtitleTextStyle the text style of the preference section subtitles
* @param spacing the spacing between preference items
*/
@Composable
fun create(
    cornerSize: Dp = DEFAULT_CORNER_SIZE,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    foregroundColor: Color = MaterialTheme.colorScheme.onBackground,
    sectionBackgroundColor: Color = MaterialTheme.colorScheme.background,
    sectionForegroundColor: Color = MaterialTheme.colorScheme.primary,
    sectionGroupItemBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    sectionGroupItemForegroundColor: Color = MaterialTheme.colorScheme.onSurface,
    alphaVariant: Float = PreferenceItemDefaults.DEFAULT_ALPHA_VARIANT,
    innerPadding: PaddingValues = PreferenceStyleDefaults.DEFAULT_INNER_PADDING,
    outerPadding: PaddingValues = DEFAULT_OUTER_PADDING,
    titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    sectionTitleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    sectionSubtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    spacing: Dp = DEFAULT_ITEM_SPACING
) : ModernStyle
```
<!-- endSnippet -->

## Other Styling Options

#### ItemStyle

You can find some `composable` functions inside `PreferenceStyleDefaults` that allow you to create a `PreferenceItemStyle` instance.

Those functions allow you to change colors, paddings, shapes, text styles and similar.

Following is one of those functions.

<!-- snippet: PreferenceStyleDefaults::item -->
```kt
/**
 * this function returns a [PreferenceItemStyle] defining the style of a preference item
 *
 * check out the overload to find a background/foreground based function
 *
 * @param colors the [PreferenceItemColors] of this item
 * @param innerPadding the outer [PaddingValues] of this item
 * @param outerPadding the inner [PaddingValues] of this item
 * @param shape the [Shape] of this item
 * @param tonalElevation the tonal elevation of this item
 * @param shadowElevation the shadow elevation of this item
 * @param titleTextStyle the shadow elevation of this item
 * @param subtitleTextStyle the shadow elevation of this item
 *
 * @return the [PreferenceItemStyle] holding the provided setup
 */
@Composable
fun item(
    colors: PreferenceItemColors = PreferenceItemDefaults.colors(),
    innerPadding: PaddingValues = LocalPreferenceSettings.current.style.defaultItemStyle.innerPadding,
    outerPadding: PaddingValues = LocalPreferenceSettings.current.style.defaultItemStyle.outerPadding,
    shape: Shape = LocalPreferenceSettings.current.style.defaultItemStyle.shape,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    subtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
): PreferenceItemStyle
```
<!-- endSnippet -->

#### Text Styling

By default you already adjust the styles via the `ItemStyle` (`titleTextStyle` + `subtitleTextStyle`) . However, you can also adjust the text styling by providing custom `titleRenderer` and `subtitleRenderer` to all preferences.

Those do provide an `AnnotatedString` that you can render as you like. Be awere, that this string has already applied the filter highlighting if you use filtering.

The default renderer for all preferences look like following (they use a simple `Text` composable, the correct text styles from your `ItemStyle` are provided by `CompositionLocal` already):

```kotlin
titleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) },
subtitleRenderer: @Composable (text: AnnotatedString) -> Unit = { Text(it) }
```

#### PreferenceItemSetup

You can find some `composable` functions inside `Preference*Defaults` that allow you to create a `PreferenceItemSetup` instance. All preference types to provide their own default setup.

This is some special class, it's used to internally setup a preference item and to provide user customisations. It's public because of the extendibility of this library.

Whenever you want to customise something you should do it like following:

```koltin
val setup = Preference*Defaults.itemSetup().copy(
    // ... only customise what you want to change
)
```

Here's the class that shows you what can be customised:

<!-- snippet: PreferenceItem::constructor -->
```kt
/**
 * this provides a class to define some custom setup to customise a preference item
 *
 * @param trailingContentSize the [TrailingContentSize] for a preference item
 * @param ignoreForceNoIconInset if true, the preference item will ignore the flag from [PreferenceSettings.forceNoIconInset]
 * @param minHeight the forced minimum height for the preference item
 * @param minSubTitleExtraHeight the forced minimum height addition if the preference item has a sub title
 * @param contentPlacementBottom if true, the content of this item will be place **below** the title/subtitle instead of **behind** it as trailing content
 * @param alignment the alignment of the preference item content
 * @param hideTitle if true, the title will not be displayed (allows the content to take up its place)
 * @param excludeFromSectionStyle if true, the item will not be styled as a section item and will break the section style up if it is placed in the middle of a section
 */
@Stable
data class PreferenceItemSetup(
    val trailingContentSize: TrailingContentSize = TrailingContentSize(),
    val ignoreForceNoIconInset: Boolean = false,
    val minHeight: Dp = 56.dp,
    val minSubTitleExtraHeight: Dp = 16.dp,
    val contentPlacementBottom: Boolean = false,
    val alignment: Alignment.Vertical = Alignment.CenterVertically,
    val hideTitle: Boolean = false,
    val excludeFromSectionStyle: Boolean = false
)
```
<!-- endSnippet -->
