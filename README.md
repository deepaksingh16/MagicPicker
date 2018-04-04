# MagicPicker

**Magic Picker** is a simple library to get rid of Android Permissions while picking image through __gallery__ or __camera__.

All the permission related code for accessing **camera** or **gallery** is handled by library internally, so that you can focus on important stuff :wink:

### Setup

1. Open Module Settings -> Click on Add **New Module** -> Select **Import Gradle project** and select **picker** library.
2. Open **app** level **_build.gradle_** and add following line in **dependencies** and you are good to go :smiley: 

```ruby
compile project(':picker')
```

### Usage

##### 1. Add permissions to manifest file
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.CAMERA"/>
```
##### 2. Adding xml folder for provider paths

Create _xml_ resource folder in values folder and create a file **provider_paths.xml** inside it.

##### 3. Register provider paths in manifest

Add following code in your **AndroidManifest.xml** file inside **-application-** tag.
```xml
<provider
     android:name="android.support.v4.content.FileProvider"
     android:authorities="${applicationId}.provider"
     android:exported="false"
     android:grantUriPermissions="true">
          <meta-data
              android:name="android.support.FILE_PROVIDER_PATHS"
              android:resource="@xml/provider_paths"/>
</provider>
```

##### 4. Create intent using **_PickerBuilder_** class

To invoke **Gallery** or **Camera**, you need to pass custom **_intent_** to **_startActivityForResult_** function. With Magic Picker, you can do this with just two lines of code.

**For Gallery**
```java
Intent intent = new PickerBuilder(context).openGallery(true).build();
startActivityForResult(intent, ImageContants.PICK_IMAGE);
```

**For Camera**
```java
Intent intent = new PickerBuilder(context).openCamera(true).build();
startActivityForResult(intent, ImageContants.PICK_IMAGE);
```

##### 5. Handle Image in _onActivityResult()_

To get Image **_Uri_**, implement **_onActivityResult_** method inside your _Activity_ or _Fragment_ and get the **_Uri_** by calling **data.getData()**

**_Example_**
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  if (resultCode == Activity.RESULT_OK) {
    switch (requestCode) {
      case ImageContants.PICK_IMAGE:
        Glide.with(context).load(data.getData()).into(imageview);
        break;
    }
  }
}
```

### Thank You :smiley: 

