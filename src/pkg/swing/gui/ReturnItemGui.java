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
import javax.swing.JOptionPane;
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
public class ReturnItemGui extends LoggedInUserPageGUI{

	public enum Actions {
		back,
		returnItem,
		signout
	}

	public ReturnItemGui(String loginMember) {
		super(loginMember);
		// TODO Auto-generated constructor stub
	}
	
	public ReturnItemGui() {
		super();
		// TODO Auto-generated constructor stub
	}

	static ReturnItemGui returnItemGui = null;
	JPanel welcomePanel, returnItemResultPanel, returnItemPanel, statusPanel;
	JLabel welcomeLabel, returnResult;
	JTextField itemsToBeReturned;
	JTextArea textArea;
	JButton back, signout, returnItem;
	JTable resultTable;
	JScrollPane scrollPane;

	public static ReturnItemGui getLibraryMember(String loginMember){
		if(returnItemGui == null)
			returnItemGui = new ReturnItemGui(loginMember);
		return returnItemGui;
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
		        
        DefaultTableModel model = new DefaultTableModel(dataArray,columnNames);
        resultTable = new JTable(model);
        JScrollPane sp = new JScrollPane(resultTable);

		returnItemResultPanel.add(resultTable);
		returnItemResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Items To Be Returned:"));
		
		returnItemPanel = new JPanel(new FlowLayout());
		itemsToBeReturned = new JTextField(50);
		returnItem = new JButton("RETURN ITEMS");

		returnItemPanel.add(itemsToBeReturned);
		returnItemPanel.add(returnItem);

		returnItemPanel.setBorder(new TitledBorder(new EtchedBorder(), "Return Item Action:"));

		statusPanel = new JPanel(new FlowLayout());
		textArea = new JTextArea("STATUS");
		textArea.setPreferredSize(new Dimension(500,100));
		statusPanel.add(textArea);
		statusPanel.setBorder(new TitledBorder(new EtchedBorder(), "ACTIONS:"));

		returnItem.setActionCommand(Actions.returnItem.name());

		signout.setActionCommand(Actions.signout.name());
		back.setActionCommand(Actions.back.name());

		signout.addActionListener(this);
		back.addActionListener(this);
		returnItem.addActionListener(this);
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
		if (e.getActionCommand() == Actions.returnItem.name()) {
			LibraryMember member = new LibraryMember(getLoginMember());
			String[] items = itemsToBeReturned.getText().split(",");
			try{
				for(int i=0 ; i< items.length ; i++)
					member.returnItems(items[i]);
				JOptionPane.showMessageDialog(this,"All Books have been returned Successfully. Thank You.",
						"Info",JOptionPane.INFORMATION_MESSAGE);
				System.out.println("All Books have been returned Successfully. Thank You.");
			}catch(Exception e1){
				JOptionPane.showMessageDialog(this,"Some Books could not be returned. Please try again later.",
						"Info",JOptionPane.ERROR_MESSAGE);
				System.out.println("Some Books could not be returned. Please try again later.");
				e1.printStackTrace();
			}

			LibraryMemberGUI user = LibraryMemberGUI.getLibraryMember(getLoginMember());
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

	public ArrayList<JPanel> getReturnItemResPanels() {
		ArrayList<JPanel> libMemPanels = new ArrayList<JPanel>();
		libMemPanels.add(welcomePanel);
		libMemPanels.add(returnItemResultPanel);
		libMemPanels.add(returnItemPanel);
		libMemPanels.add(statusPanel);
		return libMemPanels;
	}
}




