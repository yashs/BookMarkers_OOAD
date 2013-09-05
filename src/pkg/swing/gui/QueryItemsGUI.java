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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.sun.media.sound.HsbParser;

import pkg.abstractFactory.LibraryItems;
import pkg.abstractFactory.LibraryMember;	
import pkg.database.Transaction;

/**
 * @author redokani
 *
 */
public class QueryItemsGUI extends LoggedInUserPageGUI{

	//	static HashMap<String, String> 
	//	monthsMap = null;
	//	static{
	//		monthsMap = new HashMap<String, String>();
	//		monthsMap.put("JAN", "01");
	//		monthsMap.put("FEB", "02");
	//		monthsMap.put("MAR", "03");
	//		monthsMap.put("APR", "04");
	//		monthsMap.put("MAY", "05");
	//		monthsMap.put("JUN", "06");
	//		monthsMap.put("JUL", "07");
	//		monthsMap.put("AUG", "08");
	//		monthsMap.put("SEP", "09");
	//		monthsMap.put("OCT", "10");
	//		monthsMap.put("NOV", "11");
	//		monthsMap.put("DEC", "12");
	//	}

	public enum Actions {
		back,
		signout,
		query
	}

	public QueryItemsGUI(String loginMember) {
		super(loginMember);
		// TODO Auto-generated constructor stub
	}

	public QueryItemsGUI() {
		super();
		// TODO Auto-generated constructor stub
	}

	static QueryItemsGUI queryItemsGUI = null;
	JPanel welcomePanel, inputPanel, queryItemResultPanel = null, statusPanel;
	JLabel welcomeLabel, returnResult;
	JTextArea textArea;
	JButton back, signout, query;
	JTable resultTable;
	JScrollPane scrollPane;
	//	String month, year;

	public static QueryItemsGUI getLibraryMember(String loginMember){
		if(queryItemsGUI == null)
			queryItemsGUI = new QueryItemsGUI(loginMember);
		return queryItemsGUI;
	}


	public void start(List<Transaction> returnList){

		welcomePanel = new JPanel(new FlowLayout());
		signout = new JButton("Sign Out");
		back = new JButton("Go Back");

		welcomePanel.add(back);
		welcomePanel.add(new JLabel("Welcome:\t" + getLoginMember()));
		welcomePanel.add(signout);

		welcomePanel.setBorder(new EtchedBorder());



		queryItemResultPanel = new JPanel(new FlowLayout());

		String columnNames[] = { "Item No", "Title", "Checkout Date", "Due Date", "Return Date", "Fine Amount Due", "Select"};


		scrollPane = new JScrollPane( resultTable );

		Object[][] dataArray = new String[returnList.size()][6];

		for(int i =0 ; i< returnList.size(); i++){
			for(int j=0; j< 7; j++)
				if(j == 0)
					dataArray[i][j] = returnList.get(i).getItemId();
				else if(j == 1)
					dataArray[i][j] = returnList.get(i).getItemTitle();
				else if(j == 2)
					dataArray[i][j] = returnList.get(i).getCheckoutDate().toString();
				else if(j == 3)
					dataArray[i][j] = returnList.get(i).getDueDate()+"";
				else if(j == 4)
					dataArray[i][j] = returnList.get(i).getReturnDate()+"";
				else if(j == 5)
					dataArray[i][j] = returnList.get(i).getFineAmountDue()+"";
			}

		//resultTable = new JTable(dataArray, columnNames);

		DefaultTableModel model = new DefaultTableModel(dataArray,columnNames);
		resultTable = new JTable(model);
		resultTable.setSize(new Dimension(200,200));
		resultTable.setFillsViewportHeight(true);
	    //JScrollPane pane = new JScrollPane(resultTable);
	    
		queryItemResultPanel.add(resultTable);
		queryItemResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Transaction Queried:"));


		statusPanel = new JPanel(new FlowLayout());
		textArea = new JTextArea("STATUS");
		textArea.setPreferredSize(new Dimension(500,100));
		statusPanel.add(textArea);
		statusPanel.setBorder(new TitledBorder(new EtchedBorder(), "ACTIONS:"));


		signout.setActionCommand(Actions.signout.name());
		back.setActionCommand(Actions.back.name());

		signout.addActionListener(this);
		back.addActionListener(this);
		//query.addActionListener(this);
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
		}


	}

	public ArrayList<JPanel> getQueryResPanels() {
		ArrayList<JPanel> libMemPanels = new ArrayList<JPanel>();
		libMemPanels.add(welcomePanel);
		//libMemPanels.add(inputPanel);
		libMemPanels.add(queryItemResultPanel);
		libMemPanels.add(statusPanel);
		return libMemPanels;
	}
}
