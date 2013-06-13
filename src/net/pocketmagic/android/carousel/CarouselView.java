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

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CarouselView extends Gallery {

    /**
     * Graphics Camera used for transforming the matrix of ImageViews
     */
	private Camera mCamera = new Camera();
	/**
	 * 	The maximum angle the Child ImageView will be rotated by
	 */    
	private int mMaxRotationAngle = 60; //60
		/**
	 * 	The Centre of the Coverflow 
	 */   
    private int mCoveflowCenter;
   
    public CarouselView(Context context) {
    	super(context);
    	this.setStaticTransformationsEnabled(true);
    }
    public CarouselView(Context context, AttributeSet attrs) {
    	super(context, attrs);
        this.setStaticTransformationsEnabled(true);
    }
 
    public CarouselView(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
    	this.setStaticTransformationsEnabled(true);   
    }
  
    /**
     * Get the max rotational angle of the image
     * @return the mMaxRotationAngle
     */
    public int getMaxRotationAngle() {
    	return mMaxRotationAngle;
    }

    /**
     * Set the max rotational angle of each image
     * @param maxRotationAngle the mMaxRotationAngle to set
     */
    public void setMaxRotationAngle(int maxRotationAngle) {
    	mMaxRotationAngle = maxRotationAngle;
    }


    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w Current width of this view.
     * @param h Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
     protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	 mCoveflowCenter = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    	 super.onSizeChanged(w, h, oldw, oldh);
     }
    /**
     * {@inheritDoc}
     *
     * @see #setStaticTransformationsEnabled(boolean) 
     */ 
    protected boolean getChildStaticTransformation(View child, Transformation t) {
    	CarouselViewItem s = (CarouselViewItem)child;
    	final int childCenter = s.getLeft() + s.getMaxW()/2;
    	final int childWidth = s.getMaxW();
    	
    	int rotationAngle = 0;
  
    	t.clear();
    	t.setTransformationType(Transformation.TYPE_MATRIX);
 
        if (childCenter == mCoveflowCenter) {
        	transformImageBitmap(s, t, 0);
        } else {      
        	rotationAngle = (int) (((float) (mCoveflowCenter - childCenter)/ childWidth) *  mMaxRotationAngle);
            if (Math.abs(rotationAngle) > mMaxRotationAngle) 
            	rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;   
            transformImageBitmap(s, t, rotationAngle);         
        }     
        return true;
    }


  
     /**
      * Transform the Image Bitmap by the Angle passed 
      * 0 for center item
      * @param imageView ImageView the ImageView whose bitmap we want to rotate
      * @param t transformation 
      * @param rotationAngle the Angle by which to rotate the Bitmap
      */
     private void transformImageBitmap(CarouselViewItem s, Transformation t, int rotationAngle) {            
    	 mCamera.save();
    	 final Matrix imageMatrix = t.getMatrix();;
    	 
    	 final int imageWidth = s.getMaxW();
    	 final int imageHeight = s.getMaxH();
    	
    	 final int rotation = Math.abs(rotationAngle);
         //As the angle of the view gets less, zoom in     
    	 float zoomAmount = (float) (rotation * 3);
    	 mCamera.translate(0.0f, 0.0f, zoomAmount);          
    	 
    	 mCamera.rotateY(rotationAngle);
    	 mCamera.getMatrix(imageMatrix);               
    	 imageMatrix.preTranslate(-(imageWidth/2), -(imageHeight/2)); 
    	 imageMatrix.postTranslate((imageWidth/2), (imageHeight/2));
    	 mCamera.restore();
     }
}
