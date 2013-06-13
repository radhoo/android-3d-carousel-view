package net.pocketmagic.android.carousel;
/*
 * 3D carousel View
 * http://www.pocketmagic.net 
 *
 * Copyright (c) 2013 by Radu Motisan , radu.motisan@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * For more information on the GPL, please go to:
 * http://www.gnu.org/copyleft/gpl.html
 *
 */ 
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;



/* @project 
 * 
 * License to access, copy or distribute this file.
 * This file or any portions of it, is Copyright (C) 2013, Radu Motisan . All rights reserved.
 * @author Radu Motisan, radu.motisan@gmail.com
 * 
 * This file is protected by copyright law and international treaties. Unauthorized access, reproduction 
 * or distribution of this file or any portions of it may result in severe civil and criminal penalties.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * @purpose 
 */

public class Singleton extends Application {
	private static 		Singleton				m_Instance;
	// package
	//private 			String					m_szPackage,
	//											m_szVersion;
	 // Appscreen metrics
	float					m_fFrameS					= 0;
	
	public				int						m_nFrameW					= 0,
												m_nFrameH					= 0,
												m_nTotalW					= 0,
												m_nTotalH					= 0,
												m_nPaddingX					= 0,
												m_nPaddingY					= 0;
												
	// Application constants
	public static final	String					PRJNAME						= "Carousel",
												
												LOG_TAG 					= Singleton.PRJNAME + "::Singleton";


	
	public 				Activity				m_GUI						= null;	



	/*---------------------------------------------------------------------------------------------
	 * Singleton Init instance
	 *--------------------------------------------------------------------------------------------*/
	public Singleton() {
		super();
		Debug(LOG_TAG, "Singleton created.");
		m_Instance = this;
		//		
	}
	
	// Double-checked singleton fetching
	public static Singleton getInstance() {
		// init instance 
		if(m_Instance == null) {
			synchronized(Singleton.class) {
				if(m_Instance == null) new Singleton();
			}
		}
		return m_Instance;
	}  

	@Override
	public void onCreate()
	{
		Debug( LOG_TAG, "onCreate()" );
		super.onCreate();
		
	}
	
	@Override
	public void onTerminate()
	{
		Debug(LOG_TAG, "onTerminate()" );
		super.onTerminate();
	}

	/*---------------------------------------------------------------------------------------------
	 * Debug tools
	 *--------------------------------------------------------------------------------------------*/
	public void Debug(int Level, String tag, String message ) {
		switch (Level) {
				case 0: Log.e(tag,message);break;
				case 1: Log.w(tag,message);break;
				case 2: Log.i(tag,message);break;
				case 3: Log.d(tag,message);break;
				case 4: Log.v(tag,message);break;
				default: Log.d(tag,message);break;
			}
	}
	public void Debug(String tag, String message) {
		Debug(3, tag, message);
	}
	public void Debug(String message) {
		Debug(3,LOG_TAG, message);
	}
	/*---------------------------------------------------------------------------------------------
	 * GUI Metrics                                                                                
	 *--------------------------------------------------------------------------------------------*/
	
	public void InitGUIFrame(Activity context) {
		m_GUI = context;
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		m_nTotalW = dm.widthPixels;
		m_nTotalH = dm.heightPixels;
		// scale factor
		m_fFrameS = (float)m_nTotalH / 640.0f;
		// compute our frame
		m_nFrameW = (int) (960.0f * m_fFrameS);
		m_nFrameH = m_nTotalH;
		// compute padding for our frame inside the total screen size
		
		m_nPaddingY = 0;
		m_nPaddingX = (m_nTotalW - m_nFrameW) / 2;
		
		Debug(LOG_TAG, "InitGUIFrame: frame:"+m_nFrameW+"x"+m_nFrameH+ " Scale:"+m_fFrameS);
		
	}
	public int Scale(int v) {
		float s = (float)v * m_fFrameS; int rs = 0;
		
		if (s - (int)s >= 0.5) rs= ((int)s)+1; else rs= (int)s;
		//Log.d("Scale", ""+s+":"+rs);
		return rs;
	}
	public Bitmap getScaledBitmap(Context context, float scalex, float scaley, int id) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
		Matrix matrix = new Matrix();
		matrix.postScale(scalex, scaley);
		matrix.postRotate(0);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	public Drawable getScaledDrawable(Context context, float scalex, float scaley, int id) {
		return new BitmapDrawable(context.getResources(), getScaledBitmap(context, scalex, scaley, id));
	}

	Drawable ScaleDrawable(int id) {
		return getScaledDrawable(m_GUI, m_fFrameS,m_fFrameS, id);
	}
	Bitmap ScaleBitmap(int id) {
		return getScaledBitmap(m_GUI, m_fFrameS,m_fFrameS, id);
	}
	
}

