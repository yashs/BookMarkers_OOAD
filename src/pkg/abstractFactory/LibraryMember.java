/**
 * 
 */
package pkg.abstractFactory;

import java.util.List;

import pkg.database.*;

/**
 * @author redokani
 *
 */
public class LibraryMember extends Patrons {

	public LibraryMember(String loginName) {
		super(loginName);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see pkg.abstractFactory.Patrons#searchItems()
	 */
	public void reserveItems(String itemId){
		itemId = "F1";
		PersistanceActions.reserveItem(getLoginName(),itemId);
	}
	
	
	//checkoutitems
	
	public String checkOutItems(String itemId){
		itemId = "F1";
		return PersistanceActions.checkOutItems(getLoginName(),itemId);
	}	
	
	//display checkout items
	public List<Transaction> getAllCheckOutItems() {
		return PersistanceActions.getAllCheckOutItems(getLoginName());
		
	}
	
	//return items
	
	public void returnItems(String itemId){
		PersistanceActions.returnItems(getLoginName(),itemId);
	}
	
	public String payFine(){
		return PersistanceActions.payFine(getLoginName());
	}
	
	
	public void calculateFines() {
		//populate total fines by this user 
		//populate how many NF,F and videos is checked out by this user
		PersistanceActions.calculateFines(getLoginName());
		
	}
	
	//query transaction
	
	public void queryTransactionHistory(){
		
	}

	
	

	
}

