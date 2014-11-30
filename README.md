Android Exif "issue" testing
============
[What is Exif?](http://en.wikipedia.org/wiki/Exchangeable_image_file_format)

[Issue](http://stackoverflow.com/questions/5468098/reading-exif-data-from-byte-array-in-android)

Sometimes a picture is saved with Exif rotation setted (ex.: picture is saved with a rotation of 90 degrees) than to proper show this picture whoever is displaying it should rotate the picture before showing it.

So this simple app is just veryfing if I have access to the Exif info after while downloading the picture using [volley](http://www.androidhive.info/2014/05/android-working-with-volley-library-1/) [*](https://developers.google.com/events/io/sessions/325304728).

But doing that I can't use [Picasso](http://square.github.io/picasso/) that's helping me with OutOfMemmory exceptions.
####Solution####
It's easier do the exif rotation on server side, this way we normalize pictures and don't have extra processing on device side. [Lib](https://github.com/aheckmann/gm) used on server side
