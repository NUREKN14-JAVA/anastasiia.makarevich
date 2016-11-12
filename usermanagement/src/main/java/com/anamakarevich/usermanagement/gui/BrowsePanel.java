package com.anamakarevich.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.db.DatabaseException;
import com.anamakarevich.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    private MainFrame parent;
    private JPanel buttonPanel;
    private JScrollPane tablePanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JTable userTable;

    public BrowsePanel(MainFrame frame) {
        parent = frame;
        initialize();
    }

    /**
     * Sets up the browse panel
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setName("browsePanel");  //$NON-NLS-1$
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
        
    }

    /**
     * Gets or creates buttons panel
     * @return buttons panel
     */
    private JPanel getButtonsPanel() {
        if (buttonPanel == null) {
            // create panel for the buttons
            buttonPanel = new JPanel();
            // add buttons to the panel
            buttonPanel.add(getAddButton());
            buttonPanel.add(getEditButton());
            buttonPanel.add(getDeleteButton());
            buttonPanel.add(getDetailsButton());
        }
        return buttonPanel;
    }

    /**
     * Gets or creates details button
     * @return details button
     */
    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details")); //$NON-NLS-1$
            detailsButton.setName("detailsButton"); //$NON-NLS-1$
            detailsButton.setActionCommand("details"); //$NON-NLS-1$
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }
    
    /**
     * Gets or creates delete button
     * @return delete button
     */
    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete")); //$NON-NLS-1$
            deleteButton.setName("deleteButton"); //$NON-NLS-1$
            deleteButton.setActionCommand("delete"); //$NON-NLS-1$
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    /**
     * Get or creates edit button
     * @return edit button
     */
    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit")); //$NON-NLS-1$
            editButton.setName("editButton"); //$NON-NLS-1$
            editButton.setActionCommand("edit"); //$NON-NLS-1$
            editButton.addActionListener(this);
        }
        return editButton;        
    }

    /**
     * Gets or creates add button
     * @return add button
     */
    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add")); //$NON-NLS-1$
            addButton.setName("addButton"); //$NON-NLS-1$
            addButton.setActionCommand("add"); //$NON-NLS-1$
            addButton.addActionListener(this);
        }
        return addButton;
    }
   
    /**
     * Gets or creates a table with users
     * @return
     */
    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    /**
     * Gets or creates users table
     * @return
     */
    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable"); //$NON-NLS-1$            
        }
        //initTable();
        return userTable;
    }

    public void initTable() {
        UserTableModel model;
        try {
        	// try to get all users from the database
            model = new UserTableModel(parent.getDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList<User>());
            JOptionPane.showMessageDialog(this,e.getMessage(),Messages.getString("BrowsePanel.error"),  //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)){ //$NON-NLS-1$
        	// hide browse panel and show add panel instead
            this.setVisible(false);
            parent.showAddPanel();
            
        }
        if ("edit".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
        	
            User user = getSelectedUser();
            if (user ==null) {
            	return;
            }
            this.setVisible(false);
            parent.showEditPanel(user);
        }
        if ("details".equalsIgnoreCase(actionCommand)) {
        	User user = getSelectedUser();
        	if (user == null) {
        		return;
        	}
        	this.setVisible(false);
        	parent.showDetailsPanel(user);
        }
        if ("delete".equals(actionCommand)){
        	User user = getSelectedUser();
        	if (user == null) {
        		return;
        	}
        	int answer = JOptionPane.showConfirmDialog(
        		    parent,
        		    "Are you sure you want to delete?",
        		    "Confirmation",
        		    JOptionPane.OK_CANCEL_OPTION);
        	if (answer == JOptionPane.OK_OPTION) {
        		try {
					parent.getDao().delete(user);
				} catch (DatabaseException e1) {
	                JOptionPane.showMessageDialog(this, 
	                        e1.getMessage(), 
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
	                e1.printStackTrace();
				}
        	}
        	 initTable();
        }
        
    }

	private User getSelectedUser() {
		// get the index of the selected row
		int selectedRow = userTable.getSelectedRow();
		
		// if the user clicked ok without selecting anything
		if (selectedRow == -1) {
		    JOptionPane.showMessageDialog(this, "Select a user, please",
		            "Error", JOptionPane.INFORMATION_MESSAGE);
		    return null;
		}
		// get the user from the table
		User user = ((UserTableModel) userTable.getModel()).getUserByRow(selectedRow);
		// hide browse panel
		return user;
	}
    

    
}
