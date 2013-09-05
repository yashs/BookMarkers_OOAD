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

import javax.swing.BoxLayout;
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
public class SearchResultGUI extends LoggedInUserPageGUI{

	public enum Actions {
		SEARCH,
		checkout,
		reserve,
		pay,
		query,
		back,
		guestBack,
		signout
	}

	public SearchResultGUI(String loginMember) {
		super(loginMember);
		// TODO Auto-generated constructor stub
	}
	
	public SearchResultGUI() {
		super();
		// TODO Auto-generated constructor stub
	}

	static SearchResultGUI searchRes = null;
	JPanel welcomePanel, searchResultPanel, buttonPanel, statusPanel, itemActionFieldPanel, itemPanel;
	JLabel welcomeLabel, searchResult;
	JTextField searchField, itemsForAction;
	JTextArea textArea;
	JButton checkout, reserve, pay, query, back, signout, guestBack;
	JTable resultTable;
	JScrollPane scrollPane;

	public static SearchResultGUI getLibraryMember(String loginMember){
		if(searchRes == null)
			searchRes = new SearchResultGUI(loginMember);
		return searchRes;
	}


	public void start(List<LibraryItems> returnList){

		welcomePanel = new JPanel(new FlowLayout());
		signout = new JButton("Sign Out");
		back = new JButton("Go Back");
		guestBack = new JButton("Go Back");
		
		if(getLoginMember() != null)
			welcomePanel.add(back);
		else
			welcomePanel.add(guestBack);
		
		if(getLoginMember() == null)
			welcomePanel.add(new JLabel("Welcome:\t Guest"));
		else if(getLoginMember().equalsIgnoreCase("admin")){
			welcomePanel.add(new JLabel("Welcome:\t Admin"));
			welcomePanel.add(signout);
		}
		else{
			welcomePanel.add(new JLabel("Welcome:\t" + getLoginMember()));
			welcomePanel.add(signout);
		}

		welcomePanel.setBorder(new EtchedBorder());

		searchResultPanel = new JPanel();
		
		String columnNames[] = { "Item Number","Title", "Author", "Location", "Category", "Status"};
		
		
		scrollPane = new JScrollPane( resultTable );
		
		String[][] dataArray = new String[returnList.size()][6];
		
		for(int i =0 ; i< returnList.size(); i++){
			for(int j=0; j< 6; j++)
				if(j == 0)
					dataArray[i][j] = returnList.get(i).getItemNumber();
				else if(j == 1)
					dataArray[i][j] = returnList.get(i).getTitle();
				else if(j == 2)
					dataArray[i][j] = returnList.get(i).getCreator();
				else if(j == 3)
					dataArray[i][j] = returnList.get(i).getLocation();
				else if(j == 4)
					dataArray[i][j] = returnList.get(i).getCategory();
				else if(j == 5)
					dataArray[i][j] = returnList.get(i).getStatus();
		}
		
		//resultTable = new JTable(dataArray, columnNames);
        
        DefaultTableModel model = new DefaultTableModel(dataArray,columnNames);
        resultTable = new JTable(model);
        JScrollPane sp = new JScrollPane(resultTable);

		searchResultPanel.add(resultTable);
		searchResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Search Results:"));
		
		itemPanel = new JPanel(new GridLayout(2,1));
		itemActionFieldPanel = new JPanel(new FlowLayout());
		itemsForAction = new JTextField(50);
		itemActionFieldPanel.add(itemsForAction);
		
		
		buttonPanel = new JPanel(new FlowLayout());
		checkout = new JButton("CHECKOUT");
		reserve = new JButton("RESERVE");
		pay = new JButton("PAY");
		query = new JButton("QUERY TRANSACTION");
		buttonPanel.add(checkout);
		buttonPanel.add(reserve);
		buttonPanel.add(pay);
		buttonPanel.add(query);
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "CHOOSE YOUR ACTIONS:"));

		itemPanel.add(itemActionFieldPanel);
		itemPanel.add(buttonPanel);
		
		statusPanel = new JPanel(new FlowLayout());
		textArea = new JTextArea("STATUS");
		textArea.setPreferredSize(new Dimension(500,100));
		statusPanel.add(textArea);
		statusPanel.setBorder(new TitledBorder(new EtchedBorder(), "ACTIONS:"));

		checkout.setActionCommand(Actions.checkout.name());
		reserve.setActionCommand(Actions.reserve.name());
		pay.setActionCommand(Actions.pay.name());
		query.setActionCommand(Actions.query.name());
		signout.setActionCommand(Actions.signout.name());
		back.setActionCommand(Actions.back.name());
		guestBack.setActionCommand(Actions.signout.name());


		checkout.addActionListener(this);
		reserve.addActionListener(this);
		query.addActionListener(this);
		pay.addActionListener(this);
		signout.addActionListener(this);
		back.addActionListener(this);
		guestBack.addActionListener(this);
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
		if (e.getActionCommand() == Actions.SEARCH.name()) {
			List<LibraryItems> returnList= new ArrayList<LibraryItems>(); 
			returnList = super.searchLibraryItems(searchField.getText(),libMemberObj);

		}else if (e.getActionCommand() == Actions.checkout.name()) {
			LibraryMember member = new LibraryMember(getLoginMember());
			String[] itemList = itemsForAction.getText().split(",");
			for(int i = 0; i < itemList.length ; i++){
				String status = member.checkOutItems(itemList[i]);
				JOptionPane.showMessageDialog(this, status ,
						"Info",JOptionPane.INFORMATION_MESSAGE);
			}	
		}else if (e.getActionCommand() == Actions.reserve.name()) {
			LibraryMember member = new LibraryMember(getLoginMember());
			String[] itemList = itemsForAction.getText().split(":");
			
			for(int i = 0; i < itemList.length ; i++){
				try{
				member.reserveItems(itemList[i]);
				JOptionPane.showMessageDialog(this, "Reserved Successfully. An email will be sent on your email id when the book becomes available." ,
						"Info",JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception e2){
				JOptionPane.showMessageDialog(this, "Sorry!!! Unable to reserve. Please try again later." ,
						"Info",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		
		}else if (e.getActionCommand() == Actions.pay.name()) {
			List<Transaction> returnList= new ArrayList<Transaction>(); 
			LibraryMember member = new LibraryMember(getLoginMember());
			returnList = member.displayBorrowedItems();
			
			PayFinesGUI res = new PayFinesGUI(getLoginMember());
			res.start(returnList);
			
			ArrayList<JPanel> returnPayFinesGUIPanels = res.getPayFinesResPanels();
			LoginDemo.frame.getContentPane().removeAll();
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0,1));
			panel.add(returnPayFinesGUIPanels.get(0));
			panel.add(returnPayFinesGUIPanels.get(1));
			panel.add(returnPayFinesGUIPanels.get(2));
			panel.add(returnPayFinesGUIPanels.get(3));
			
			
			LoginDemo.frame.getContentPane().add(panel, BorderLayout.SOUTH);

			LoginDemo.frame.setSize(800, 600);
			LoginDemo.frame.invalidate();
			LoginDemo.frame.validate();
			setVisible(true);
		}else if (e.getActionCommand() == Actions.query.name()) {
			QueryGUIInputs res = new QueryGUIInputs(getLoginMember());
			res.start();
			
			ArrayList<JPanel> returnQueryGUIInputPanels = res.getQueryGUIInputsResPanels();
			LoginDemo.frame.getContentPane().removeAll();
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0,1));
			panel.add(returnQueryGUIInputPanels.get(0));
			panel.add(returnQueryGUIInputPanels.get(1));
			panel.add(returnQueryGUIInputPanels.get(2));
			
			LoginDemo.frame.getContentPane().add(panel, BorderLayout.SOUTH);

			LoginDemo.frame.setSize(800, 450);
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

	public ArrayList<JPanel> getSearchResPanels() {
		ArrayList<JPanel> libMemPanels = new ArrayList<JPanel>();
		libMemPanels.add(welcomePanel);
		libMemPanels.add(searchResultPanel);
		libMemPanels.add(itemPanel);
		libMemPanels.add(statusPanel);
		return libMemPanels;
	}
}




