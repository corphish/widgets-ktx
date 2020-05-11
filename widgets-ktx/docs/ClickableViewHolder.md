# ClickableViewHolder
`ClickableViewHolder` is an extension of [BasicViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/BasicViewHolder.md) which supports `onClick` events. It can be created like this.
```kotlin
val clickableViewHolder = ClickableViewHolder(view, listOf(R.id.key, R.id.value)) { view, pos ->
    // Stuff to do when it is clicked
}
```
Apart from the `view` and `viewIds` parameter, it takes an additional `onClickListener` parameter to handle `onClick` events.
The parameters of the `onClickListener` are:
- `view` - View which is clicked.
- `pos` - Position of item in the RecyclerView which is clicked (value is same as getAdapterPosition()).