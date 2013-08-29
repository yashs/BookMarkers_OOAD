package pkg.swing.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import pkg.swing.utils.SpringUtilities;


public class Signup extends JPanel implements ActionListener {
	private enum Actions {
	    REGISTER,
	    CANCEL
	  }
	
	static JButton REGISTER, CANCEL;
	static JPanel buttonActions, registrationPanel;
	

	public ArrayList<JPanel> getRegistrationPanels() {
		ArrayList<JPanel> signupPanels = new ArrayList<JPanel>();
		
		String[] labels = {"Name: ", "LoginName: ", "Email: ", "Phone:", "Address: "};
	    int numPairs = labels.length;

	    //Create and populate the panel.
	    registrationPanel = new JPanel(new SpringLayout());
	    for (int i = 0; i < numPairs; i++) {
	        JLabel l = new JLabel(labels[i], JLabel.TRAILING);
	        registrationPanel.add(l);
	        JTextField textField = new JTextField(10);
	        l.setLabelFor(textField);
	        registrationPanel.add(textField);
	    }
	   	    
	    SpringUtilities.makeCompactGrid(registrationPanel,
	                                    numPairs, 2, //rows, cols
	                                    6, 6,        //initX, initY
	                                    6, 6);       //xPad, yPad
	    
	    registrationPanel.setVisible(true);  
		
	    buttonActions = new JPanel(new FlowLayout());  
	    
	    REGISTER = new JButton("REGISTER");
	    CANCEL = new JButton("CANCEL");
	 
	    buttonActions.add(REGISTER);
	    buttonActions.add(CANCEL);
	    
	    REGISTER.setActionCommand(Actions.REGISTER.name());
	    CANCEL.setActionCommand(Actions.CANCEL.name());

	    REGISTER.addActionListener(this);
	    CANCEL.addActionListener(this);   
		
		buttonActions.setVisible(true);
		signupPanels.add(registrationPanel);
		signupPanels.add(buttonActions);	 
		

	    return signupPanels;
	}


	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == Actions.REGISTER.name()) {
		      // DO DB PERSIST OPERATION
		    	
		      
		      
		    }else if (evt.getActionCommand() == Actions.CANCEL.name()) {
		    	LoginDemo.frame.getContentPane().removeAll();
		    	setVisible(false);
		    	LoginDemo.start();
		    	setVisible(true);
		    }
		
	}
}