/*
 * org.openmicroscopy.shoola.env.ui.TopFrameImpl
 *
 *------------------------------------------------------------------------------
 *
 *  Copyright (C) 2004 Open Microscopy Environment
 *      Massachusetts Institute of Technology,
 *      National Institutes of Health,
 *      University of Dundee
 *
 *
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this library; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *------------------------------------------------------------------------------
 */


package org.openmicroscopy.shoola.env.ui;

//Java imports
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.env.Container;

/** 
 * Implements the {@link TopFrame} interface
 * 
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 *              <a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author  <br>Andrea Falconi &nbsp;&nbsp;&nbsp;&nbsp;
 *              <a href="mailto:a.falconi@dundee.ac.uk">
 *              a.falconi@dundee.ac.uk</a>
 * <br><b>Internal version:</b> $Revision$  $Date$
 * @version 2.2
 * @since OME2.2
 */

public class TopFrameImpl 
    extends JFrame 
    implements TopFrame, ActionListener
{
    
	/** Action command ID. */    
    static final int        FILE = 0;
    static final int        VIEW = 1;
    static final int        HELP = 2;
    static final int        EXIT = 3;
    
	/** the 3 available menus. */    
    private JMenu           fileMenu, viewMenu, helpMenu;
	/** The application internal desktop. */ 
    private JDesktopPane    desktop;
    /** Reference to the singleton {@link Container}. */ 
    private Container       container;
    
    /**
     * Creates a new Instance of {@link TopFrameImpl}
     *
     * @param container Reference to the singleton {@link Container}. 
     */ 
    public TopFrameImpl(Container container)
    {
        super("Open Microscopy Environment");
        this.container = container;
        //make sure we have nice window decorations
        JFrame.setDefaultLookAndFeelDecorated(true);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO: add icon
        setJMenuBar(createMenuBar());
        desktop = new JDesktopPane();
        getContentPane().add(desktop);
    }
    
	/** Implemented as specified by {@link TopFrame}. */     
    public void addToDesktop(Component c, int position)
    {
        desktop.add(c, new Integer(position));
    }
    
	/** Implemented as specified by {@link TopFrame}.*/ 
    public void removeFromDesktop(Component c)
    {
        desktop.remove(c);
        c.setVisible(false);
    }
    
	/** Implemented as specified by {@link TopFrame}.*/ 
    public void addToMenu(int menuType, JMenuItem menuItem)
    {
        JMenu menu = retrieveMenu(menuType);
        if (menuType == FILE) addToMenuFile(menuItem);
        else menu.add(menuItem);
    }
    
	/** Implemented as specified by {@link TopFrame}. */ 
    public void removeFromMenu(int menuType, JMenuItem item)
    {
        JMenu menu = retrieveMenu(menuType);
        menu.remove(item);
    }
    
	/** 
	 * Handles the <code>EXIT<code> event fired by the fileMenu.
	 * 
	 * Required by the ActionListener interface. 
	 */ 
    public void actionPerformed(ActionEvent e)
    {
        try {
            int     cmd = Integer.parseInt(e.getActionCommand());
            // just in case we need to handle other events
            switch (cmd) {
                case EXIT:
                  //TODO: do something
            }        
        } catch(NumberFormatException nfe) {//impossible if IDs are set correctly 
                throw nfe;  //just to be on the safe side...
        }
    }
    
	/**
	* Pops up the top frame window.
	*/
    public void open()
    {
    	pack();
    	setVisible(true);
    }
    
	/** 
	* Adds the specified menuItem to the container at the position n-1. 
	* 
	* @param menuItem       menuItem to add.
	*/
    private void addToMenuFile(JMenuItem menuItem)
    {
        JComponent popMenu = fileMenu.getPopupMenu();
        Component[] list = popMenu.getComponents();
        Component lastOne = list[list.length-1];
        fileMenu.add(menuItem);
        fileMenu.add(lastOne);
    }
    
	/** Initializes the 3 menus and add them to the menuBar. */
    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar(); 
        createFileMenu();
        viewMenu = new JMenu("View");
        helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        return menuBar;
    }
    
	/** Initializes the fileMenu. */
    private void createFileMenu()
    {
        fileMenu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.setActionCommand(""+EXIT);
        menuItem.addActionListener(this);
        fileMenu.add(menuItem);
    }

	/** 
	* Retrieves the specified menu. 
	* 
	* @param  int  menuType.
	* @return the above mentioned. 
	*/    
    private JMenu retrieveMenu(int menuType)
    {
        JMenu menu = null;
        try {
            switch (menuType) {
                case FILE:
                    menu = fileMenu;
                    break;
                case VIEW:
                    menu = viewMenu;
                    break;
                case HELP:
                    menu = helpMenu;
            }// end switch  
        } catch(NumberFormatException nfe) {//impossible if IDs are set correctly 
                throw nfe;  //just to be on the safe side...
        }    
        return menu;
    }
    
}
