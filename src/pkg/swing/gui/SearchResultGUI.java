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
	JPanel welcomePanel, searchResultPanel, buttonPanel, statusPanel;
	JLabel welcomeLabel, searchResult;
	JTextField searchField;
	JTextArea textArea;
	JButton checkout, reserve, pay, query, back, signout;
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
		welcomePanel.add(back);
		welcomePanel.add(new JLabel("Welcome:"+getLoginMember()));
		welcomePanel.add(signout);
		
		welcomePanel.setBorder(new EtchedBorder());

		searchResultPanel = new JPanel();
		
		String columnNames[] = { "Title", "Author", "Location", "Category", "Status"};
		
		
		scrollPane = new JScrollPane( resultTable );
		
		String[][] dataArray = new String[returnList.size()][5];
		
		for(int i =0 ; i< returnList.size(); i++){
			for(int j=0; j< 5; j++)
				if(j == 0)
					dataArray[i][j] = returnList.get(i).getTitle();
				else if(j == 1)
					dataArray[i][j] = returnList.get(i).getCreator();
				else if(j == 2)
					dataArray[i][j] = returnList.get(i).getLocation();
				else if(j == 3)
					dataArray[i][j] = returnList.get(i).getCategory();
				else if(j == 4)
					dataArray[i][j] = returnList.get(i).getStatus();
		}
		
		//resultTable = new JTable(dataArray, columnNames);
        
        DefaultTableModel model = new DefaultTableModel(dataArray,columnNames);
        resultTable = new JTable(model);
        JScrollPane sp = new JScrollPane(resultTable);

		searchResultPanel.add(resultTable);
		searchResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Search Results:"));
		
		
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


		checkout.addActionListener(this);
		reserve.addActionListener(this);
		query.addActionListener(this);
		pay.addActionListener(this);
		signout.addActionListener(this);
		back.addActionListener(this);
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

		}else if (e.getActionCommand() == Actions.reserve.name()) {

		}else if (e.getActionCommand() == Actions.pay.name()) {

		}else if (e.getActionCommand() == Actions.query.name()) {

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

	public ArrayList<JPanel> getSearchRes() {
		ArrayList<JPanel> libMemPanels = new ArrayList<JPanel>();
		libMemPanels.add(welcomePanel);
		libMemPanels.add(searchResultPanel);
		libMemPanels.add(buttonPanel);
		libMemPanels.add(statusPanel);
		return libMemPanels;
	}
}




