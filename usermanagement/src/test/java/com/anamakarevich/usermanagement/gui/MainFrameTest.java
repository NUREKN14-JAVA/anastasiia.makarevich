package com.anamakarevich.usermanagement.gui;

import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.MockDaoFactory;
import com.anamakarevich.usermanagement.util.Messages;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.ComponentFinder;
import junit.extensions.jfcunit.finder.Finder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import com.mockobjects.dynamic.Mock;

public class MainFrameTest extends JFCTestCase {

    private MainFrame mainFrame;
    private List<User> users;
    private Mock mockUserDao;
    
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        try {
        	// choose and set up the DaoFactory implementation class
        	// and UserDao implementation -> use mock
            Properties properties = new Properties();
            properties.setProperty("dao.factory",
                MockDaoFactory.class.getName());
            DaoFactory.init(properties);
            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            
            // add some user
            User testUser = new User(666L, "Ozzy", "Osbourne", LocalDate.now());
            users = new ArrayList<User>();
            users.add(testUser);
            // mock findAll method
            mockUserDao.expectAndReturn("findAll",users);
            setHelper(new JFCTestHelper());
            // create new window
            mainFrame = new MainFrame();
            mockUserDao.verify();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // show the main window
        SwingUtilities.invokeLater(
    			new Runnable() {
    				public void run() {
    					 mainFrame.setVisible(true);
    				}
    			});
    }
    
    @Test
    public void testBrowseControls() {
        try {
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
		} catch (Exception e) {
			fail(e.toString());
		}
    }
  
    @Test
    public void testAddUser() {
        try {
			// Set up test data 
			String firstName = "John";
			String lastName = "Doe";
			LocalDate date = LocalDate.now();
			
			// prepare 2 user instances: 1st is a test data, 2d is a user 
			User user = new User(firstName, lastName, date);
			
			User expectedUser = new User(new Long(1), firstName, lastName, date);
			
			// if user is passed expectedUser is returned
			mockUserDao.expectAndReturn("create", user, expectedUser);
			
			ArrayList<User> users = new ArrayList<User>(this.users);
			
			users.add(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);
			
			// check that there are no users in the table
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			
			// find and click the add button
			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			// find the add panel
			find(JPanel.class, "addPanel");
			
			// find and and fill in the fields
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			
			getHelper().sendString(new StringEventData(this, firstNameField, "John"));
			getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date.toString()));  
			
			// check that ok an cancel buttons exist
			//find(JButton.class, "cancelButton");
			JButton okButton = (JButton) find(JButton.class, "okButton");
			// click ok
			getHelper().enterClickAndLeave(new MouseEventData(this,okButton));

			// find browse panel = check that we returned to the main screen 
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			// check that the new user has been added
			assertEquals(2, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
    }

    @Test
    public void testCancelAddUser() {
        try {
			ArrayList<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			
			// check that there are no users in the table
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			
			// find and click the add button
			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			// find the add panel
			find(JPanel.class, "addPanel");
			
			// find and and fill in the fields
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			
			getHelper().sendString(new StringEventData(this, firstNameField, "John"));
			getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));
			getHelper().sendString(new StringEventData(this, dateOfBirthField, LocalDate.now().toString()));  
			
			// check that ok an cancel buttons exist
			JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
			find(JButton.class, "okButton");
			// click cancel
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
			
