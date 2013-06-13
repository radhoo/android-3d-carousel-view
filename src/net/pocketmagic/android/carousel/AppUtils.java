package net.pocketmagic.android.carousel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

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

public class AppUtils {
	final String 			LOG_TAG		 			= Singleton.PRJNAME + "::AppUtils";
	
	public static int		MAX_TEXT_LEN			= 18;
	
	/**
	 * copies files on the native filesystem, optional unzip
	 * 
	 */
	public static void AssetFileCopy(Context context, String PathDest, String assetName, boolean gunzip) {
        try {
        	File fdOut = new File(PathDest);
        	fdOut.createNewFile();
        	BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fdOut));
        	InputStream in = null;
			if (gunzip)
				in = new GZIPInputStream(context.getAssets().open(assetName));
			else
				in = new BufferedInputStream(context.getAssets().open(assetName));
        	//copy file content
        	int length;
        	byte buffer[] = new byte[4096];
        	while ((length = in.read(buffer, 0, 4096)) != -1) { 
	        	out.write(buffer,0,length);
        	}
        	out.flush();out.close();in.close();
        } catch (IOException ex) { ex.printStackTrace(); }
	}
	
	
	
	public static String ShortText(String label, int max) {
		if (label != null && label.length() > max)
			return label.substring(0, max) + "..";
		else 
			return label;
	}
	
	
	public static void Sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// interface elements
	public static RelativeLayout.LayoutParams GetRLP(int vw, int vh, int styles[][], int left, int top){
		RelativeLayout.LayoutParams lpv = new RelativeLayout.LayoutParams(vw,vh);
		if (styles!= null)
			for (int i=0;i<styles.length;i++) {
				if (styles[i] != null) {
					if (styles[i].length == 1) lpv.addRule(styles[i][0]);
					if (styles[i].length == 2) lpv.addRule(styles[i][0], styles[i][1]);
				}
			}
	    if (top!=-1) lpv.topMargin = top;
	    if (left!=-1) lpv.leftMargin = left;
	    return lpv;
	}
	
	public static void AddView(RelativeLayout panel, View v, int vw, int vh, int styles[][], int left, int top ) {
		panel.addView(v, GetRLP(vw, vh, styles, left, top));
	}
	public static void UpdateView(RelativeLayout panel, View v, int vw, int vh, int styles[][], int left, int top ) {
		panel.updateViewLayout(v, GetRLP(vw, vh, styles, left, top));
	}
	
		

}
