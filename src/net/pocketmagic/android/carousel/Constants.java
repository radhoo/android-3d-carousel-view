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
import android.graphics.Color;

public class Constants {
	public static Singleton			m_Inst					= Singleton.getInstance();
	// text sizes
	public static final int			m_nTextSizeSmall 		= m_Inst.Scale(12),
									m_nTextSizeMedium		= m_Inst.Scale(20),
									m_nTextSizeBig			= m_Inst.Scale(25),
									m_nSmallShadow			= m_Inst.Scale(4),
									m_nCornerPaddings		= m_Inst.Scale(30),
	// misc
									m_nRoundCornerRadius	= m_Inst.Scale(20),
									
									m_nClrTextBlack			= Color.BLACK,
									m_nClrTextPurple		= Color.rgb(120,60,95),
									
									m_nCardWhitePadding 	= 0;
    public static final	int			JPG_QUALITY				= 95,
    								THUMB_MAX_WIDTH			= 80,
									THUMB_MAX_HEIGHT		= 100;
									
}
