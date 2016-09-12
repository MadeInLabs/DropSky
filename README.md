# DropSky

Create a menu that items enter with a drop animation.

![alt tag](https://github.com/MadeInLabs/DropSky/blob/master/giphy.gif)

# How to use

##1. Put the DropSkyLayout on your layout:

```xml
<br.com.madeinlabs.dropsky.DropSkyLayout android:id="@+id/drop_sky"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

If you want DropSky been scrolled you can put it inside a NestedScrollView, like this:

```xml
<android.support.v4.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <br.com.madeinlabs.dropsky.DropSkyLayout android:id="@+id/drop_sky"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"/>
</android.support.v4.widget.NestedScrollView>
```

#2. So create your DropSkyAdapter and add your items on it (custom views) together with the background colors of each item:

```java
DropSkyAdapter adapter = new DropSkyAdapter(this);
addViewItem(cutomView1, ContextCompat.getColor(context, colorResource));
mDropSkyLayout.setAdapter(adapter);
```

#3. Then, drop:

```java
mDropSkyLayout.drop(2000);
```
