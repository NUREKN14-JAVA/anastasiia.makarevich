package com.anamakarevich.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.DaoFactoryImpl;
import com.anamakarevich.usermanagement.db.MockUserDao;
import com.anamakarevich.usermanagement.util.Messages;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;
    private List users;
    
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        try {
        Properties properties = new Properties();
        properties.setProperty("com.anamakarevich.usermanagement.db.UserDao", 
                MockUserDao.class.getName());
        DaoFactory.getInstance().init(properties);
        properties.setProperty("dao.factory",
                DaoFactoryImpl.class.getName());
        setHelper(new JFCTestHelper());
        // create and open a new window
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testBrowseControls() {
        
        // check that we have the browse panel available
        find(JPanel.class, "browsePanel");
        
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(3, table.getColumnCount());
        assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0));
        assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1));
        assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2));
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "detailsButton");
        find(JButton.class, "deleteButton");
        
    }
  
    @Test
    public void testAddUser() {
        // check that there are not users in the table
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(0, table.getRowCount());
        
        // find and click the add button
        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
        
        // find the add panel
        find(JPanel.class, "addPanel");
        
        // find and save the input fields to variables
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
        
        // find the ok button
        JButton okButton = (JButton) find(JButton.class, "okButton");
        find(JButton.class, "cancelButton");
        
        // enter test data
        getHelper().sendString(new StringEventData(this, firstNameField, "John"));
        getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));
        DateFormat formatter = DateFormat.getDateInstance();
        String date = formatter.format(new Date());
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
        
        // click ok button
        getHelper().enterClickAndLeave(new MouseEventData(this,okButton));
        
        // find browse panel 
        find(JPanel.class, "browsePanel");
        table = (JTable) find(JTable.class, "userTable");
        assertEquals(1, table.getRowCount());
      
    }
    
    
    @After
    protected void tearDown() throws Exception {
        // close the window
        mainFrame.setVisible(false);
        getHelper().cleanUp(this);
        super.tearDown();
    }
  
    /**
     * Finds the component of the given class with the given name
     * @param componentClass
     * @param name
     * @return the gui component
     */
    private Component find(Class componentClass, String name) {
        // create the a finder for the gui components
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        // make the component show up instantly
        finder.setWait(0);
        Component component = finder.find(mainFrame,0);
        // check if the component was found
        assertNotNull("Could not find component ‘" + name +"'", component);
        return component;
    }
  
}
