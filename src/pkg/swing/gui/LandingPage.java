package pkg.swing.gui;
import javax.swing.*;

import pkg.abstractFactory.LibraryItems;
import pkg.abstractFactory.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;



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
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		pack();
	}
	
	 @Override
	  public void actionPerformed(ActionEvent evt) {
	    if (evt.getActionCommand() == Actions.SEARCH.name()) {
	      // DO DB SEARCH OPERATION
	    	User guestUser = new User();
	    	List<LibraryItems> returnList= new ArrayList<LibraryItems>(); 
			returnList = guestUser.searchItems(searchText.getText());
	      
			SearchResultGUI res = new SearchResultGUI();
			res.start(returnList);
			ArrayList<JPanel> searchResPanels = res.getSearchResPanels();
			LoginDemo.frame.getContentPane().removeAll();
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0,1));
			panel.add(searchResPanels.get(0));
			panel.add(searchResPanels.get(1));
			//panel.add(searchResPanels.get(2));
			panel.add(searchResPanels.get(3));
			
			
			LoginDemo.frame.getContentPane().add(panel, BorderLayout.SOUTH);

			LoginDemo.frame.setSize(800, 450);
			LoginDemo.frame.invalidate();
			LoginDemo.frame.validate();
			setVisible(true);
			
	      
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
	    	
	    	RegistrationPageGUI signupScreen = new RegistrationPageGUI();
	    	signupScreenPanels = signupScreen.getRegistrationPanels();
	    	
	    	LoginDemo.frame.add(signupScreenPanels.get(0), BorderLayout.NORTH);
	    	LoginDemo.frame.add(signupScreenPanels.get(1), BorderLayout.CENTER);
	    	LoginDemo.frame.add(signupScreenPanels.get(2), BorderLayout.SOUTH);
	    	setSize(new Dimension(1000, 1000));
	    	setVisible(true);
		    }
	  }
}
