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
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("SIGN OUT")){
			LoginDemo.frame.getContentPane().removeAll();
	    	setVisible(false);
	    	
	    	LoginDemo.start();
	    	
	    	setVisible(true);

		}else{
			String loginName=text1.getText();
			String password=text2.getText();
			
			LoginUser member = new LoginUser(loginName, password);			
			String status = member.allowLogin();
			
			if (status.startsWith("AUTHENTICATED")) {
				status = status.replace("AUTHENTICATED", "");
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
				LoginDemo.frame.setVisible(true);
			}
			else{
				System.out.println("enter the valid username and password");
				JOptionPane.showMessageDialog(this,"Incorrect login or password",
						"Error",JOptionPane.ERROR_MESSAGE);
			}
			
//			PersistanceActions checkUserAuth = new PersistanceActions();
			/*Database database= new Database();
			Connection connection = null;
			try {
				connection = database.Get_Connection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String status="";
			try {
				status = checkUserAuth.userAuthenticate(connection, loginName, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if (status.startsWith("AUTHENTICATED")) {
				status = status.replace("AUTHENTICATED", "");
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
				LoginDemo.frame.setVisible(true);
			}
			else{
				System.out.println("enter the valid username and password");
				JOptionPane.showMessageDialog(this,"Incorrect login or password",
						"Error",JOptionPane.ERROR_MESSAGE);
			}*/
		}
	}

	public ArrayList<JPanel> getLoginScreen(){


		ArrayList<JPanel> loginScreenPanels = new ArrayList<JPanel>();
		loginScreenPanels.add(banner);
		loginScreenPanels.add(panel);

		return loginScreenPanels;
	}


}
