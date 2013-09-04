package pkg.swing.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import pkg.subscription.MemberRegistration;
import pkg.swing.utils.SpringUtilities;


public class RegistrationPageGUI extends JPanel implements ActionListener {
	private enum Actions {
		REGISTER,
		CANCEL
	}

	static JButton REGISTER, CANCEL;
	static JPanel buttonActions, registrationPanel, statusPanel;
	static JTextArea statusTextArea;
	MemberRegistration memberReg = null;
	JTextField [] inputs = null;

	public ArrayList<JPanel> getRegistrationPanels() {
		ArrayList<JPanel> signupPanels = new ArrayList<JPanel>();



		String[] labels = {"Name: ", "LoginName: ", "Password:", "Confirm Password:", "Email: ", "Phone:", "Address: "};
		int numPairs = labels.length;

		inputs = new JTextField[numPairs];

		//Create and populate the panel.
		registrationPanel = new JPanel(new SpringLayout());
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			registrationPanel.add(l);
			JTextField textField = new JTextField(10);;
			if(l.getText().equalsIgnoreCase("Password:") || l.getText().equalsIgnoreCase("Confirm Password:"))
				textField = new JPasswordField(10);
			else
				textField = new JTextField(10);
			inputs[i] = textField;

			l.setLabelFor(textField);
			registrationPanel.add(textField);
		}






		memberReg = new MemberRegistration();



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

		statusPanel = new JPanel(new FlowLayout());

		statusTextArea = new JTextArea("Email address is required to send reservation notifications.");

		statusTextArea.setPreferredSize(new Dimension(500,100));
		statusPanel.add(statusTextArea);

		signupPanels.add(registrationPanel);
		signupPanels.add(buttonActions);	 
		signupPanels.add(statusPanel);

		return signupPanels;
	}


	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getActionCommand() == Actions.REGISTER.name()) {
			// DO DB PERSIST OPERATION
			boolean flag = false;
			if(inputs[0].getText().equals("") || inputs[1].getText().equals("") || inputs[2].getText().equals("") || inputs[6].getText().equals("")){
				System.out.println("Name, UserName, Password and Address cannot be blank");
				JOptionPane.showMessageDialog(this,"Name, UserName, Password and Address cannot be blank",
						"Error",JOptionPane.ERROR_MESSAGE);
				flag = true;
			}
			else if(memberReg.checkDuplicateLoginName(inputs[1].getText())){
				System.out.println("Duplicate UserNAME");
				JOptionPane.showMessageDialog(this,"Duplicate UserNAME",
						"Error",JOptionPane.ERROR_MESSAGE);
				flag = true;
			}

			else if(!inputs[2].getText().equals((inputs)[3].getText())){
				System.out.println("Password and Confirm Password Do not Match");
				JOptionPane.showMessageDialog(this,"Password and Confirm Password Do not Match",
						"Error",JOptionPane.ERROR_MESSAGE);
				flag = true;
			}
			else if(!inputs[4].getText().equals("")) {
				if(!memberReg.isValidEmailAddress(inputs[4].getText())){
					System.out.println("Not a Valid Email Address. Please Enter again");
					JOptionPane.showMessageDialog(this,"Not a Valid Email Address. Please Enter again",
							"Error",JOptionPane.ERROR_MESSAGE);
					flag = true;
				}
			}

			else if(inputs[4].getText().equals("") && inputs[5].getText().equals("")){
				System.out.println("Either an email address or phone number is required");
				JOptionPane.showMessageDialog(this,"Either an email address or phone number is required",
						"Error",JOptionPane.ERROR_MESSAGE);
				flag = true;
			}

			else if(inputs[4].getText().equals("")){
				if(inputs[5].getText().length() > 0 && inputs[5].getText().length() !=10){
					System.out.println("Please enter a valid 10 digit phone number without hyphens");
					JOptionPane.showMessageDialog(this,"Please enter a valid 10 digit phone number without hyphens",
							"Error",JOptionPane.ERROR_MESSAGE);
					flag = true;
				}else{
					try{
						Long.parseLong(inputs[5].getText());
					}catch(Exception e){
						System.out.println("Please enter a valid 10 digit phone number without hyphens");
						JOptionPane.showMessageDialog(this,"Please enter a valid 10 digit phone number without hyphens",
								"Error",JOptionPane.ERROR_MESSAGE);
						flag = true;
					}
				}
			}
			
			
			if(!flag){
				memberReg.setLoginName(inputs[1].getText());
				memberReg.setPassWord(inputs[2].getText());
				memberReg.setFullName(inputs[0].getText());
				memberReg.setEmailID(inputs[4].getText());
				
				if(inputs[5].getText().length() == 10)
					memberReg.setPhoneNumber(Long.parseLong(inputs[5].getText()));
				memberReg.setStartDate(new Timestamp(System.currentTimeMillis()));
				Random generator = new Random();
				memberReg.setCardNumber(generator.nextInt(400));
				memberReg.setAddress(inputs[6].getText());
				if(!inputs[6].getText().equalsIgnoreCase("Spingfield")){
					LoginDemo.frame.getContentPane().removeAll();
					setVisible(false);
					CCPaymentGui ccinfo = CCPaymentGui.CCPaymentGui();
					ccinfo.start();

			    	ArrayList<JPanel> ccPaymentPanels = ccinfo.getCCScreen();

			    	LoginDemo.frame.add(ccPaymentPanels.get(0), BorderLayout.NORTH);
			    	LoginDemo.frame.add(ccPaymentPanels.get(1), BorderLayout.CENTER);
			    	LoginDemo.frame.add(ccPaymentPanels.get(2), BorderLayout.SOUTH);
			    	LoginDemo.frame.invalidate();
			    	LoginDemo.frame.validate();
			    	
			    	setSize(new Dimension(500, 500));
					setVisible(true);
				}else{
					JOptionPane.showMessageDialog(this,"Confirmation code sent to your email/phone. Please login again.",
							"Info",JOptionPane.INFORMATION_MESSAGE);
					LoginDemo.frame.getContentPane().removeAll();
					
					setVisible(false);
					LoginDemo.start();
					setVisible(true);
				}
				memberReg.setMemberDetailsInDB(memberReg);
			}

		}else if (evt.getActionCommand() == Actions.CANCEL.name()) {
			LoginDemo.frame.getContentPane().removeAll();
			setVisible(false);
			LoginDemo.start();
			setVisible(true);
		}

	}
}