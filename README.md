# Widgets
[![](https://jitpack.io/v/corphish/widgets-ktx.svg)](https://jitpack.io/#corphish/widgets-ktx)

Collection of custom views or widgets that I use in my apps.
Could be useful for others too.

## Setup
- Add the jitpack repo in __root level__ `build.gradle` if not already.
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
- Add the library dependency in __app level__ `build.gradle`.
```
dependencies {
	implementation 'com.github.corphish:widgets-ktx:0.2.0'
}
```

## Widgets present
- [KeyValueView](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/KeyValueView.md)
- [PlaceholderView](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/PlaceholderView.md)
- [StaticAdapter](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/StaticAdapter.md)
- [BasicViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/BasicViewHolder.md)
- [ClickableViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/ClickableViewHolder.md)
