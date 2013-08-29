/**
 * 
 */
package pkg.gui.clients;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import pkg.abstractFactory.LibraryAdmin;

/**
 * @author redokani
 *
 */
public class LibraryAdminGUI extends LoggedInUserPageGUI {


	public LibraryAdminGUI(String loginMember) {
		super(loginMember);
	}

	/* (non-Javadoc)
	 * @see pkg.gui.clients.LoggedInUserPageGUI#showWelcomeMessage(java.lang.String)
	 */
	@Override
	public void showWelcomeMessage(String loginUserName) {
		System.out.println("Welcome Admin " + loginUserName);
		performLoginActions();

	}

	/* (non-Javadoc)
	 * @see pkg.gui.clients.LoggedInUserPageGUI#performLoginActions(java.lang.String)
	 */
	@Override
	public void performLoginActions() {
		LibraryAdmin libAdminObj = new LibraryAdmin(getLoginMember());
		Scanner takeIpFromMember = new Scanner(System.in);

		System.out.println("\n Choose options: 1.Search \n2.query max items checked \n3.query total number of items ");
		System.out.println("\n4.query total number of items borrowed \n5.query no of members \n6. query total fine collected");

		int options;
		options = takeIpFromMember.nextInt();
		if(options == 1){
			super.searchLibraryItems(takeIpFromMember,libAdminObj);
		}

		else if(options == 2){
			displayMaxItemsChecked(libAdminObj);			
		}

		else if(options == 3){
			displayNoOfItems(libAdminObj);

		}

		else if(options == 4){
			displayNoOfItemsBorrowed(libAdminObj);
		}

		else if(options == 5){
			displayNoOfMembers(libAdminObj);

		}

		else if(options == 6){
			displayTotalFineCollected(libAdminObj);

		}
		else{
			System.out.println("wrong option!!!");
		}
	}

	public void displayTotalFineCollected(LibraryAdmin libAdminObj) {
		int totalFineCollected = libAdminObj.queryTotalFineCollected();
	}

	public void displayNoOfMembers(LibraryAdmin libAdminObj) {
		int noOfMembers = libAdminObj.queryNoOfMembers();
		System.out.println("Total number of members are: "+noOfMembers);
	}

	public void displayNoOfItemsBorrowed(LibraryAdmin libAdminObj) {
		Map<String,Integer> noOfItemsBorrowed = new HashMap<String,Integer>();
		noOfItemsBorrowed = libAdminObj.queryNoOfItemsBorrowed();
	}

	public void displayNoOfItems(LibraryAdmin libAdminObj) {
		Map<String,Integer> noOfItems = new HashMap<String,Integer>();
		noOfItems = libAdminObj.queryNoOfItems();
		//display value in a text area
		
	}

	public void displayMaxItemsChecked(LibraryAdmin libAdminObj) {
		String title = libAdminObj.queryMaxItemsChecked();
	}

}
