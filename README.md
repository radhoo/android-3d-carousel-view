# android-3d-carousel-view

Data controls take various forms, one of the mostly used being the listview. With the emerging "finger friendly" technologies, 
various controls have been developed to take advantage of easier data browsing. Android natively supports the Gallery view, 
but that looks a bit "unfinished" at least. It is, however, possible to extend the Gallery view, and handle the way each item 
is created and shown, as Neil Davies did, in his excellent CoverFlow Example tutorial (http://www.inter-fuser.com/2010/02/android-coverflow-widget-v2.html) .

I pushed things a bit further to offer the following:
- circular list: we'll never get to the end, as the last item is linked to the first, as in a circular list. Browsing the items will let the content coming, continuously and regardless of the number of actual items.
- nice shadow effect: each item's image is prolonged with a little shadow gradient at the bottom
- memory optimizations: we only create the shadow effect for the items in view, and not for the entire carousel structure, which can get very big. I had no issues in loading close to 2500 items in my view, and the animation, movement and memory status were all ok!
- scalable items, that will look ok , regardless of Android device screen size
- custom items, composed of an image and a text label
- filtering , as part of the attached carousel adapter structure, optimized for fast searches in large data sets
All in one a whole new concept that behaves with the same fluency regardless of the number of data items.

Project page: http://www.pocketmagic.net/2013/06/a-3d-carousel-view-for-android

Wiki page on https://github.com/radhoo/android-3d-carousel-view/wiki
