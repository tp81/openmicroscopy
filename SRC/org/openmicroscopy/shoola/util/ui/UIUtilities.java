/*
 * org.openmicroscopy.shoola.util.ui.UIUtilities
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

package org.openmicroscopy.shoola.util.ui;

//Java imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.openmicroscopy.shoola.util.ui.border.TitledLineBorder;

//Third-party libraries

//Application-internal dependencies

/** 
 * A collection of static methods to perform common UI tasks.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author  <br>Andrea Falconi &nbsp;&nbsp;&nbsp;&nbsp;
 * 				<a href="mailto:a.falconi@dundee.ac.uk">
 * 					a.falconi@dundee.ac.uk</a>
 * @version 2.2
 * <small>
 * (<b>Internal version:</b> $Revision$ $Date$)
 * </small>
 * @since OME2.2
 */
public class UIUtilities
{
	
	/** Background color of an even row. */
	public final static Color 				BACKGROUND_COLOUR_EVEN = 
												new Color(232, 242, 254);
	
	/** Background color of an odd row. */
	public final static Color 				BACKGROUND_COLOUR_ODD = 
												new Color(255, 255, 255);
	
	/** Background color of the selected row */
	public final static Color 				SELECTED_BACKGROUND_COLOUR = new Color(180, 213, 255);
	
	/** Foreground color of a cell.*/
	public final static Color 				FOREGROUND_COLOUR = new Color(0, 0, 
																			0);
	
	/** The starting color of the gradient used in the track. */
	public static final Color 				TRACK_GRADIENT_START = 
													new Color(76, 76, 76);

	/** The final color of the gradient used in the track. */
	public static final Color 				TRACK_GRADIENT_END = 
												new Color(176, 176, 176);
	
	/** The color of the line drawn on the knobs. */
	public static final Color  				LINE_COLOR = Color.BLACK;

	/** A day in milliseconds. */
	public static final long				DAY = 86400000;
	
	/** Unicode for the degrees symbol. */
	public final static String 				DEGREES_SYMBOL = "\u00B0";
	
	
	/** Unicode for the microns symbol. */
	public final static String  			MICRONS_SYMBOL = "\u00B5m";

	/** Unicode for the squared symbol. */
	public final static String  			SQUARED_SYMBOL =  "\u00B2";
	
	/** Unicode for the squared symbol. */
	public final static String  			DELTA_SYMBOL =  "\u0394";
	
	/** Pixels string. */
	public final static String  			PIXELS_SYMBOL = "px";
	
    /** String to representing the nanometer symbol. */
    public static final String              NANOMETER = " \u00B5m";
    
    /** String to represent the micron symbol. */
    public static final String 				MICRONS = "(in \u00B5)";
    
	/** Background color of the hightlighted node. */
	public static final Color				HIGHLIGHT = new Color(204, 255, 
															204);
	/** Background color of the even rows. */
	public static final Color				BACKGROUND = Color.WHITE;
	
	/** Background color of the add rows. */
	public static final Color				BACKGROUND_ONE = new Color(236, 243,
																		254);
    /** 
     * The string displayed before an item name if the name has been
     * truncated.
     */
    public static final String              DOTS = "...";
    
    /** The Steelblue color. */
    public static final Color               STEELBLUE = new Color(0x4682B4);
    
    /** The default text color. */
    public static final Color               DEFAULT_TEXT = Color.WHITE;
    
    /** Width of the dialog window. */
    public static final int                 DIALOG_WIDTH = 500;
    
    /** Height of the dialog window. */
    public static final int                 DIALOG_HEIGHT = 500;
    
	/** Width of the separator. */
	public static final int                 SEPARATOR_WIDTH = 2;
	
    /** Maximum width of the table. */
    public static final int                 TABLE_WIDTH = 200;
    
    /** The value of the increment factor. */
    public static final int					INCREMENT = 15;
    
    /** Key value for the default folder. */
    private static final String 			DEFAULT_FOLDER = "defaultFolder";
    
    /** The default mac L&F. */
    private static final String				MAC_L_AND_F = 
    											"apple.laf.AquaLookAndFeel";
    
