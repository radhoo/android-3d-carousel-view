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

public class CarouselDataItem {
	String m_szImgPath;
	long m_nDocDate;
	String m_szDocName;
	
	public CarouselDataItem(String path, long date, String name) {
		m_szImgPath = path;
		m_nDocDate = date;
		m_szDocName = name;
	}

	
	public String getImgPath() {
		return m_szImgPath;
	}
	public String getDocText() {
		return m_szDocName;
	}

}
