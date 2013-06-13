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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CarouselViewItem extends RelativeLayout{
	int m_w, m_h;
	
	public CarouselViewItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public int getMaxW() {
		return m_w;
	}
	public int getMaxH() {
		return m_h;
	}
	public CarouselViewItem(Context context, Bitmap img, String text, int w, int h) {
		super(context);
		m_w = w; m_h = h;
		//
		// create image
		ScalableImageView scaIv = new ScalableImageView(context, w, h);
		scaIv.setId(100);
    	scaIv.setImageBitmap(img);
     	AppUtils.AddView(this,  scaIv, w, h, 
     			new int[][]{new int[]{RelativeLayout.CENTER_IN_PARENT}}, -1, -1);
    	
		// create text
    	TextView tv = new TextView(context);
    	tv.setText(AppUtils.ShortText(text, 18));
    	tv.setTextColor(Color.BLACK);
	    //tv.setShadowLayer(2, 0, 0, Color.BLACK);
	    tv.setTextSize(Constants.m_nTextSizeMedium); 
	    
    	AppUtils.AddView(this,  tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
    			new int[][]{new int[]{RelativeLayout.CENTER_HORIZONTAL},new int[]{RelativeLayout.ALIGN_BOTTOM, 100}}, -1, -1);
	}
	
	
}
