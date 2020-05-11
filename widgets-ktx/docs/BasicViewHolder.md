# BasicViewHolder
BasicViewHolder makes it easier to define ViewHolders and minimises the need for creating ViewHolder classes. Usually, in a `ViewHolder` class you associate the views of your item view so that they can be accessed and populated in `onBindViewHolder` method of `RecyclerView.Adapter`. A `ViewHolder` needs a `View` object from which its views can be defined. A typical `ViewHolder` looks like this.
```kotlin
inner class SampleViewHolder(view: View): RecyclerView.ViewHolder(view) {
    internal val icon: ImageView = view.findViewById(R.id.viewIcon)
    internal val title: TextView = view.findViewById(R.id.viewTitle)
    internal val description: TextView = view.findViewById(R.id.viewDescription)
}
```
(In Java, to do the same thing you would need to write few more lines).
The `ViewHolder` is created in `RecyclerView.Adapter`'s `onCreateViewHolder` method like this.
```kotlin
override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.SampleViewHolder {
    // create a new view
    val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_item, parent, false)

    return SampleViewHolder(view)
}
```
And then in `onBindViewHolder` method you access the `ViewHolder`.
```kotlin
override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
    holder.icon.setImageResource(R.drawable.icon)
    holder.title.text = "Title"
    holder.description.text = "Description"
}
```

The process of creating `BasicViewHolder` is similar as well. You don't need to define a `ViewHolder` class. You need to instantiate a `BasicViewHolder` object in `onCreateViewHolder` method.
```kotlin
override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.BasicViewHolder {
    // create a new view
    val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_item, parent, false)

    return BasicViewHolder(view, listOf(R.id.viewIcon, R.id.viewTitle, R.id.viewDescription))
}
```   
As you can see, we are passing the inflated view and the list of view ids present in the item as parameters while creating a `BasicViewHolder`. The list of view ids is needed by `BasicViewHolder` to find and map them to `View`s which can be used later. And now in `onBindViewHolder` method, you access them like this.
```kotlin
override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
    holder.getViewById<ImageView>(R.id.viewIcon)?.setImageResource(R.drawable.icon)
    holder.getViewById<TextView>(R.id.viewTitle)?.text = "Title"
    holder.getViewById<TextView>(R.id.viewDescription)?.text = "Description"
}
```
`BasicViewHolder` provides a `getViewById` method for accessing the views. All you need to do is pass the view id you want to access and specify the type of the `View`. `BasicViewHolder` internally uses a `HashMap` to store the view mappings, which is why the returned views are `Nullable`. If you are specifying the view ids correctly, you should be fine. Since `HashMap` is used, `getViewById` call does not impose any overheads and is efficient.

`BasicViewHolder` does not support onClick events. To achieve this, please see [ClickableViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/ClickableViewHolder.md).