package com.anamakarevich.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {
    
    private MainFrame frame;
    private JPanel buttonPanel;
    private JScrollPane tablePanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JTable userTable;

    public BrowsePanel(MainFrame frame) {
        this.frame = frame;
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
            buttonPanel.add(getAddButton(),null);
            buttonPanel.add(getEditButton(),null);
            buttonPanel.add(getDeleteButton(),null);
            buttonPanel.add(getDetailsButton(),null);
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
            // TODO: Remove null
            UserTableModel model = new UserTableModel(new ArrayList());
            userTable.setModel(model);
        }
        return userTable;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        // check if the add button was clicked
        if ("add".equalsIgnoreCase(actionCommand)){ //$NON-NLS-1$
            this.setVisible(false);
            frame.showAddPanel();
            
        }
        
    }

    
}
