package com.anamakarevich.usermanagement.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.anamakarevich.usermanagement.db.DaoFactory;
import com.anamakarevich.usermanagement.db.UserDao;
import com.anamakarevich.usermanagement.util.Messages;

public class MainFrame extends JFrame {

  
    private static final long serialVersionUID = 1L;
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;

    private JPanel contentPanel;
    private BrowsePanel browsePanel;
    private AddPanel addPanel;
    private UserDao dao;
    private EditPanel editPanel;
    
    public MainFrame() {
    
        super();
        dao = DaoFactory.getInstance().getUserDao();
        initialize();

    }

    // sets up the main window
    private void initialize() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
        this.setContentPane(getContentPanel());
    }
    
    public static void main(String[] args) {
        
        // create and show the main window
    	MainFrame frame = new MainFrame();
    	SwingUtilities.invokeLater(
    			new Runnable() {
    				public void run() {
    				     frame.setVisible(true);
    				}
    			});
    }
 
    /**
     * Shows the panel in the centre of the content pane
     * @param panel
     */
    private void showPanel(JPanel panel) {
    	getContentPane().add(panel, BorderLayout.CENTER);
    	SwingUtilities.invokeLater(
    	new Runnable() {
    		public void run() {
    	        panel.setVisible(true); 
    	        panel.repaint();
    		}
    	});
        
    }
    
    private JPanel getContentPanel() {
        // check if the panel already exists
        if  (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }
    
    private BrowsePanel getBrowsePanel() {
    	// check if the panel already exists
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel) browsePanel).initTable();
        return browsePanel;
    }

    private AddPanel getAddPanel() {
    	// check if the panel already exists
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }    

    private EditPanel getEditPanel() {
    	// check if the panel already exists
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        return editPanel;
    }
    
    public void showAddPanel() {
        showPanel(getAddPanel());
    }
    
    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public UserDao getDao() {
        return dao;
    }

    public void showEditPanel() {
        showPanel(getEditPanel());
    }


}