			// find browse panel = check that we returned to the main screen 
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			// check that the new user has been added
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
    }
    
    @Test
    public void testEditUser() {
        try {
			User expectedUser = new User(new Long(666), "Robert", "Burns", LocalDate.now());
			System.out.println(expectedUser);
			mockUserDao.expect("update", expectedUser);

			List<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			// select first user in the table
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			// click edit button
			getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
			
			// check that the edit panel has been opened
			find(JPanel.class, "editPanel");
			
			// find all the fields
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			find(JTextField.class, "dateOfBirthField");
			
			// simulate editing data, replace Ozzy Osbourne with Robert Burns, who cares anyway?
			getHelper().sendString(new StringEventData(this, firstNameField, "Robert"));
			getHelper().sendString(new StringEventData(this, lastNameField, "Burns"));
			
			// check if cancel button exists
			find(JButton.class, "cancelButton");
			// find and click ok button
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			// check that the brows panel appears after clicking ok
			table = (JTable) find(JTable.class, "userTable");
			find(JPanel.class, "browsePanel");
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
    	
    }
    
    @Test
    public void testCancelEditUser() {
        try {
			List<User> users = new ArrayList<User>(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			// select first user in the table
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			// click edit button
			getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
			
			// check that the edit panel has been opened
			find(JPanel.class, "editPanel");
			
			// find all the fields
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			find(JTextField.class, "dateOfBirthField");
			
			// simulate editing data, replace Ozzy Osbourne with Robert Burns, who cares anyway?
			getHelper().sendString(new StringEventData(this, firstNameField, "Robert"));
			getHelper().sendString(new StringEventData(this, lastNameField, "Burns"));
			
			// check if cancel button exists
			JButton cancelButton =  (JButton) find(JButton.class, "cancelButton");
			// find and click ok button
			find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
			
			// check that the brows panel appears after clicking ok
			table = (JTable) find(JTable.class, "userTable");
			find(JPanel.class, "browsePanel");
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
    	
    }
    
    
    @Test
    public void testDetailsPanel() {

    	try {
			// prepare the main panel
			mockUserDao.expectAndReturn("findAll", users);
			// check the usertable
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
			// select the first user
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			// click edit button
			getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));
			// check that the details panel has been opened
			find(JPanel.class, "detailsPanel");
			JLabel firstName = (JLabel) find(JLabel.class, "firstName");
			JLabel lastName = (JLabel) find(JLabel.class, "lastName");
			//System.out.println(firstName.getText());
			// find and click ok button
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			// check that the brows panel appears after clicking ok
			table = (JTable) find(JTable.class, "userTable");
			find(JPanel.class, "browsePanel");
			if (!"Ozzy".equals(firstName.getText())){
				fail("Wrong user");
			}
			if (!"Osbourne".equals(lastName.getText())) {
				fail("Wrong user");
			}
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}    	
    }
 
    @Test 
    public void testDeleteUser() {
    	try {
			// prepare user to delete
			 User expectedUser = new User(new Long(666), "Ozzy", "Osbourne",  LocalDate.now());
			 mockUserDao.expect("delete", expectedUser);
			 
			 // create empty list to simulate deletion
			 List<User> users = new ArrayList<User>();
			 mockUserDao.expectAndReturn("findAll", users);
			 
			// check the usertable
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			
			// find delete button
			JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
			// select first user
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			// click delete button
			getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
			
			
			// find all the dialogs
			@SuppressWarnings("unchecked")
			List<JDialog> showingDialogs = getHelper().getShowingDialogs();
			
			// get the first dialog (actually we know that there is just one
			JDialog dialog = (JDialog)showingDialogs.get(0);
			Finder buttonFinder = new ComponentFinder(JButton.class);
			
			// find ok button
			JButton okButton = (JButton)buttonFinder.find(dialog, 0);
			
			// confirm user deletion
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			// check that we returned to the browse panel
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			// check that the user was deleted
			assertEquals(0, table.getRowCount());
			mockUserDao.verify();
			
		} catch (Exception e) {
			fail(e.toString());
		}
    }
    
    @Test 
    public void testCancelDeleteUser() {
    	try {
			 // create empty list to simulate deletion
			 List<User> users = new ArrayList<User>(this.users);
			 mockUserDao.expectAndReturn("findAll", users);
			 
			// check the usertable
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			
			// find delete button
			JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
			// select first user
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			// click delete button
			getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
			
			
			// find all the dialogs
			List<JDialog> showingDialogs = (List<JDialog>) getHelper().getShowingDialogs();

			// get the first dialog (actually we know that there is just one
			JDialog dialog = (JDialog)showingDialogs.get(0);
			Finder buttonFinder = new ComponentFinder(JButton.class);
			
			// find cancel button
			JButton cancelButton = (JButton)buttonFinder.find(dialog, 1);
			
			// cancel user deletion
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
			
			// check that we returned to the browse panel
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			// check that the user was deleted
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();
			
		} catch (Exception e) {
			fail(e.toString());
		}
    }    
    
    @After
    protected void tearDown() throws Exception {
    	try {
			mockUserDao.verify();
			// close the window
			mainFrame.setVisible(false);
			getHelper();
			TestHelper.cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
  
    /**
     * Finds the component of the given class with the given name
     * @param componentClass 
     * @param name name of the component to find
     * @return the gui component
     */
    private Component find(Class<?> componentClass, String name) {
        // create an instance of finder for the gui components
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        // make the component show up instantly when testing
        finder.setWait(0);
        Component component = finder.find(mainFrame,0);
        // check if the component was found
        assertNotNull("Could not find component ‘" + name +"'", component);
        return component;
    }
  
}
