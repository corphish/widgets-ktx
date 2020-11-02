# MutableListAdapter
You would probably agree with the fact that `RecyclerView` is a widely used widget. But, for every `RecyclerView`, there must be an `Adapter`. And writing adapters (even for small layouts) can be annoying and repetitive task.
The `MutableListAdaptable` makes your life easier by minimising the need for creating adapter classes. It provides a way of defining adapters to work with _mutable data sets_ without creating a class for it.
The `MutableListAdaptable` provides an interface, with which an adapter for both mutable data sets can be built and used.

### Example
```kotlin
val adapter = object: MutableListAdaptable<String, ViewHolder>() {
                          override fun getLayoutResource(viewType: Int) = R.layout.layout_item
                          override fun getViewHolder(view: View, viewType: Int) = ViewHolder(view)

                          override fun bind(viewHolder: ViewHolder, item: String, position: Int) {
                              viewHolder.item.text = item
                          }

                          override fun getDiffUtilItemCallback() = object: DiffUtil.ItemCallback<String>() {
                                      override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
                                      override fun areContentsTheSame(oldItem: String, newItem: String) = false
                      }.buildAdapter(true)
```
You will need to define a `ViewHolder` class which will define the views, and that's it, you have an `Adapter` ready to be attached to a `RecyclerView`.
```kotlin
recyclerView.adapter = adapter
```

`MutableListAdapter` is a child class of the `ListAdapter` class, so in order to supply the list, you need to call `submitList(newList)` of the `ListAdapter` class in order to supply or update the list. However, it is important to know that, if a new list (not the same updated list) is not passed to the `submitList` method, the changes will not fire. Which is why we have the `updateList` method of the `MutableListAdapter` which can help you avoid such situations as it will pass a new list to the `submitList` method.

### Usage
You need to define the `ListAdaptable` interface and build an adapter from it. Definition guidelines are as follows:
-  `getLayoutResource(viewType)` - Supply the layout resource id of the item. This is used in the `onCreateViewHolder` method of RecyclerView adapter. If you want to have different layouts for different items, you can use the `viewType` parameter to check and supply the ids accordingly. You can define a behavior on how view types are defined based on the item position by overriding the `getViewType()` method.
-  `getViewHolder(view, viewType)` - Supply the ViewHolder which defines the layout views. Build your ViewHolder with the `view` parameter which is the inflated view of id that you supplied in `getLayoutResource()`. If you need to have different ViewHolders for different view types, use the `viewType` parameter to detect and supply accordingly. In case you are planning to supply multiple ViewHolders, make sure to define the ViewHolder type of the Adaptable to be a generic one.
-  `getViewType(position)` - You need not override this if you are not planning to use view types, otherwise you can supply a logic on how a type of view is determined based on item position. This is a helper method, not used internally, but meant to be used by you in binding.
-  `getDiffUtilItemCallback()` - Supply the `DiffUtil.ItemCallback` here.
-  `bind(viewHolder, item, position)` - Supply the binding logic here. You can retrieve the views from `viewHolder` and populate them with `item`. Additionally, you can use `position` to determine ViewHolder type.
-  `buildAdapter()` - Builds the adapter for working with mutable lists, which you can use it in a recyclerView.

However, you still need to define a `ViewHolder` class and supply it to `MutableListAdaptable`. Check the [BasicViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/BasicViewHolder.md) and [ClickableViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/ClickableViewHolder.md) to easily create `ViewHolder` objects.

### Using BasicViewHolder/ClickableViewHolder in MutableListAdaptable
`BasicViewHolder` and `ClickableViewHolder` makes it really easy to define ViewHolder objects without creating classes.
To create a `BasicViewHolder`, something like this can be done.
```kotlin
BasicViewHolder(view, listOf(R.id.key, R.id.value))
```

You can supply this `ViewHolder` in `MutableListAdaptable`'s `getViewHolder(view, items)` method.
```kotlin
val adapter = object: MutableListAdaptable<String, ViewHolder>() {
                          override fun getViewHolder(view: View, viewType: Int) = BasicViewHolder(view, listOf(R.id.key, R.id.value))
}
```

And then in `MutableListAdaptable`'s `bind` method, access the `ViewHolder` like this.
```kotlin
val adapter = object: MutableListAdaptable<String, ViewHolder>() {
                          override fun bind(viewHolder: ViewHolder, item: String, position: Int) {
                                viewHolder.getViewById<TextView>(R.id.key)?.text = item
                          }
}
```