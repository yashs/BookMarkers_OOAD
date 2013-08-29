/**
 * 
 */
package pkg.gui.clients;

import java.util.Scanner;

import pkg.abstractFactory.*;

/**
 * @author redokani
 *
 */
public abstract class LoggedInUserPageGUI {

	private String loginMember;
	
	public LoggedInUserPageGUI(String loginMember){
		this.loginMember = loginMember;
	}
	
	public String getLoginMember(){
		return loginMember;
	}
	
	
	public abstract void showWelcomeMessage(String loginMessage);
	
	public abstract void performLoginActions();
	
	public void searchLibraryItems(Scanner takeIpFromMember, User libUser) {
		System.out.println("Enter search options: Search By Keyword/Location/Title/Author/Keyword in a title\n");
		String keyword = takeIpFromMember.nextLine();
		libUser.searchItems(keyword);
	}

	//show buttons with functionalities reserve a book,checkoutbook,return book,payfine,
	//query for transaction history



}
