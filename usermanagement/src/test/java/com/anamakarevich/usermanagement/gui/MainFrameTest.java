package com.anamakarevich.usermanagement.gui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        setHelper(new JFCTestHelper());
        // create and open a new window
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
    
    @Test
    public void testBrowseControls() {
        
        // check that we have all the necessary buttons
        find(JPanel.class, "browsePanel");
        find(JTable.class, "userTable");
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
    }
    
    @Test
    public void testAddUser() {
        // check if the add button exists
        JButton addButton = (JButton) find(JButton.class, "addButton");
        // emulate button click
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
        find(JPanel.class, "addPanel");
        find(JTextField.class, "firstNameField");
        find(JTextField.class, "lastNameField");
        find(JTextField.class, "dateOfBirthField");
        find(JButton.class, "okButton");
    }
    
    
    @After
    protected void tearDown() throws Exception {
        // close the window
        mainFrame.setVisible(false);
        getHelper().cleanUp(this);
        super.tearDown();
    }
  
    private Component find(Class componentClass, String name) {
        // create the a finder for the gui components
        NamedComponentFinder finder  = new NamedComponentFinder(componentClass, name);
        // make the component show up instantly
        finder.setWait(0);
        Component component = finder.find(mainFrame,0);
        // check if the component was found
        assertNotNull("Could not find component ‘" + name +"'", component);
        return component;
    }

}
