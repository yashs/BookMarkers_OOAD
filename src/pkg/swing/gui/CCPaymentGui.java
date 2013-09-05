package pkg.swing.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CCPaymentGui extends JPanel implements ActionListener {

	static CCPaymentGui ccpayment = null;
	JPanel banner, ccInfo, payButton;
	JLabel cardType, cardNumber;
	JTextField text1, text2;
	JButton PAY;
	int paymentCode = 0;
	String loginName;

	public static CCPaymentGui CCPaymentGui(){
		if(ccpayment == null)
			ccpayment = new CCPaymentGui();
		return ccpayment;
	}

	public void start(int paymentCode, String loginName){
		this.paymentCode = paymentCode;
		banner = new JPanel();
		banner.add(new JLabel("Please Enter Your Card Details for Payment Processing:"));


		ccInfo=new JPanel(new FlowLayout());

		cardType = new JLabel();
		cardType.setText("Card Type::");

		text1 = new JTextField(15);
		text1.setSize(20, 5);

		cardNumber = new JLabel();
		cardNumber.setText("Card Number:");
		text2 = new JTextField(15);

		ccInfo.add(cardType);
		ccInfo.add(text1);
		ccInfo.add(cardNumber);
		ccInfo.add(text2);

		payButton=new JPanel(new FlowLayout());

		PAY=new JButton("PAY");
		payButton.add(PAY);

		PAY.addActionListener(this);


	}	

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(paymentCode == 0){
			System.out.println("Payment Successful. Please activate your account by geting confimation code from your email/SMS.");
			JOptionPane.showMessageDialog(this,"Payment Successful. Confirmation code sent to your email/phone. Please login again.",
					"Info",JOptionPane.INFORMATION_MESSAGE);	
			LoginDemo.frame.getContentPane().removeAll();

			setVisible(false);
			LoginDemo.start();
			setVisible(true);
		}else if(paymentCode == 1){
			System.out.println("Payment Successful. Your Fines have been paid successfully.");
			JOptionPane.showMessageDialog(this,"Payment Successful. Your Fines have been paid successfully.",
					"Info",JOptionPane.INFORMATION_MESSAGE);
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
			panel.add(libMemPanels.get(4));
			
			
			LoginDemo.frame.getContentPane().add(panel, BorderLayout.SOUTH);

			LoginDemo.frame.setSize(800, 600);
			LoginDemo.frame.invalidate();
			LoginDemo.frame.validate();
			setVisible(true);
		}
		
	}

	public ArrayList<JPanel> getCCScreen() {

		ArrayList<JPanel> ccPanels = new ArrayList<JPanel>();
		ccPanels.add(banner);
		ccPanels.add(ccInfo);
		ccPanels.add(payButton);

		return ccPanels;
	}

}
