package com.anamakarevich.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.anamakarevich.usermanagement.util.Messages;

public class MainFrame extends JFrame {
    
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;

    private JPanel contentPanel;
    private JPanel browsePanel;
    private AddPanel addPanel;
    
    
    public MainFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
        this.setContentPane(getContentPanel());
    }
    
    JPanel getContentPanel() {
        // check if the panel already exists
        if  (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
        
    }
    
    private JPanel getBrowsePanel() {
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
            // TODO: decide where to put the browse panel
        }
        return browsePanel;
    }

    public static void main(String[] args) {
        
        // create and show the main window
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true); 
        panel.repaint();
        
    }

    private JPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

}
