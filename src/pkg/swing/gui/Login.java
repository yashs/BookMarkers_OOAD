package pkg.swing.gui;
import javax.swing.*;

import pkg.database.Database;
import pkg.database.PersistanceActions;
import pkg.misc.LoginUser;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;

class Login extends JPanel implements ActionListener
{
	JButton SUBMIT, SIGNOUT;
	JPanel panel;
	JPanel banner;
	JLabel label1,label2;
	JTextField  text1,text2;
	static Login login = null;

	public static Login getLogin(){
		if(login == null)
			login = new Login();
		return login;
	}
	
	public void start(){
		
		LoginDemo.frame.getContentPane().removeAll();
		setVisible(false);
		
		banner = new JPanel();
		banner.add(new JLabel("WELCOME TO BOOKMARKERS LIBRARY"));
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);
		text1.setSize(20, 5);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		SUBMIT=new JButton("SUBMIT");

		panel=new JPanel(new FlowLayout());
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(SUBMIT);
		LoginDemo.frame.add(banner,BorderLayout.NORTH);
		LoginDemo.frame.add(panel,BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		setVisible(false);
	}
	
	LoginUser member = null;
	JTextField confirmationCode = new JTextField(20);
	String loginName = "";
	String password = "";
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("SUBMIT1")){		
			boolean validation = member.validateConfirmationCode(confirmationCode.getText());
			
			if(validation){
				JOptionPane.showMessageDialog(this,"Valid Confirmation code. You have been validated. To complete validation, please login again.",
						"Info",JOptionPane.INFORMATION_MESSAGE);
				LoginDemo.frame.getContentPane().removeAll();
		    	setVisible(false);   	
		    	LoginDemo.start();
		    	setVisible(true);
			}else{
				JOptionPane.showMessageDialog(this,"InValid Confirmation code. You have been not been validated. To complete validation, please login and try again.",
						"Info",JOptionPane.ERROR_MESSAGE);
				loginActions("Account Not Activated");				
			}

		}
		else if(ae.getActionCommand().equals("SIGN OUT")){
			LoginDemo.frame.getContentPane().removeAll();
	    	setVisible(false);
	    	LoginDemo.start();   	
	    	setVisible(true);

		}else{
			loginName=text1.getText();
			password=text2.getText();
			
			member = new LoginUser(loginName, password);			
			String status = member.allowLogin();		
			loginActions(status);
		}
	}

	
	private void loginActions(String status) {
		if (status.startsWith("AUTHENTICATED")) {
			
			boolean admin = member.checkifAdmin(loginName);
			if(admin){
				
			}else{
				LibraryMemberGUI user = LibraryMemberGUI.getLibraryMember(loginName);
				user.start();
				ArrayList<JPanel> libMemPanels = user.getLibraryMemberpanels();
				LoginDemo.frame.getContentPane().removeAll();
				
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(0,1));
				panel.add(libMemPanels.get(0));
				panel.add(libMemPanels.get(1));
				panel.add(libMemPanels.get(2));
				panel.add(libMemPanels.get(3));
				
				
				LoginDemo.frame.getContentPane().add(panel, BorderLayout.SOUTH);

				LoginDemo.frame.setSize(800, 600);
				LoginDemo.frame.invalidate();
				LoginDemo.frame.validate();
				setVisible(true);
				
			}
			
			
			
			/*status = status.replace("AUTHENTICATED", "");
			JPanel pan = new JPanel(new FlowLayout());
			JPanel housekeeping = new JPanel();
			JLabel label = new JLabel("Welcome:"+status + "\n You have been authenticated");
			SIGNOUT = new JButton("SIGN OUT");
			pan.add(label);
			pan.add(SIGNOUT);

			SIGNOUT.setActionCommand("SIGN OUT");
			SIGNOUT.addActionListener(this);

			

			LoginDemo.frame.getContentPane().removeAll();
			LoginDemo.frame.getContentPane().add(housekeeping,BorderLayout.NORTH);
			LoginDemo.frame.getContentPane().add(pan,BorderLayout.CENTER);
			LoginDemo.frame.setVisible(true);*/
		}else if(status.startsWith("Account Not Activated")){
			JPanel pan = new JPanel(new FlowLayout());
			JLabel label = new JLabel("Please Enter The Confirmation Code:\t");
			//confirmationCode = new JTextField(20);
			//confirmationCode.setText(t)
			SUBMIT = new JButton("SUBMIT");
			pan.add(label);
			pan.add(confirmationCode);
			pan.add(SUBMIT);
			
			SUBMIT.setActionCommand("SUBMIT1");
			SUBMIT.addActionListener(this);
			
			LoginDemo.frame.getContentPane().removeAll();
			//LoginDemo.frame.getContentPane().add(housekeeping,BorderLayout.NORTH);
			LoginDemo.frame.getContentPane().add(pan,BorderLayout.CENTER);
			LoginDemo.frame.invalidate();
	    	LoginDemo.frame.validate();
			LoginDemo.frame.setVisible(true);
			
		}
		else{
			System.out.println("enter the valid username and password");
			JOptionPane.showMessageDialog(this,"Incorrect login or password",
					"Error",JOptionPane.ERROR_MESSAGE);
		}		
	}

	public ArrayList<JPanel> getLoginScreen(){


		ArrayList<JPanel> loginScreenPanels = new ArrayList<JPanel>();
		loginScreenPanels.add(banner);
		loginScreenPanels.add(panel);

		return loginScreenPanels;
	}


}
