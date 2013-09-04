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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import pkg.abstractFactory.LibraryItems;
import pkg.abstractFactory.LibraryMember;	
import pkg.swing.gui.SearchResultGUI.Actions;

/**
 * @author redokani
 *
 */
public class LibraryMemberGUI extends LoggedInUserPageGUI{

	public enum Actions {
		SEARCH,
		checkout,
		reserve,
		pay,
		query,
		signout
	}

	public LibraryMemberGUI(String loginMember) {
		super(loginMember);
		// TODO Auto-generated constructor stub
	}

	static LibraryMemberGUI libMember = null;
	JPanel welcomePanel, searchPanel, searchResultPanel, buttonPanel, statusPanel;
	JLabel welcomeLabel, searchResult;
	JTextField searchField;
	JTextArea textArea;
	JButton SEARCH, checkout, reserve, pay, query, signout;

	public static LibraryMemberGUI getLibraryMember(String loginMember){
		if(libMember == null)
			libMember = new LibraryMemberGUI(loginMember);
		return libMember;
	}

	public void start(){

		welcomePanel = new JPanel(new FlowLayout());
		signout = new JButton("Sign Out");
		welcomePanel.add(new JLabel("Welcome:"+getLoginMember()));
		welcomePanel.add(signout);
		welcomePanel.setBorder(new EtchedBorder());

		searchPanel = new JPanel(new FlowLayout());
		searchField = new JTextField(50);
		searchField.setText("Enter search options: Search By Keyword/Location/Title/Author/Keyword in a title\n");
		SEARCH = new JButton("SEARCH");

		searchPanel.add(searchField);
		searchPanel.add(SEARCH);	
		searchPanel.setBorder(new TitledBorder(new EtchedBorder(), "SEARCH"));

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

		SEARCH.setActionCommand(Actions.SEARCH.name());
		checkout.setActionCommand(Actions.checkout.name());
		reserve.setActionCommand(Actions.reserve.name());
		pay.setActionCommand(Actions.pay.name());
		query.setActionCommand(Actions.query.name());
		signout.setActionCommand(Actions.signout.name());

		SEARCH.addActionListener(this);
		signout.addActionListener(this);
		checkout.addActionListener(this);
		reserve.addActionListener(this);


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
			SearchResultGUI res = new SearchResultGUI(getLoginMember());
			res.start(returnList);
			
			ArrayList<JPanel> searchResPanels = res.getSearchRes();
			LoginDemo.frame.getContentPane().removeAll();
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0,1));
			panel.add(searchResPanels.get(0));
			panel.add(searchResPanels.get(1));
			panel.add(searchResPanels.get(2));
			panel.add(searchResPanels.get(3));
			
			
			LoginDemo.frame.getContentPane().add(panel, BorderLayout.SOUTH);

			LoginDemo.frame.setSize(800, 600);
			LoginDemo.frame.invalidate();
			LoginDemo.frame.validate();
			setVisible(true);
			
		}else if (e.getActionCommand() == Actions.checkout.name()) {

		}else if (e.getActionCommand() == Actions.reserve.name()) {

		}else if (e.getActionCommand() == Actions.pay.name()) {

		}else if (e.getActionCommand() == Actions.query.name()) {

		}else if (e.getActionCommand() == Actions.signout.name()) {
			LoginDemo.frame.getContentPane().removeAll();
	    	setVisible(false);
	    	LoginDemo.start(); 
//	    	LoginDemo.frame.invalidate();
//	    	LoginDemo.frame.validate();
	    	setVisible(true);
		}

	}

	public ArrayList<JPanel> getLibraryMemberpanels() {
		ArrayList<JPanel> libMemPanels = new ArrayList<JPanel>();
		libMemPanels.add(welcomePanel);
		libMemPanels.add(searchPanel);
		//libMemPanels.add(searchResultPanel);
		libMemPanels.add(buttonPanel);
		libMemPanels.add(statusPanel);
		return libMemPanels;
	}
}




