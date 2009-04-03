/*
 * org.openmicroscopy.shoola.util.ui.lens.ZoomPanel 
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */
package org.openmicroscopy.shoola.util.ui.lens;

//Java imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

//Third-party libraries

//Application-internal dependencies

/** 
 * ZoomPanel shows the zoomed image of the lens, in the centre of the 
 * ZoomWindowUI.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 	<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author	Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 * 	<a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $Date: $)
 * </small>
 * @since OME3.0
 */
class ZoomPanel
	extends JPanel
{
	
	/** Colour used to clear the background of the panel. */
	final static Color		CLEAR_COLOUR = new Color(0, 0, 0, 255);
	
	/** zoomed image from thelens, and magnified by the model. */
	private BufferedImage 	zoomImage;
	
	/** Constructor for the zoom Panel. */
	ZoomPanel() {}
	
	/**
	 * Sets the image shown on the zoomWindow.
	 * 
	 * @param img See above.
	 */
	void setZoomImage(BufferedImage img)
	{
		zoomImage = img;
		invalidate();
		repaint();
	}
	
    /**
     * Overridden to draw the zoomed image and the current position of the lens
     * on the canvas.
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(Graphics g)
    {
        int w = this.getWidth();
        int h = this.getHeight();
        if (zoomImage == null) return;
        g.setColor(CLEAR_COLOUR);
        g.fillRect(0, 0, w, h);
        int x = (w/2)-zoomImage.getWidth()/2;
        int y = (h/2)-zoomImage.getHeight()/2;
        g.drawImage(zoomImage, x, y, zoomImage.getWidth(), 
                                        zoomImage.getHeight(), null);
    }

}


