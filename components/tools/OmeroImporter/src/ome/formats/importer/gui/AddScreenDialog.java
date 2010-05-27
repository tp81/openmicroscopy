/*
 * ome.formats.importer.gui.AddDatasetDialog
 *
 *------------------------------------------------------------------------------
 *  Copyright (C) 2006-2008 University of Dundee. All rights reserved.
 *
 *
 *  This program is free software; you can redistribute it and/or modify
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

package ome.formats.importer.gui;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ome.formats.OMEROMetadataStoreClient;
import ome.formats.importer.ImportConfig;
import omero.model.Screen;


/**
 * @author Brian Loranger brain at lifesci.dundee.ac.uk
 *
 */
@SuppressWarnings("serial")
public class AddScreenDialog extends JDialog implements ActionListener
{
    boolean debug = false;

    Window                  owner;
    
    JPanel                  mainPanel;
    JPanel                  internalPanel;

    JButton                 OKBtn;
    JButton                 cancelBtn;

    JTextField              nameField;
    JTextArea               descriptionArea;

    String                  screenName;
    String                  screenDescription;
    
    Screen                  screen;
    ImportConfig	      	config;
    OMEROMetadataStoreClient      store;
    
    /**
     * @param config - ImportConfig
     * @param owner - parent window
     * @param title - dialog title
     * @param modal - modal yes/no
     * @param store - store to pull list of screens from
     */
    AddScreenDialog(ImportConfig config, Window owner, String title, Boolean modal, OMEROMetadataStoreClient store)
    {
        this.config = config;
        this.store = store;
        this.owner = owner;
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setTitle(title);
        setModal(modal);
        setResizable(true);
        setSize(new Dimension(480, 300));
        setLocationRelativeTo(owner);
        
        // Set up the main panel for tPane, quit, and send buttons
        double mainTable[][] =
                {{TableLayout.FILL, 100, 5, 100, 10}, // columns
                {TableLayout.FILL, 40}}; // rows

        mainPanel = GuiCommonElements.addMainPanel(this, mainTable, 10,10,10,10, debug);
        
        // Add the quit and send buttons to the main panel
        cancelBtn = GuiCommonElements.addButton(mainPanel, "Cancel", 'C',
                "Cancel adding a screen", "1, 1, f, c", debug);
        cancelBtn.addActionListener(this);

        OKBtn = GuiCommonElements.addButton(mainPanel, "OK", 'O',
                "Accept your new screen", "3, 1, f, c", debug);
        OKBtn.addActionListener(this);
        OKBtn.setEnabled(false);

        this.getRootPane().setDefaultButton(OKBtn);
        GuiCommonElements.enterPressesWhenFocused(OKBtn);
        
        double internalTable[][] = 
            {{160, TableLayout.FILL}, // columns
            {30, 30, TableLayout.FILL}}; // rows
        
        internalPanel = GuiCommonElements.addMainPanel(this, internalTable, 10,10,10,10, debug);

        String message = "Please enter your screen name and an optional " +
                "description below.";

        @SuppressWarnings("unused")
        JTextPane instructions = 
        	GuiCommonElements.addTextPane(internalPanel, message, "0,0,1,0", debug);

        nameField = GuiCommonElements.addTextField(internalPanel, "Screen Name: ", "", 'E',
        "Input your screen name here.", "", TableLayout.PREFERRED, "0, 1, 1, 1", debug);
        
        nameField.getDocument().addDocumentListener(new MyDocumentListener());
        
        descriptionArea = GuiCommonElements.addScrollingTextArea(internalPanel, "Description: (optional)", 
                "", 'W', "0, 2, 1, 2", debug);
        
        // Add the tab panel to the main panel
        mainPanel.add(internalPanel, "0, 0, 4, 0");
        add(mainPanel, BorderLayout.CENTER);
        
        setVisible(true);      

    }
        
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent event)
    {
        Object source = event.getSource();
        
        if (source == OKBtn)
        {  
            screenName = nameField.getText();
            screenDescription = descriptionArea.getText();
            
            if (screenName.length() > 255)
            {
                JOptionPane.showMessageDialog(this, "The screen's name can not be longer than 255 characters.");                
            }
            else if (screenName.trim().length() > 0)
            {
                screen = store.addScreen(screenName, screenDescription);
                config.savedScreen.set(screen.getId().getValue());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "The screen's name can not be blank.");
            }
        }
        
        if (source == cancelBtn)
        {
            dispose();
        }
    }

    /**
     * Check to make sure blank objects are not added to 
     * the db
     * 
     * @author Brian W. Loranger
     *
     */
    class MyDocumentListener implements DocumentListener {

		public void changedUpdate(DocumentEvent e) {}

		public void insertUpdate(DocumentEvent e) {
			if (nameField.getText().trim().length() < 1)
				OKBtn.setEnabled(false);
			else
				OKBtn.setEnabled(true);
		}

		public void removeUpdate(DocumentEvent e) {
			if (nameField.getText().trim().length() < 1)
				OKBtn.setEnabled(false);
			else
				OKBtn.setEnabled(true);
		}
    }

    
    /**
     * Testing main (for debugging only)
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        String laf = UIManager.getSystemLookAndFeelClassName() ;
        //laf = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
        //laf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        //laf = "javax.swing.plaf.metal.MetalLookAndFeel";
        //laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        
        if (laf.equals("apple.laf.AquaLookAndFeel"))
        {
            System.setProperty("Quaqua.design", "panther");
            
            try {
                UIManager.setLookAndFeel(
                    "ch.randelshofer.quaqua.QuaquaLookAndFeel"
                );
           } catch (Exception e) { System.err.println(laf + " not supported.");}
        } else {
            try {
                UIManager.setLookAndFeel(laf);
            } catch (Exception e) 
            { System.err.println(laf + " not supported."); }
        }
        new AddScreenDialog(null, null, "Add a Screen", true, null);
    }
}
