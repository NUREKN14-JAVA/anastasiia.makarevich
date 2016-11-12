package com.anamakarevich.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.anamakarevich.usermanagement.User;
import com.anamakarevich.usermanagement.db.DatabaseException;
import com.anamakarevich.usermanagement.util.Messages;

public class AddPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private MainFrame parent;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private Color bgColor = Color.WHITE;
    
    /**
     * Constructor for add panel
     * @param frame - the parent frame to 'host' this panel
     */
    public AddPanel(MainFrame frame) {
        parent = frame;
        initialize();
    }
    
    protected MainFrame getParentFrame() {
    	return parent;
    }
    
     // Set up the buttons panel and the input fields
    private void initialize() {
        this.setName("addPanel");  //$NON-NLS-1$
        setLayout(new BorderLayout());
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
            cancelButton.setText(Messages.getString("AddPanel.cancel")); //$NON-NLS-1$
            cancelButton.setName("cancelButton"); //$NON-NLS-1$
            cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(Messages.getString("AddPanel.ok")); //$NON-NLS-1$
            okButton.setName("okButton"); //$NON-NLS-1$
            okButton.setActionCommand("ok"); //$NON-NLS-1$
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
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"),getFirstNameField()); //$NON-NLS-1$
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"),getLastNameField()); //$NON-NLS-1$
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"),getDateOfBirthField()); //$NON-NLS-1$
        }
        return fieldPanel;
    }

    protected JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField"); 
        }
        return dateOfBirthField;
    }

    protected JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField"); 
        }
        return lastNameField;
    }

    protected JTextField getFirstNameField() {
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
        if ("ok".equals(e.getActionCommand())) { 
            User user = new User();
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                user.setDateOfBirthd(LocalDate.parse(getDateOfBirthField().getText(),formatter));
            }
            catch (DateTimeParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().create(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, 
                        e1.getMessage(), 
                        Messages.getString("AddPanel.error"),  //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    protected void clearFields() {
        getFirstNameField().setText(""); //$NON-NLS-1$
        getFirstNameField().setBackground(bgColor);
        
        getLastNameField().setText(""); //$NON-NLS-1$
        getLastNameField().setBackground(bgColor);

        getDateOfBirthField().setText(""); //$NON-NLS-1$
        getDateOfBirthField().setBackground(bgColor);
        
    }
}
