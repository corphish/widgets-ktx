# ImmutableListAdapter
You would probably agree with the fact that `RecyclerView` is a widely used widget. But, for every `RecyclerView`, there must be an `Adapter`. And writing adapters (even for small layouts) can be annoying and repetitive task.
The `ListAdaptable` makes your life easier by minimising the need for creating adapter classes. It provides a way of defining adapters to work with _immutable as well as mutable data sets_ without creating a class for it.
The `ListAdaptable` provides an interface, with which an adapter for both mutable and immutable data sets can be built and used.

### Example
```kotlin
val adapter = object: ListAdaptable<String, ViewHolder>() {
                          override fun getLayoutResource(viewType: Int) = R.layout.layout_item
                          override fun getListItems() = items
                          override fun getViewHolder(view: View, viewType: Int) = ViewHolder(view)
          
                          override fun bind(viewHolder: ViewHolder, position: Int) {
                              viewHolder.item.text = getListItems()[position]
                          }
                      }.buildImmutableListAdapter(true) // Or buildMutableListAdapter
```
You will need to define a `ViewHolder` class which will define the views, and that's it, you have an `Adapter` ready to be attached to a `RecyclerView`.
```kotlin
recyclerView.adapter = adapter
```

### Usage
You need to define the `ListAdaptable` interface and build an adapter from it. Definition guidelines are as follows:
-  `getLayoutResource(viewType)` - Supply the layout resource id of the item. This is used in the `onCreateViewHolder` method of RecyclerView adapter. If you want to have different layouts for different items, you can use the `viewType` parameter to check and supply the ids accordingly. You can define a behavior on how view types are defined based on the item position by overriding the `getViewType()` method.
-  `getListItems()` - Supply the list of data which is going to be displayed. Only single item type is supported, so if you need multiple data sets, please combine them in a data class and use the data class type instead.
-  `getViewHolder(view, viewType)` - Supply the ViewHolder which defines the layout views. Build your ViewHolder with the `view` parameter which is the inflated view of id that you supplied in `getLayoutResource()`. If you need to have different ViewHolders for different view types, use the `viewType` parameter to detect and supply accordingly. In case you are planning to supply multiple ViewHolders, make sure to define the ViewHolder type of the Adaptable to be a generic one.
-  `getViewType(position)` - You need not override this if you are not planning to use view types, otherwise you can supply a logic on how a type of view is determined based on item position. This is a helper method, not used internally, but meant to be used by you in binding.
-  `bind(viewHolder, position)` - Supply the binding logic here. You can retrieve the views from `viewHolder` and populate them with `getListItems()[position]`.
-  `buildImmutableListAdapter(notifyDataSetChanged = false)` - Builds the adapter for working with immutable lists, which you can use it in a recyclerView. An optional boolean parameter can be passed which can ask the adapter to invoke its `notifyDataSetChanged()` method immediately if value is true.
-  `buildMutableListAdapter(diffUtilItemCallback, notifyDataSetChanged = false)` - Builds the adapter for working with mutable lists, which you can use it in a recyclerView. A `DiffUtil.ItemCallback` definition needs to passed so that item changes can be handled. An optional boolean parameter can be passed which can ask the adapter to invoke its `notifyDataSetChanged()` method immediately if value is true.

However, you still need to define a `ViewHolder` class and supply it to `ListAdaptable`. Check the [BasicViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/BasicViewHolder.md) and [ClickableViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/ClickableViewHolder.md) to easily create `ViewHolder` objects.

### Using BasicViewHolder/ClickableViewHolder in ListAdaptable
`BasicViewHolder` and `ClickableViewHolder` makes it really easy to define ViewHolder objects without creating classes.
To create a `BasicViewHolder`, something like this can be done.
```kotlin
BasicViewHolder(view, listOf(R.id.key, R.id.value))
```

You can supply this `ViewHolder` in `ListAdaptable`'s `getViewHolder(view, items)` method.
```kotlin
val adapter = object: ListAdaptable<String, ViewHolder>() {
                          override fun getViewHolder(view: View, viewType: Int) = BasicViewHolder(view, listOf(R.id.key, R.id.value))
}
```

And then in `ListAdaptable`'s `bind` method, access the `ViewHolder` like this.
```kotlin
val adapter = object: ListAdaptable<String, ViewHolder>() {
                          override fun bind(viewHolder: ViewHolder, position: Int) {
                                viewHolder.getViewById<TextView>(R.id.key)?.text = getListItems()[position]
                          }
}
```

### Creating Adapters the Kotlin way!
The above described methods will work well in Java as well, with its own syntax of course (you might need to write a bit more, because you know, Java!). But with Kotlin, you can define an adapter by writing even lesser lines!
With the `Adapters.newImmutableListAdapter{}` method, you can can do just that.
```kotlin
recyclerView.adapter = Adapters.newImmutableListAdapter<String, BasicViewHolder> { 
    layoutResourceId = { R.layout.layout_item }
    listItems = myList
    viewHolder = { view, type -> BasicViewHolder(view, listOf(R.id.item)) }
    binding = { viewHolder, position -> 
        viewHolder.getViewById<TextView>(R.id.item)?.text = listItems[position]
    }
}
```
Inside the block, you define the properties of your adapter. They will then be used to create the adapter. You will need to specify the types of your data and ViewHolder while calling the `newImmutableListAdapter` method. This helps in associating the necessary properties with given type.
In generic terms, the method call looks like this.
```kotlin
Adapters.newImmutableListAdapter<T, V> {}
```
Information on the types:
-  `T` - Indicates the type of data which will be presented in recyclerview.
-  `V` - Indicates the ViewHolder type. This must be a _RecyclerView.ViewHolder or its subtype_.

Now it's time for defining the properties.
-  `layoutResourceId` - Generator for the resource id of the layout of your item. ViewType is passed which can be accessed inside the block so that you can supply the resource id accordingly. Must return a int resource id from the block.
-  `listItems` - Data set you want to display, inform of list. The type of the list element depends on what you specified as `T`.
-  `viewHolder` - Generator which lets you build the ViewHolder of type that you specified earlier. It provides you with `view` which you can use to create ViewHolder. Additionally, you can use the 2nd parameter to determine the view type and supply the ViewHolder accordingly.
-  `binding` - Describe how the items will be bound to data in this anonymous function. It provides with the ViewHolder and item which you can use to populate the views.

### Prebuilt Adapters
For convenience, 2 simple adapters are also included for common use cases. The adapters included are:
-  `PrebuiltAdapters.singleItemAdapterWith(items: List<String>)` - Provides adapter for displaying a single text item per line. Each entry of `items` are displayed per line.
-  `PrebuiltAdapters.keyValueItemAdapterWith(items: List<Pair<String, String>>)` - Provides adapter for displaying key-value pairs in layout similar to that of `KeyValueView`.

#### Example
In your activity's layout file, `activity_main.xml` in this case, declare your `RecyclerView` just like you would normally.
```xml
<androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:id="@+id/recyclerView" />
```

And then in your activity source file, `MainActivity.kt` in this case, simply define data which you are going to display, and define the layout manager and adapter for `RecyclerView`, like this.
```kotlin
val countryList = listOf("USA", "Spain", "Italy", "Russia", "France")

// Assumes that you have acquired the layout recyclerView using findViewById() or ViewBinding
recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = PrebuiltAdapters.singleItemAdapterWith(countryList)
```

And that's it, you should a see vertical list displaying each country.