# DropSky

Create a menu that items enter with a "drop" (top-down) or "fly" (bottom-up) animation.

#Drop Animation
![alt tag](https://github.com/MadeInLabs/DropSky/blob/master/dropsky-drop.gif)

#Fly Animation
![alt tag](https://github.com/MadeInLabs/DropSky/blob/master/dropsky-fly.gif)

# How to use

##1. Add DropSky as a dependency:

* Gradle:

```gradle
dependencies {
	compile 'com.github.madeinlabs:dropsky:1.0.1'
}
```

* Maven (add this on the `pom.xml`):

```xml
<dependency> 
    <groupId>com.github.madeinlabs</groupId>
    <artifactId>dropsky</artifactId>
    <version>1.0.1</version> 
    <type>pom</type> 
</dependency>
```

##2. Put the DropSkyLayout on your xml layout:

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

##3. So create your DropSkyAdapter and add your items on it (custom views) together with the background colors of each item:

```java
DropSkyAdapter adapter = new DropSkyAdapter(this);
addViewItem(cutomView1, ContextCompat.getColor(context, colorResource1));
...
addViewItem(cutomViewN, ContextCompat.getColor(context, colorResourceN));
mDropSkyLayout.setAdapter(adapter);
```

If you want "fly" animation, you can use other constructor:
```java
boolean reverseMode = true;
DropSkyAdapter adapter = new DropSkyAdapter(this, reverseMode);
...
```

##4. Then, show:

```java
mDropSkyLayout.show(2000);
```
