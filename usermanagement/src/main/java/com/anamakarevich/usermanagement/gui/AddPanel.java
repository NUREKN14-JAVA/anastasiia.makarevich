package com.anamakarevich.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPanel extends JPanel implements ActionListener {

    private MainFrame frame;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    
    
    public AddPanel(MainFrame frame) {
        this.frame = frame;
        initialize();
    }

    /**
     * Set up the buttons panel and the input fields
     */
    private void initialize() {
        setLayout(new BorderLayout());
        this.setName("addPanel"); 
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Gets or creates buttons panel
     * @return 
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton());
            buttonPanel.add(getCancelButton());
        }
        return buttonPanel;
    }
    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText("Cancel");
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText("Add");
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }
    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            // create grid layout with 3 rows and 2 columns
            fieldPanel.setLayout(new GridLayout(3,2));
            // add input fields
            addLabeledField(fieldPanel, "First Name",getFirstNameField());
            addLabeledField(fieldPanel, "Last Name",getLastNameField());
            addLabeledField(fieldPanel, "Date of Birth",getDateOfBirthField());
        }
        return fieldPanel;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
