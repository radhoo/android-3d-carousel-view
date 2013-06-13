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
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

public class MainActivity extends Activity implements OnItemSelectedListener, TextWatcher {
	
	Singleton 				m_Inst 					= Singleton.getInstance();
	CarouselViewAdapter 	m_carouselAdapter		= null;	 
	private final int		m_nFirstItem			= 1000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //no keyboard unless requested by user
      	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
      		
        // compute screen size and scaling
     	Singleton.getInstance().InitGUIFrame(this);
     	
     	int padding = m_Inst.Scale(10);
		// create the interface : full screen container
		RelativeLayout panel  = new RelativeLayout(this);
	    panel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		panel.setPadding(padding, padding, padding, padding);
	    panel.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, 
	    		new int[]{Color.WHITE, Color.GRAY}));
	    setContentView(panel); 
	    
	    // copy images from assets to sdcard
	    AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma1.png", "plasma1.png", false);
	    AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma2.png", "plasma2.png", false);
	    AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma3.png", "plasma3.png", false);
	    AppUtils.AssetFileCopy(this, "/mnt/sdcard/plasma4.png", "plasma4.png", false);
	    
	    //Create carousel view documents
	    ArrayList<CarouselDataItem> Docus = new ArrayList<CarouselDataItem>();
	    for (int i=0;i<1000;i++) {
	    	CarouselDataItem docu;
	        if (i%4==0) docu = new CarouselDataItem("/mnt/sdcard/plasma1.png", 0, "First Image "+i);
	       	else if (i%4==1) docu = new CarouselDataItem("/mnt/sdcard/plasma2.png", 0, "Second Image "+i);
	        else if (i%4==2) docu = new CarouselDataItem("/mnt/sdcard/plasma3.png", 0, "Third Image "+i);
	        else docu = new CarouselDataItem("/mnt/sdcard/plasma4.png", 0, "4th Image "+i);
	        Docus.add(docu);
	    } 
	    
	    // add the serach filter
	    EditText etSearch = new EditText(this);
	    etSearch.setHint("Search your documents");
	    etSearch.setSingleLine();
	    etSearch.setTextColor(Color.BLACK);
	    etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu_search, 0, 0, 0);
	    AppUtils.AddView(panel, etSearch, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
	    		new int[][]{new int[]{RelativeLayout.CENTER_HORIZONTAL}, new int[]{RelativeLayout.ALIGN_PARENT_TOP}}, -1,-1);
	    etSearch.addTextChangedListener((TextWatcher) this); 

	    // add logo
	    TextView tv = new TextView(this);
	    tv.setTextColor(Color.BLACK);
	    tv.setText("www.pocketmagic.net");
	    AppUtils.AddView(panel, tv, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
	    		new int[][]{new int[]{RelativeLayout.CENTER_HORIZONTAL}, new int[]{RelativeLayout.ALIGN_PARENT_BOTTOM}}, -1,-1);
	    
	    // create the carousel
	    CarouselView coverFlow = new CarouselView(this);
        
	    // create adapter and specify device independent items size (scaling)
	    // for more details see: http://www.pocketmagic.net/2013/04/how-to-scale-an-android-ui-on-multiple-screens/
	    m_carouselAdapter =  new CarouselViewAdapter(this,Docus, m_Inst.Scale(400),m_Inst.Scale(300));
        coverFlow.setAdapter(m_carouselAdapter);
        coverFlow.setSpacing(-1*m_Inst.Scale(150));
        coverFlow.setSelection(Integer.MAX_VALUE / 2, true);
        coverFlow.setAnimationDuration(1000);
        coverFlow.setOnItemSelectedListener((OnItemSelectedListener) this);

        AppUtils.AddView(panel, coverFlow, LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT, 
        		new int[][]{new int[]{RelativeLayout.CENTER_IN_PARENT}},
        		-1, -1); 
    }

	public void afterTextChanged(Editable arg0) {}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		m_carouselAdapter.getFilter().filter(s.toString()); 
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		 CarouselDataItem docu =  (CarouselDataItem) m_carouselAdapter.getItem((int) arg3);
		 if (docu!=null)
			 Toast.makeText(this, "You've clicked on:"+docu.getDocText(), Toast.LENGTH_SHORT).show();
	}

	public void onNothingSelected(AdapterView<?> arg0) {}


    
}
