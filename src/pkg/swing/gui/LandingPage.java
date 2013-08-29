package pkg.swing.gui;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;



class LandingPage extends JFrame implements ActionListener{
	
	public enum Actions {
	    SEARCH,
	    SIGNIN,
	    SIGNUP
	  }
	
	
	
	JButton SEARCH, SIGNIN, SIGNUP;
	JPanel banner, search, signOptions;
	//JLabel label1,label2;
	JTextField  searchText;
	LandingPage()
	{
		
	}
	
	
	public void start(){
		setDefaultCloseOperation(javax.swing.
				WindowConstants.DISPOSE_ON_CLOSE);
		banner = new JPanel();
		banner.add(new JLabel("WELCOME TO BOOKMARKERS LIBRARY"));
		
		search = new JPanel(new FlowLayout());
		searchText = new JTextField(50);
		SEARCH = new JButton("SEARCH");
		
		search.add(searchText);
		search.add(SEARCH);
		
		signOptions = new JPanel(new FlowLayout());
		SIGNIN = new JButton("SIGN IN");
		SIGNUP = new JButton("SIGN UP");
		
		signOptions.add(SIGNIN);
		signOptions.add(SIGNUP);
		
		add(banner,BorderLayout.NORTH);
		add(search,BorderLayout.CENTER);
		add(signOptions,BorderLayout.SOUTH);
		
		
		SEARCH.setActionCommand(Actions.SEARCH.name());
		SIGNIN.setActionCommand(Actions.SIGNIN.name());
		SIGNUP.setActionCommand(Actions.SIGNUP.name());
				
		SEARCH.addActionListener(this);
		SIGNIN.addActionListener(this);
		SIGNUP.addActionListener(this);
		
		
		//SUBMIT.addActionListener(this);
		setTitle("WELCOME TO BOOKMARKERS");
		setVisible(true);
	}
	
	 @Override
	  public void actionPerformed(ActionEvent evt) {
	    if (evt.getActionCommand() == Actions.SEARCH.name()) {
	      // DO DB SEARCH OPERATION
	    	
	      
	      
	    }else if (evt.getActionCommand() == Actions.SIGNIN.name()) {
	    	LoginDemo.frame.getContentPane().removeAll();
	    	setVisible(false);
	    	
	    	Login loginScreen = Login.getLogin();
	    	loginScreen.start();
	    	ArrayList<JPanel> loginScreenPanels = loginScreen.getLoginScreen();
	    	
	    	LoginDemo.frame.add(loginScreenPanels.get(0), BorderLayout.NORTH);
	    	LoginDemo.frame.add(loginScreenPanels.get(1), BorderLayout.SOUTH);
	    	setVisible(true);
	    }else if (evt.getActionCommand() == Actions.SIGNUP.name()) {
		    // Do SIGN UP Registration
	    	LoginDemo.frame.getContentPane().removeAll();
	    	setVisible(false);
	    	
	    	ArrayList<JPanel> signupScreenPanels = new ArrayList<JPanel>();
	    	
	    	Signup signupScreen = new Signup();
	    	signupScreenPanels = signupScreen.getRegistrationPanels();
	    	
	    	LoginDemo.frame.add(signupScreenPanels.get(0), BorderLayout.NORTH);
	    	LoginDemo.frame.add(signupScreenPanels.get(1), BorderLayout.SOUTH);
	    	setVisible(true);
		    }
	  }
}
