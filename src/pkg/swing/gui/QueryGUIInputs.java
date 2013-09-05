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
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.sun.media.sound.HsbParser;

import pkg.abstractFactory.LibraryItems;
import pkg.abstractFactory.LibraryMember;	
import pkg.database.Transaction;

/**
 * @author redokani
 *
 */
public class QueryGUIInputs extends LoggedInUserPageGUI{

	static HashMap<String, String> 
	monthsMap = null;
	static{
		monthsMap = new HashMap<String, String>();
		monthsMap.put("", "");
		monthsMap.put("JAN", "01");
		monthsMap.put("FEB", "02");
		monthsMap.put("MAR", "03");
		monthsMap.put("APR", "04");
		monthsMap.put("MAY", "05");
		monthsMap.put("JUN", "06");
		monthsMap.put("JUL", "07");
		monthsMap.put("AUG", "08");
		monthsMap.put("SEP", "09");
		monthsMap.put("OCT", "10");
		monthsMap.put("NOV", "11");
		monthsMap.put("DEC", "12");
	}

	public enum Actions {
		back,
		signout,
		query
	}

	public QueryGUIInputs(String loginMember) {
		super(loginMember);
		// TODO Auto-generated constructor stub
	}

	public QueryGUIInputs() {
		super();
		// TODO Auto-generated constructor stub
	}

	static QueryGUIInputs queryItemsGUI = null;
	JPanel welcomePanel, inputPanel,  statusPanel;
	JLabel welcomeLabel, returnResult;
	JComboBox month;
	JTextField year;
	JTextArea textArea;
	JButton back, signout, query;
	JTable resultTable;
	JScrollPane scrollPane;

	public static QueryGUIInputs getLibraryMember(String loginMember){
		if(queryItemsGUI == null)
			queryItemsGUI = new QueryGUIInputs(loginMember);
		return queryItemsGUI;
	}


	public void start(){

		welcomePanel = new JPanel(new FlowLayout());
		signout = new JButton("Sign Out");
		back = new JButton("Go Back");

		welcomePanel.add(back);
		welcomePanel.add(new JLabel("Welcome:\t" + getLoginMember()));
		welcomePanel.add(signout);

		welcomePanel.setBorder(new EtchedBorder());

		inputPanel = new JPanel(new FlowLayout());		
		String[] months = { "", "JAN", "FEB", "MAR","APR", "MAY", "JUN", "JUL", "AUG", "SEP","OCT", "NOV", "DEC" };
		month = new JComboBox(months);
		year = new JTextField(4);
		year.setText("2013");
		query = new JButton("QUERY");
		inputPanel.add(month);
		inputPanel.add(year);
		inputPanel.add(query);

		statusPanel = new JPanel(new FlowLayout());
		textArea = new JTextArea("STATUS");
		textArea.setPreferredSize(new Dimension(500,100));
		statusPanel.add(textArea);
		statusPanel.setBorder(new TitledBorder(new EtchedBorder(), "ACTIONS:"));

		
		query.setActionCommand(Actions.query.name());
		signout.setActionCommand(Actions.signout.name());
		back.setActionCommand(Actions.back.name());
	
		signout.addActionListener(this);
		back.addActionListener(this);
		query.addActionListener(this);
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
		if (e.getActionCommand() == Actions.back.name()) {
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
		}else if (e.getActionCommand() == Actions.query.name()) {
			List<Transaction> returnList= new ArrayList<Transaction>(); 
			LibraryMember member = new LibraryMember(getLoginMember());
			returnList = member.queryTransactionHistory(monthsMap.get(month.getSelectedItem()), year.getText());

			QueryItemsGUI res = new QueryItemsGUI(getLoginMember());
			res.start(returnList);

			ArrayList<JPanel> returnQueryTransactionGUIPanels = res.getQueryResPanels();
			LoginDemo.frame.getContentPane().removeAll();

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0,1));
			panel.add(returnQueryTransactionGUIPanels.get(0));
			panel.add(returnQueryTransactionGUIPanels.get(1));
			panel.add(returnQueryTransactionGUIPanels.get(2));

			LoginDemo.frame.getContentPane().add(panel, BorderLayout.SOUTH);

			LoginDemo.frame.setSize(800, 450);
			LoginDemo.frame.invalidate();
			LoginDemo.frame.validate();
			setVisible(true);
		}

	}

	public ArrayList<JPanel> getQueryGUIInputsResPanels() {
		ArrayList<JPanel> libMemPanels = new ArrayList<JPanel>();
		libMemPanels.add(welcomePanel);
		libMemPanels.add(inputPanel);
		libMemPanels.add(statusPanel);
		return libMemPanels;
	}
}




