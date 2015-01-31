Android Exif "issue" testing
============
[What is Exif?](http://en.wikipedia.org/wiki/Exchangeable_image_file_format)

[Issue](http://stackoverflow.com/questions/5468098/reading-exif-data-from-byte-array-in-android)

Sometimes a picture is saved with Exif rotation setted (ex.: picture is saved with a rotation of 90 degrees) than to proper show this picture whoever is displaying it should rotate the picture before showing it.

So this simple app is just veryfing if I have access to the Exif info after downloading the picture (Either from the byte[] or after savign the picture into device). 

Turns out that checking the Exif from Inputstream in DownloadTask.java
```java
@Override
protected String doInBackground(String... params) {
    InputStream inputStream = downloadImage(params[0]);
    //Can't retrieve Exif here
    return saveImageToCache(inputStream);
}
```
To access exif info from a downloaded picture need to save it into device.

####Solution####
It's easier do the exif rotation on server side, this way we normalize pictures and don't have extra processing on device side. [Lib](https://github.com/aheckmann/gm) used on server side
