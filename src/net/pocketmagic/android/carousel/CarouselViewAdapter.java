package net.pocketmagic.android.carousel;
/*
 * Copyright (C) 2013 Radu Motisan, www.pocketmagic.net, 
 * based on the Neil Davies's CoverFlow implementation
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This code is base on the Android Gallery widget and was Created by Neil Davies neild001@gmail.com to be a Coverflow widget
 * Modified by  Radu Motisan, radu.motisan@gmail.com for the following changes:
 * - circular list: we'll never get to the end, as the last item is linked to the first, as in a circular list. Browsing the items will let the content coming, continuously and regardless of the number of actual items.
 * - nice shadow effect: each item's image is prolonged with a little shadow gradient at the bottom
 * - memory optimizations: we only create the shadow effect for the items in view, and not for the entire carousel structure, which can get very big. I had no issues in loading close to 2500 items in my view, and the animation, movement and memory status were all ok!
 * - scalable items, that will look ok , regardless of Android device screen size
 * - custom items, composed of an image and a text label
 * - filtering , as part of the attached carousel adapter structure, optimized for fast searches in large data sets 
 *
 * @author Radu Motisan
 */
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

 public class CarouselViewAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    private List<CarouselDataItem> mDocus, mDocusOrig;
    int m_w, m_h;
    
    public CarouselViewAdapter(Context c, List<CarouselDataItem> docuList, int image_max_w, int image_max_h) {
    	m_w = image_max_w;
    	m_h = image_max_h;
    	mDocus = docuList;
    	mContext = c;
    }
     
	private Bitmap createReflectedImage(Bitmap bmp) {
		if (bmp == null) return null;
		// The gap we want between the reflection and the original image
		final int reflectionGap = 0;
       	int width = bmp.getWidth();
    	int height = bmp.getHeight();
    	
    	
    	//This will not scale but will flip on the Y axis
    	Matrix matrix = new Matrix();
    	matrix.preScale(1, -1);
       
    	//Create a Bitmap with the flip matrix applied to it.
    	int reflection_part = 4;
    	//We only want the bottom half of the image
    	Bitmap reflectionImage = Bitmap.createBitmap(bmp, 0, 
    			height - height/reflection_part, width, height/reflection_part, matrix, false);
    	//Create a new bitmap with same width but taller to fit reflection
    	Bitmap bitmapWithReflection = Bitmap.createBitmap(width , (height + height/reflection_part), Config.ARGB_8888);
    	//Create a new Canvas with the bitmap that's big enough for
    	//the image plus gap plus reflection
    	Canvas canvas = new Canvas(bitmapWithReflection);
    	//Draw in the original image
    	canvas.drawBitmap(bmp, 0, 0, null);
    	//Draw in the gap
    	Paint deafaultPaint = new Paint();
    	canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
    	//Draw in the reflection
    	canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
    	//Create a shader that is a linear gradient that covers the reflection
    	Paint paint = new Paint(); 
    	LinearGradient shader = new LinearGradient(0, height, 0, 
    			bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, 
    			TileMode.CLAMP); 
    	//Set the paint to use this shader (linear gradient)
    	paint.setShader(shader); 
    	//Set the Transfer mode to be porter duff and destination in
    	paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
    	//Draw a rectangle using the paint with our linear gradient
    	canvas.drawRect(0, height, width, 
    			bitmapWithReflection.getHeight() + reflectionGap, paint); 
    	
    	return bitmapWithReflection;

	}

     public int getCount() {
         //return mDocus.length;
    	 return Integer.MAX_VALUE; 
     }

     public Object getItem(int position) {
    	 if (mDocus.size() == 0) return null;
    	 
         if (position >= mDocus.size()) { 
             position = position % mDocus.size(); 
         } 
       //  return position;
         return mDocus.get(position);
     }

     public long getItemId(int position) {
    	 if (mDocus.size() ==0) return 0; //fix divide by zero on filtering
    	 if (position >= mDocus.size()) { 
    		 position = position % mDocus.size(); 
         } 
         return position;
     }
     //@Override
     public View getView(int position, View convertView, ViewGroup parent) {
    	 // empty items
    	 if (mDocus.size() ==0){
    		 Bitmap empty = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.empty);
    		 Bitmap reflection = createReflectedImage(empty);
    		 empty.recycle();
    		 CarouselViewItem scaIv = new CarouselViewItem(mContext, reflection, "empty doc", m_w, m_h);
    	     return scaIv; //fix divide by zero on filtering
    	 }
    	 // we got content
    	 if (position >= mDocus.size()) { 
             position = position % mDocus.size(); 
         } 
    	 
    	 Bitmap originalImage = BitmapFactory.decodeFile(mDocus.get(position).getImgPath());
    	 Bitmap reflection = createReflectedImage(originalImage);
    	 if (originalImage != null)
    		 originalImage.recycle();
    	 CarouselViewItem scaIv = new CarouselViewItem(mContext, reflection, mDocus.get(position).getDocText(), m_w, m_h);
     
         return scaIv;
     }
     public int checkPosition(int position) { 
         if (position >= mDocus.size()) { 
             position = position % mDocus.size(); 
         } 
         return position; 
     } 
     /** 
      * Returns the size (0.0f to 1.0f) of the views  depending on the 'offset' to the center. 
      * 
      */ 
      public float getScale(boolean focused, int offset) { 
    	  /* Formula: 1 / (2 ^ offset) */ 
    	  return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
      }
      public Filter getFilter() {
    	    return new Filter() {

    	        @Override
    	        protected FilterResults performFiltering(CharSequence constraint) {
    	            final FilterResults oReturn = new FilterResults();
    	            //final CarouselDataItem[] mDocus;
    	            final ArrayList<CarouselDataItem> results = new ArrayList<CarouselDataItem>();
    	            if (mDocusOrig == null) mDocusOrig = mDocus;
    	            if (constraint != null) {
    	                if (mDocusOrig != null && mDocusOrig.size() > 0) {
    	                    for (final CarouselDataItem g : mDocusOrig) {
    	                        if (g.getDocText().toLowerCase()
    	                                .contains(constraint.toString()))
    	                            results.add(g);
    	                    }
    	                }
    	                oReturn.values = results;
    	            }
    	            return oReturn;
    	        }

    	        @SuppressWarnings("unchecked")
    	        @Override
    	        protected void publishResults(CharSequence constraint,
    	                FilterResults results) {
    	        	mDocus = (ArrayList<CarouselDataItem>) results.values;
    	            notifyDataSetChanged();
    	        }
    	    };
    	}

    	public void notifyDataSetChanged() {
    	    super.notifyDataSetChanged();
    	    //notifyChanged = true;
    	}
}