	/**
	 * Centers the specified component on the screen.
	 * The location of the specified component is set so that it will appear
	 * in the middle of the screen when made visible.
	 * This method is mainly useful for windows, frames and dialogs.
	 * 
	 * @param window	The component to center.
	 */
	public static void centerOnScreen(Component window)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle ed = window.getBounds();
		window.setLocation((screenSize.width-ed.width)/2, 
							(screenSize.height-ed.height)/2);
	}
	
	/**
	 * Centers the specified component on the screen and then makes it visible.
	 * This method is mainly useful for windows, frames and dialogs. 
	 * 
	 * @param window	The component to center.
	 * @see	#centerOnScreen(Component)
	 */
	public static void centerAndShow(Component window)
	{
		centerOnScreen(window);
		window.setVisible(true);
	}
    
	/**
	 * Centers the specified component on the parent and then makes it visible.
	 * This method is mainly useful for windows, frames and dialogs. 
	 * 
	 * @param parent    The visible parent.
     * @param child     The child to display.
	 */
	public static void centerAndShow(Component parent, Component child)
	{
		Rectangle bounds = parent.getBounds();
		Rectangle ed = child.getBounds();
		child.setLocation(bounds.x+(bounds.width-ed.width)/2, 
						bounds.y+(bounds.height-ed.height)/2);
		child.setVisible(true);
	}
	
    /**
     * Sets the location of the specified child relative to the location
     * of the specified parent and then makes it visible.
     * This method is mainly useful for windows, frames and dialogs. 
     * 
     * @param parent    The visible parent.
     * @param child     The child to display.
     */
    public static void setLocationRelativeToAndShow(Component parent, 
                                                Component child)
    {
        setLocationRelativeTo(parent, child);
    }
    
    /**
     * Sets the location of the specified child relative to the location
     * of the specified parent and then makes it visible.
     * This method is mainly useful for windows, frames and dialogs. 
     * 
     * @param parent    The visible parent.
     * @param child     The child to display.
     */
    public static void setLocationRelativeTo(Component parent, 
                                                Component child)
    {
        int x = parent.getX()+parent.getWidth();
        int y = parent.getY();
        int childWidth = child.getWidth();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (x+childWidth > screenSize.getWidth()) {
            if (childWidth < parent.getX()) x = parent.getX()-childWidth;
            else x = (int) (screenSize.getWidth()-childWidth);
        } 
        child.setLocation(x, y);
        child.setVisible(true);
    }
    
    /**
     * Sets the location of the specified child relative to the location
     * of the specified parent and then makes it visible, and size to fill window.
     * This method is mainly useful for windows, frames and dialogs. 
     * 
     * @param parent    The visible parent.
     * @param child     The child to display.
     * @param max		The maximum size of the window.
     */
    public static void setLocationRelativeToAndSizeToWindow(Component parent, 
                                                Component child, Dimension max)
    {
    	setLocationRelativeToAndSizeToWindow(parent.getBounds(), child, max);
    }
    
    /**
     * Sets the location of the specified child relative to the location
     * of the specified parent and then makes it visible, and size to fill window.
     * This method is mainly useful for windows, frames and dialogs. 
     * 
     * @param parentBounds    The bounds of the visible parent.
     * @param child     The child to display.
     * @param max		The maximum size of the window.
     */
    public static void setLocationRelativeToAndSizeToWindow(Rectangle parentBounds, 
                                                Component child, Dimension max)
    {

        int x = (int) (parentBounds.getX()+ parentBounds.getWidth());
        int y = (int) parentBounds.getY();
        int childWidth = child.getWidth();
        int childHeight = child.getHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (x+childWidth > screenSize.getWidth()) {
            if (childWidth < parentBounds.getX()) x = (int)(parentBounds.getX())-childWidth;
            else x = (int) (screenSize.getWidth()-childWidth);
        } 
        child.setLocation(x, y);
        int newHeight = (int)screenSize.getHeight()-y-10;
        int newWidth = (int)screenSize.getWidth()-x-10;
        
        if(newWidth>childWidth)
        	childWidth = newWidth;
        if(newHeight>childHeight)
        	childHeight = newHeight;
        
        if(childWidth>max.getWidth())
        	childWidth = (int)max.getWidth();
        if(childHeight>max.getHeight())
        	childHeight = (int)max.getHeight();
        
        child.setSize(childWidth, childHeight);
        child.setVisible(true);
    }
    
    /**
     * Sets the location of the specified child relative to the passed 
     * bounds.
     * This method is mainly useful for windows, frames and dialogs. 
     * 
     * @param parentBounds  The bounds of the parent.
     * @param child     	The child to display.
     */
    public static void setLocationRelativeTo(Rectangle parentBounds, 
                                                Component child)
    {
        int x = parentBounds.x+parentBounds.width;
        int y = parentBounds.y;
        int childWidth = child.getWidth();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (x+childWidth > screenSize.getWidth()) {
            if (childWidth < parentBounds.x) x = parentBounds.x-childWidth;
            else x = (int) (screenSize.getWidth()-childWidth);
        } 
        child.setLocation(x, y);
        child.setVisible(true);
    }
    /**
     * Sets the location of the passed component relative to the specified 
     * bounds.
     * 
     * @param bounds    The bounds of the main component.
     * @param child     The location of the child
     */
    public static void incrementRelativeToAndShow(Rectangle bounds, 
                                                Component child)
    {
        if (bounds == null) {
            UIUtilities.centerAndShow(child);
            return;
        }
        child.setLocation(bounds.x+INCREMENT, bounds.y+INCREMENT);
        child.setVisible(true);
    }
    
	/**
     * Creates a modal JDialog containing the specified JComponent
     * for the specified parent.
     * The newly created dialog is then centered on the screen and made visible.
	 *
     * @param parent The parent component.
     * @param title  The title of the dialog.
     * @param c      The component to display.
     * @see #centerAndShow(Component)
	 */
    public static void makeForDialog(Component parent, String title, 
                                    JComponent c)
    {
        if (c == null) return;
        JDialog dialog = null;
        if (parent instanceof Frame) dialog = new JDialog((Frame) parent);
        else if (parent instanceof Dialog) 
            dialog = new JDialog((Dialog) parent);
        else if (dialog == null || parent == null) 
            dialog = new JDialog(); //no parent
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setTitle(title);
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        Container container = dialog.getContentPane();
        container.setLayout(new BorderLayout(0, 0));
        container.add(c, BorderLayout.CENTER);
        centerAndShow(dialog);
    }
    
    /** 
     * Creates a modal JDialog with no title. 
     * 
     * @param parent The parent component.
     * @param c      The component to display.
     * @see #makeForDialog(Component, JComponent)
     */
    public static void makeForDialog(Component parent, JComponent c)
    {
        makeForDialog(parent, "", c);
    }
    
	/** 
	 * Builds a tool tip in a fixed font and color.
	 * You pass the tool tip text and get back an <i>HTML</i> string to be
	 * passed, in turn, to the <code>setToolTipText</code> method of a 
	 * {@link javax.swing.JComponent}.
	 *
	 * @param toolTipText     The textual content of the tool tip.
	 * @return An <i>HTML</i> fomatted string to be passed to 
	 * 			<code>setToolTipText()</code>.
	 */
	public static String formatToolTipText(String toolTipText) 
	{
		StringBuffer buf = new StringBuffer(90+toolTipText.length());
		//buf.
		buf.append("<html><body bgcolor=#FFFCB7 text=#AD5B00>");
		//TODO: change into platform independent font
		buf.append("<font face=Arial size=2>");  
		buf.append(toolTipText);
		buf.append("</font></body></html>");
		return toolTipText;
		//return buf.toString();
	} 
	
    /**
     * Builds a tool tip in a fixed font and color.
     * 
     * @param title     The title to format.
     * @param body      The body to format.
     * @param maxWidth  The maximum width of the <code>HTML</code> table.
     * @return See below.
     */
    public static String makeParagraph(String title, String body, int maxWidth)
    {
        if (title != null && body == null) 
            return formatToolTipText(title);
        //title.
        StringBuffer buf = new StringBuffer();
        buf.append("<html><body bgcolor=#FFFCB7 text=#AD5B00>");
        //TODO: change into platform independent font
        if (title != null && body != null) {
            String s = "<table width="+maxWidth+"><tr>";
            buf.append(s);
            buf.append("<td><b>");
            buf.append(title);
            buf.append("</b><hr size=1>");
            buf.append("<font face=Arial size=2>"); 
            buf.append(body);
            buf.append("</font>");
            buf.append("</td></tr></table>");
        } else if (title == null && body != null) {
            String s = "<table width="+maxWidth+"><tr>";
            buf.append(s);
            buf.append("<td>");
            buf.append("<font face=Arial size=2>"); 
            buf.append(body);
            buf.append("</font>");
            buf.append("</td></tr></table>");
        }
        buf.append("</body></html>");
        return buf.toString();  
    }
    
	/** 
	 * Create a separator to add to a toolbar. The separator needs to be 
	 * set when the layout of the toolbar is reset.
	 * 
	 * @param button   The button to add to the toolBar. The height of the 
	 * 				   separator depends of the insets of the button.
	 * @param icon     The icon to add to the button. The height of the 
     *                 separator depends of the height of the icon.
	 * @return See below.
	 */
	public static JSeparator toolBarSeparator(JButton button, Icon icon)
	{
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		Insets i = button.getInsets();
		int h = icon.getIconHeight();
		Dimension d = new Dimension(SEPARATOR_WIDTH, i.top+h+i.bottom);
		separator.setPreferredSize(d);
		separator.setSize(d);
		return separator;
	}
	
    /**
     * Displays the specified string into a {@link JLabel} and sets 
     * the font to <code>bold</code>.
     * 
     * @param s The string to display.
     * @return See above.
     */
    public static JLabel setTextFont(String s)
    {
        JLabel label = new JLabel(s);
        Font font = label.getFont();
        Font newFont = font.deriveFont(Font.BOLD);
        label.setFont(newFont);
        return label;
    }
    
    /**
     * Displays the specified string into a {@link JLabel} and sets 
     * the font to <code>bold</code>.
     * 
     * @param s 		The string to display.
     * @param fontStyle The style of font.
     * @param fontSize	The size of the font.
     * @return See above.
     */
    public static JLabel setTextFont(String s, int fontStyle, int fontSize)
    {
        JLabel label = new JLabel(s);
        Font font = label.getFont();
        label.setFont(font.deriveFont(fontStyle, fontSize));
        return label;
    }
    
    /**
     * Adds the specified {@link JComponent} to a {@link JPanel} 
     * with a left flowlayout.
     * 
     * @param component The component to add.
     * @return See below.
     */
    public static JPanel buildComponentPanel(JComponent component)
    {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(component);
        return p;
    }
    
    /**
     * Adds the specified {@link JComponent} to a {@link JPanel} 
     * with a right flowlayout.
     * 
     * @param component The component to add.
     * @return See below.
     */
    public static JPanel buildComponentPanelRight(JComponent component)
    {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p.add(component);
        return p;
    }
    
    /**
     * Adds the specified {@link JComponent} to a {@link JPanel} 
     * with a right flowlayout.
     * 
     * @param component The component to add.
     * @return See below.
     */
    public static JPanel buildComponentPanelCenter(JComponent component)
    {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        p.add(component);
        return p;
    }
    
    /**
     * Sets the UI properties of the button to unify the L&F.
     * 
     * @param b The button.
     */
    public static void unifiedButtonLookAndFeel(AbstractButton b)
    {
        b.setMargin(new Insets(0, 2, 0, 3));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }

    /**
     * Sets the opacity of the specified button depending on the 
     * system look and Feel.
     * 
     * @param b The button to handle.
     */
    public static void opacityCheck(AbstractButton b)
    {
    	String laf = UIManager.getSystemLookAndFeelClassName();
    	b.setContentAreaFilled(!(MAC_L_AND_F.equals(laf)));
    }
    
    /**
     * Sets the defaults for the specified area.
     * 
     * @param area The text area.
     */
    public static void setTextAreaDefault(JComponent area)
    {
        area.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        //area.setForeground(STEELBLUE);
        area.setBackground(Color.WHITE);
        area.setOpaque(true);
        if (area instanceof JTextComponent) 
        	((JTextComponent) area).setEditable(true);
    }
    
    /**
     * Formats a double to two decimal places and returns as a string. 
     * 
     * @param val The number to be formatted. 
     * @return The formatted string. 
     */
    public static String twoDecimalPlaces(double val)
    {
    	double v = val;
    	String value;
    	double c = v;
    	if (v < 0) return null;
    	if ((c-Math.floor(c)) > 0) value = ""+Math.round(c*100)/100f; 
    	else  value = ""+(int) c;
    	if (value.equals("0")) return null;
    	return value; 
	}
    
    /**
     * Formats the text and displays it in a {@link JTextPane}.
     * 
     * @param text The text to display.
     * @return See above.
     */
    public static JTextPane buildTextPane(String text)
    {
    	return buildTextPane(text, null);
    }
    
    /**
     * Formats the text and displays it in a {@link JEditorPane}.
     * 
     * @param text	The text to display.
     * @return See above.
     */
    public static JEditorPane buildTextEditorPane(String text)
    {
        JEditorPane textPane = new JEditorPane();
        textPane.setContentType("text/html");
        textPane.setText(text);
        textPane.setOpaque(false);
        textPane.setEditable(false);
        textPane.setFocusable(false);
        
        return textPane;
    }
    
    /**
     * Formats the text and displays it in a {@link JTextPane}.
     * 
     * @param text 			The text to display.
     * @param foreground	The foreground color.
     * @return See above.
     */
    public static JTextPane buildTextPane(String text, Color foreground)
    {
    	StyleContext context = new StyleContext();
        StyledDocument document = new DefaultStyledDocument(context);

        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
        if (foreground != null)
        	StyleConstants.setForeground(style, foreground);
        try {
            document.insertString(document.getLength(), text, style);
        } catch (BadLocationException e) {}

        JTextPane textPane = new JTextPane(document);
        textPane.setOpaque(false);
        textPane.setEditable(false);
        textPane.setFocusable(false);
        
        return textPane;
    }
    
    /** 
     * Sets the focus default for the specified button.
     * 
     * @param button The button to handle.
     */
    public static void enterPressesWhenFocused(JButton button)
    {
    	button.registerKeyboardAction(
    			button.getActionForKeyStroke(
    					KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), 
    					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), 
    					JComponent.WHEN_FOCUSED);

    	button.registerKeyboardAction(
    			button.getActionForKeyStroke(
    					KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), 
    					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), 
    					JComponent.WHEN_FOCUSED);
    }
    
    /**
     * Returns the pathname string of the default folder.
     * 
     * @return See above.
     */
    public static String getDefaultFolderAsString()
    {
    	Preferences  prefs = Preferences.userNodeForPackage(UIUtilities.class);
    	if (prefs == null) return null;
    	return prefs.get(DEFAULT_FOLDER, null);
    }
    
    /**
     * Sets the pathname string of the default folder.
     * 
     * @param f The value to set.
     */
    public static void setDefaultFolder(String f)
    {
    	Preferences  prefs = Preferences.userNodeForPackage(UIUtilities.class);
    	if (prefs == null) return;
    	if (f == null) f = "";
    	prefs.put(DEFAULT_FOLDER, f);
    }
    
    /**
     * Returns the default folder.
     *  
     * @return See above.
     */
    public static File getDefaultFolder()
    {
    	String f = UIUtilities.getDefaultFolderAsString();
    	if (f == null || f == "") return null; 
    	return new File(f);
    }
    
    /**
     * Sets the preferred size, minimum and maximum size of the specified 
     * component.
     * 
     * @param component	The component to handle.
     * @param dim		The dimension to set.
     */
    public static void setDefaultSize(Component component, Dimension dim)
    {
    	if (component == null) return;
    	if (dim == null) dim = new Dimension(5, 5);
    	component.setPreferredSize(dim);
    	component.setMaximumSize(dim);
    	component.setMinimumSize(dim);
    }
    
    /**
     * Finds the component identified by the specified class contained in the 
     * passed component. Returns the found component or <code>null</code> if 
     * none found.
     * 
     * @param comp	The component to visit. Mustn't be <code>null</code>.
     * @param c		The class identifying the component to find.
     * @return See above.
     */
    public static Component findComponent(Component comp, Class c)
    {
    	if (c == null || comp == null)
    		throw new IllegalArgumentException("The parameters cannot be " +
    				"null");
    	if (c.isAssignableFrom(comp.getClass())) return comp;
		
		if (comp instanceof Container) {
			Component[] comps = ((Container)comp).getComponents();
			Component child;
			for (int i = 0; i < comps.length; i++) {
				child = findComponent(comps[i], c);
				if (child != null) return child;
			}
		}
		return null;
    }
    
    /**
     * Finds the components identified by the specified class contained in the 
     * passed component. Returns a collection of found component or 
     * <code>null</code> if none found.
     * 
     * @param comp	The component to visit. Mustn't be <code>null</code>.
     * @param c		The class identifying the component to find.
     * @return See above.
     */
    public static List<Component> findComponents(Component comp, Class c)
    {
    	List<Component> l = null;
    	if (c == null || comp == null)
    		throw new IllegalArgumentException("The parameters cannot be " +
    				"null");
    	if (c.isAssignableFrom(comp.getClass())) {
    		l = new ArrayList<Component>(1);
    		l.add(comp);
    		return l;
    	}
    	if (comp instanceof Container) {
			Component[] comps = ((Container)comp).getComponents();
			Component child;
			l = new ArrayList<Component>(comps.length);
			for (int i = 0; i < comps.length; i++) {
				child = findComponent(comps[i], c);
				if (child != null) l.add(child);
			}
			return l;
		}
		return null;
    }
    
    /**
     * Returns <code>true</code> if the passed color is a dark color,
     * <code>false</code> otherwise.
     * 
     * @param c The color to handle. Mustn't be <code>null</code>.
     * @return See above.
     */
    public static boolean isDarkColor(Color c)
    {
    	if (c == null) return false;
    	return (c.getRed()+c.getGreen()+c.getBlue())/3 < 128;
    }

    /**
     * Creates a default timestamp.
     * 
     * @return See above.
     */
    public static Timestamp getDefaultTimestamp()
    {
        return new Timestamp(new Date().getTime());
    }
   
    /**
     * Formats as a <code>String</code> the specified time.
     * 
     * @param time The timestamp to format.
     * @return Returns the stringified version of the passed timestamp.
     */
    public static String formatTime(Timestamp time) 
    {
    	if (time == null) return "";
    	return DateFormat.getDateInstance().format(time);  
    }
    
    /**
     * Formats as a <code>String</code> the specified time.
     * 
     * @param time The timestamp to format.
     * @return Returns the stringified version of the passed timestamp.
     */
    public static String formatDateTime(Timestamp time) 
    {
    	if (time == null) return "";
    	return DateFormat.getDateTimeInstance().format(time);  
    }
	
    /**
     * Formats as a <code>String</code> the specified time.
     * 
     * @param time The timestamp to format.
     * @return Returns the stringified version of the passed timestamp.
     */
    public static String formatShortDateTime(Timestamp time) 
    {
    	if (time == null) return "";
    	return DateFormat.getDateTimeInstance(
    			DateFormat.SHORT, DateFormat.SHORT).format(time);  
    }
    
    /**
     * Formats the string to be two decimal places. 
     * 
	 * @param value The value to be formatted. 
     * @return See above.
     */
    public static String FormatToDecimal(double value) 
    {
    	try { 
    		return String.format("%.2f",value);
		} catch (Exception e) { return ""; }
    }
    
    
    /**
     * Returns the partial name of the image's name
     * 
     * @param originalName The original name.
     * @return See above.
     */
    public static String[] splitString(String originalName)
    {
    	String[] l = null;
        if (Pattern.compile("/").matcher(originalName).find()) {
            l = originalName.split("/", 0);
        } else if (Pattern.compile("\\\\").matcher(originalName).find()) {
            l = originalName.split("\\\\", 0);
        } 
        return l;
    }
    
	/** 
	 * Builds the collapse component.
	 * 
	 * @param title The title displayed in the border.
	 * @return See above.
	 */
	public static JPanel buildCollapsePanel(String title)
	{
		JPanel p = new JPanel();
		p.setBorder(new TitledLineBorder(title));
		return p;
	}
	
	/**
	 * Formats and sets the title border of the passed component.
	 * 
	 * @param title The title.
	 * @param p		The component to handle.
	 */
	public static void setBoldTitledBorder(String title, JComponent p)
	{
		TitledBorder border = new TitledBorder(title);
		border.setTitleFont(p.getFont().deriveFont(Font.BOLD));
		p.setBorder(border);
	}
	
	/**
	 * Formats the passed URL.
	 * 
	 * @param url The value to format.
	 * @return See above.
	 */
	public static String formatURL(String url)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("<html><body>");
		buf.append("<a href=\"");
		buf.append(url);
		buf.append("\"");
		buf.append(">");
		buf.append(url);
		buf.append("</a>");
		buf.append("</body></html>");
		return buf.toString();
	}
	
}
