package com.anamakarevich.usermanagement.gui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
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
        Component component = finder.find(mainFrame,0);
        // make the component show up instantly
        finder.setWait(0);
        // check if the component was found
        assertNotNull("Could not find component ‘" + name +"'", component);
        return component;
    }

}
