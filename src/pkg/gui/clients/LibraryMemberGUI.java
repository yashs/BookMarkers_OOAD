/**
 * 
 */
package pkg.gui.clients;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pkg.abstractFactory.LibraryMember;
import pkg.database.PersistanceActions;
import pkg.database.Transaction;

/**
 * @author redokani
 *
 */
public class LibraryMemberGUI extends LoggedInUserPageGUI{

	public LibraryMemberGUI(String loginName) {
		super(loginName);
	}

	@Override
	public void showWelcomeMessage(String loginUserName) {
		System.out.println("Welcome " + loginUserName);		
		performLoginActions();
	}


	public void performLoginActions() {
		LibraryMember libMemberObj = new LibraryMember(getLoginMember());
		libMemberObj.calculateFines();
		Scanner takeIpFromMember = new Scanner(System.in);
		int options = 0;

		while(true){
			System.out.println("\nChoose Options: 1.Search \n2.Reserve a book \n3. Checkout Book \n4.Return a book \n5.Pay Fine \n6.Query Transaction History \n7.Exit");


			options = takeIpFromMember.nextInt();
			String itemId = takeIpFromMember.nextLine(); //validate this itemId and set it in item class

			/*Note for GUI
			 * here user will select options for item. If any item selected and is not available, then show reserve item button
			 * if any item checked is available then show checkout button
			 * pay fine button will be shown if a book is returned and user has to pay fine
			 * query transaction history button is everytime available
			 * */

			if(options == 1){
				//search
				super.searchLibraryItems(takeIpFromMember,libMemberObj);
			}

			else if(options == 2){
				//Reserve a book if not available 
				libMemberObj.reserveItems(itemId);
			}
			else if(options == 3){
				//checkout a book

				String status = libMemberObj.checkOutItems(itemId);
				List<Transaction> displayAllCheckOutItems = new ArrayList<Transaction>();
				displayAllCheckOutItems = libMemberObj.getAllCheckOutItems(); //iterate

			}
			else if(options == 4){
				//return a book
				libMemberObj.returnItems(itemId);
			}
			else if(options == 5){
				//pay fine
				libMemberObj.calculateFines(); //show all fine with that username
				String payfine = libMemberObj.payFine();
				//this payfine contains member card and total fine to be paid
			}
			else if(options == 6){
				//query transaction
				libMemberObj.queryTransactionHistory();
			}
			else if(options ==7){
				System.exit(0);
			}
		}

	}


}
