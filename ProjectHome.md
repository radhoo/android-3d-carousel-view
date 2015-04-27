## Intro ##
Data controls take various forms, one of the mostly used being the listview. With the emerging "finger friendly" technologies, various controls have been developed to take advantage of easier data browsing. Android natively supports the Gallery view,but that looks a bit "unfinished" at least. It is, however, possible to extend the Gallery view, and handle the way each item is created and shown, as Neil Davies did, in his excellent [CoverFlow Example tutorial](http://www.inter-fuser.com/2010/02/android-coverflow-widget-v2.html).<br />
[![](http://www.pocketmagic.net/wp-content/uploads/2013/06/android_carousel_gallery_view_01-300x187.jpg)](http://www.pocketmagic.net/2013/06/a-3d-carousel-view-for-android)
<br />
I pushed things a bit further to offer the following:<br />
- circular list: we'll never get to the end, as the last item is linked to the first, as in a circular list. Browsing the items will let the content coming, continuously and regardless of the number of actual items.<br />
- nice shadow effect: each item's image is prolonged with a little shadow gradient at the bottom<br />
- memory optimizations: we only create the shadow effect for the items in view, and not for the entire carousel structure, which can get very big. I had no issues in loading close to 2500 items in my view, and the animation, movement and memory status were all ok!<br />
- scalable items, that will look ok , regardless of Android device screen size<br />
- custom items, composed of an image and a text label<br />
- filtering , as part of the attached carousel adapter structure, optimized for fast searches in large data sets<br />
All in one a whole new concept that behaves with the same fluency regardless of the number of data items.<br />
[![](http://www.pocketmagic.net/wp-content/uploads/2013/06/android_carousel_gallery_view_03-300x187.jpg)](http://www.pocketmagic.net/2013/06/a-3d-carousel-view-for-android)<br />
The code details are available on http://www.pocketmagic.net/2013/06/a-3d-carousel-view-for-android/<br />


Released under GPL v2.0 license.<br />