# Widgets
[![](https://jitpack.io/v/corphish/widgets-ktx.svg)](https://jitpack.io/#corphish/widgets-ktx) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/ad8c2abe4b8b4731b845b850995d0f03)](https://www.codacy.com/manual/d97.avinaba/widgets-ktx?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=corphish/widgets-ktx&amp;utm_campaign=Badge_Grade)

Collection of custom views or widgets that I use in my apps.
Could be useful for others too.

## Setup
-   Add the jitpack repo in __root level__ `build.gradle` if not already.
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
- Add the library dependency in __app level__ `build.gradle`.
```groovy
dependencies {
	implementation 'com.github.corphish:widgets-ktx:0.4.0'
}
```

## Widgets present
- Views - Various commonly used views wrapped for easy use.
    -   [KeyValueView](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/KeyValueView.md)
    -   [PlaceholderView](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/PlaceholderView.md)
- RecyclerView adapters - Working with RecyclerView is now easier than ever as these classes eliminates the needs for creating separate adapter classes.
    -   [ImmutableListAdapter](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/ImmutableListAdapter.md)
    -   [MutableListAdapter](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/MutableListAdapter.md)
- RecyclerView ViewHolders - Let's you define a ViewHolder in one line, once again eliminating the need for creating separate classes for ViewHolders.
    -   [BasicViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/BasicViewHolder.md)
    -   [ClickableViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/ClickableViewHolder.md)