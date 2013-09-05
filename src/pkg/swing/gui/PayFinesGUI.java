/**
 * 
 */
package pkg.swing.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import pkg.abstractFactory.LibraryItems;
import pkg.abstractFactory.LibraryMember;	
import pkg.database.Transaction;

/**
 * @author redokani
 *
 */
public class PayFinesGUI extends LoggedInUserPageGUI{

	public enum Actions {
		back,
		payFines,
		signout
	}

	public PayFinesGUI(String loginMember) {
		super(loginMember);
		// TODO Auto-generated constructor stub
	}
	
	public PayFinesGUI() {
		super();
		// TODO Auto-generated constructor stub
	}

	static PayFinesGUI payFineGui = null;
	JPanel welcomePanel, returnItemResultPanel, buttonPanel, statusPanel;
	JLabel welcomeLabel, returnResult;
	//JTextField searchField;
	JTextArea textArea;
	JButton back, signout, payFine;
	JTable resultTable;
	JScrollPane scrollPane;

	public static PayFinesGUI getLibraryMember(String loginMember){
		if(payFineGui == null)
			payFineGui = new PayFinesGUI(loginMember);
		return payFineGui;
	}


	public void start(List<Transaction> returnList){

		welcomePanel = new JPanel(new FlowLayout());
		signout = new JButton("Sign Out");
		back = new JButton("Go Back");
		
		welcomePanel.add(back);
		welcomePanel.add(new JLabel("Welcome:\t" + getLoginMember()));
		welcomePanel.add(signout);

		welcomePanel.setBorder(new EtchedBorder());

		returnItemResultPanel = new JPanel();
		
		String columnNames[] = { "Item No", "Title", "Due Date", "Fine Amount"};
		
		
		scrollPane = new JScrollPane( resultTable );
		
		String[][] dataArray = new String[returnList.size()][5];
		
		for(int i =0 ; i< returnList.size(); i++){
			for(int j=0; j< 5; j++)
				if(j == 0)
					dataArray[i][j] = returnList.get(i).getItemId();
				else if(j == 1)
					dataArray[i][j] = returnList.get(i).getItemTitle();
				else if(j == 2)
					dataArray[i][j] = returnList.get(i).getDueDate().toString();
				else if(j == 3)
					dataArray[i][j] = returnList.get(i).getFineAmountDue()+"";
				
		}
		
		//resultTable = new JTable(dataArray, columnNames);
        
        DefaultTableModel model = new DefaultTableModel(dataArray,columnNames);
        resultTable = new JTable(model);
        JScrollPane sp = new JScrollPane(resultTable);

		returnItemResultPanel.add(resultTable);
		returnItemResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Items To Be Returned:"));
		
		
		buttonPanel = new JPanel(new FlowLayout());
		payFine = new JButton("PAY FINE");
//		reserve = new JButton("RESERVE");
//		pay = new JButton("PAY");
//		query = new JButton("QUERY TRANSACTION");
		buttonPanel.add(payFine);
//		buttonPanel.add(reserve);
//		buttonPanel.add(pay);
//		buttonPanel.add(query);
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Return Item Action:"));

		statusPanel = new JPanel(new FlowLayout());
		textArea = new JTextArea("STATUS");
		textArea.setPreferredSize(new Dimension(500,100));
		statusPanel.add(textArea);
		statusPanel.setBorder(new TitledBorder(new EtchedBorder(), "ACTIONS:"));

		payFine.setActionCommand(Actions.payFines.name());
//		reserve.setActionCommand(Actions.reserve.name());
//		pay.setActionCommand(Actions.pay.name());
//		query.setActionCommand(Actions.query.name());
		signout.setActionCommand(Actions.signout.name());
		back.setActionCommand(Actions.back.name());
		//returnItem.setActionCommand(Actions.signout.name());


//		checkout.addActionListener(this);
//		reserve.addActionListener(this);
//		query.addActionListener(this);
//		pay.addActionListener(this);
		signout.addActionListener(this);
		back.addActionListener(this);
		payFine.addActionListener(this);
	}	


	@Override
	public void showWelcomeMessage(String loginMessage) {
		// TODO Auto-generated method stub

	}
	@Override
	public void performLoginActions() {
		// TODO Auto-generated method stub

	}

	LibraryMember libMemberObj = new LibraryMember(getLoginMember());

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == Actions.payFines.name()) {
			LibraryMember member = new LibraryMember(getLoginMember());
			member.payFine();
			LoginDemo.frame.getContentPane().removeAll();
			setVisible(false);
			CCPaymentGui ccinfo = CCPaymentGui.CCPaymentGui();
			ccinfo.start(1, getLoginMember());

	    	ArrayList<JPanel> ccPaymentPanels = ccinfo.getCCScreen();

	    	LoginDemo.frame.add(ccPaymentPanels.get(0), BorderLayout.NORTH);
	    	LoginDemo.frame.add(ccPaymentPanels.get(1), BorderLayout.CENTER);
	    	LoginDemo.frame.add(ccPaymentPanels.get(2), BorderLayout.SOUTH);
	    	LoginDemo.frame.invalidate();
	    	LoginDemo.frame.validate();
	    	
	    	setSize(new Dimension(500, 500));
			setVisible(true);

		}else if (e.getActionCommand() == Actions.back.name()) {
			LibraryMemberGUI user = LibraryMemberGUI.getLibraryMember(getLoginMember());
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
		}else if (e.getActionCommand() == Actions.signout.name()) {
			LoginDemo.frame.getContentPane().removeAll();
	    	setVisible(false);
	    	LoginDemo.start();   	
	    	setVisible(true);
		}
	}

	public ArrayList<JPanel> getPayFinesResPanels() {
		ArrayList<JPanel> libMemPanels = new ArrayList<JPanel>();
		libMemPanels.add(welcomePanel);
		libMemPanels.add(returnItemResultPanel);
		libMemPanels.add(buttonPanel);
		libMemPanels.add(statusPanel);
		return libMemPanels;
	}
}




