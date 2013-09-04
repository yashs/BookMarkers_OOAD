/**
 * 
 */
package pkg.swing.gui;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.JPanel;

import pkg.abstractFactory.*;

/**
 * @author redokani
 *
 */
public abstract class LoggedInUserPageGUI extends JPanel implements ActionListener {

	private String loginMember;
	
	public LoggedInUserPageGUI(String loginMember){
		this.loginMember = loginMember;
	}
	
	public LoggedInUserPageGUI(){

	}
	
	public String getLoginMember(){
		return loginMember;
	}
	
	
	public abstract void showWelcomeMessage(String loginMessage);
	
	public abstract void performLoginActions();
	
	public List<LibraryItems> searchLibraryItems(String keyword, User libUser) {
		System.out.println("Enter search options: Search By Keyword/Location/Title/Author/Keyword in a title\n");
		//String keyword = takeIpFromMember.nextLine();
		return libUser.searchItems(keyword);
	}

	//show buttons with functionalities reserve a book,checkoutbook,return book,payfine,
	//query for transaction history



}
