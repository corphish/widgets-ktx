# StaticAdapter
You would probably agree with the fact that `RecyclerView` is a widely used widget. But, for every `RecyclerView`, there must be an `Adapter`. And writing adapters (even for small layouts) can be annoying and repetitive task.
`StaticAdapter` makes your life easier by minimising the need for creating adapter classes. It provides a way of defining adapters to work with _non-changing data sets_ without creating a class for it.
The `StaticAdaptable` provides an interface, with which an adapter can be built and used.

### Example
```kotlin
val adapter = object: StaticAdaptable<String, ViewHolder>() {
                          override fun getLayoutResource() = R.layout.layout_item
                          override fun getListItems() = items
                          override fun getViewHolder(view: View) = ViewHolder(view)
          
                          override fun bind(viewHolder: ViewHolder, item: String) {
                              viewHolder.item.text = item
                          }
                      }.buildAdapter(true)
```
You will need to define a `ViewHolder` class which will define the views, and that's it, you have an `Adapter` ready to be attached to a `RecyclerView`.
```kotlin
recyclerView.adapter = adapter
```

### Usage
You need to define the `StaticAdaptable` interface and build an adapter from it. Definition guidelines are as follows:
-  `getLayoutResource()` - Supply the layout resource id of the item. This is used in the `onCreateViewHolder` method of RecyclerView adapter.
-  `getListItems()` - Supply the list of data which is going to be displayed. Only single item type is supported, so if you need multiple data sets, please combine them in a data class and use the data class type instead.
-  `getViewHolder(view)` - Supply the ViewHolder which defines the layout views. Build your ViewHolder with the `view` parameter which is the inflated view of id that you supplied in `getLayoutResource()`.
-  `bind(viewHolder, item)` - Supply the binding logic here. You can retrieve the views from `viewHolder` and populate them with data present in `item`.
-  `buildAdapter(notifyDataSet = false)` - Finally build the adapter, which you can use it in a recyclerView. An optional boolean parameter can be passed which can ask the adapter to invoke its `notifyDataSetChanged()` method immediately if value is true.

However, you still need to define a `ViewHolder` class and supply it to `StaticAdaptable`. Check the [BasicViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/BasicViewHolder.md) and [ClickableViewHolder](https://github.com/corphish/widgets-ktx/blob/master/widgets-ktx/docs/ClickableViewHolder.md) to easily create `ViewHolder` objects.

### Using BasicViewHolder/ClickableViewHolder in StaticAdaptable
`BasicViewHolder` and `ClickableViewHolder` makes it really easy to define ViewHolder objects without creating classes.
To create a `BasicViewHolder`, something like this can be done.
```kotlin
BasicViewHolder(view, listOf(R.id.key, R.id.value))
```

You can supply this `ViewHolder` in `StaticAdaptable`'s `getViewHolder(view, items)` method.
```kotlin
val adapter = object: StaticAdaptable<String, ViewHolder>() {
                          override fun getViewHolder(view: View, items: List<String>) = BasicViewHolder(view, listOf(R.id.key, R.id.value))
}
```

And then in `StaticAdaptable`'s `bind` method, access the `ViewHolder` like this.
```kotlin
val adapter = object: StaticAdaptable<String, ViewHolder>() {
                          override fun bind(viewHolder: ViewHolder, item: String) {
                                viewHolder.getViewById<TextView>(R.id.key)?.text = item
                          }
}
```

### Creating Adapters the Kotlin way!
The above described methods will work well in Java as well, with its own syntax of course (you might need to write a bit more, because you know, Java!). But with Kotlin, you can define an adapter by writing even lesser lines!
With the `Adapters.newStaticAdapter{}` method, you can can do just that.
```kotlin
recyclerView.adapter = Adapters.newStaticAdapter<String, BasicViewHolder> { 
    layoutResourceId = R.layout.layout_item
    listItems = myList
    viewHolder = { view -> BasicViewHolder(view, listOf(R.id.item)) }
    binding = { viewHolder, item -> 
        viewHolder.getViewById<TextView>(R.id.item)?.text = item
    }
}
```
Inside the block, you define the properties of your adapter. They will then be used to create the adapter. You will need to specify the types of your data and ViewHolder while calling the `newStaticAdapter` method. This helps in associating the necessary properties with given type.
In generic terms, the method call looks like this.
```kotlin
Adapters.newStaticAdapter<T, V> {}
```
Information on the types:
-  `T` - Indicates the type of data which will be presented in recyclerview.
-  `V` - Indicates the ViewHolder type. This must be a _RecyclerView.ViewHolder or its subtype_.

Now it's time for defining the properties.
-  `layoutResourceId` - The resource id of the layout of your item.
-  `listItems` - Data set you want to display, inform of list. The type of the list element depends on what you specified as `T`.
-  `viewHolder` - Anonymous function which lets you build the ViewHolder of type that you specified earlier. It provides you with `view` which you can use to create ViewHolder.
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

recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = PrebuiltAdapters.singleItemAdapterWith(countryList)
```

And that's it, you should a see vertical list displaying each country.