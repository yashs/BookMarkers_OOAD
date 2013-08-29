/**
 * 
 */
package pkg.abstractFactory;

import java.util.HashMap;
import java.util.Map;

import pkg.database.PersistanceActionsAdmin;

/**
 * @author redokani
 *
 */
public class LibraryAdmin extends Patrons {

	public LibraryAdmin(String loginName) {
		super(loginName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see pkg.abstractFactory.Patrons#searchItems()
	 */
	public Map<String,Integer> queryNoOfItems(){
		//Find the number of books (and videos) that are carried by the library in every category.
		//getLoginName and query database
		return PersistanceActionsAdmin.queryNoOfItems();
	}
	
	public int queryNoOfMembers(){
		//Find the total number of members.
		return PersistanceActionsAdmin.queryNoOfMembers();
		
		
	}
	
	public Map<String,Integer> queryNoOfItemsBorrowed(){
		//Find the number of books/items currently out.
		return PersistanceActionsAdmin.queryNoOfItemsBorrowed();
	}

	public String queryMaxItemsChecked(){
		//Find the title of the book checked out the most in a month (or a year)
		return PersistanceActionsAdmin.queryMaxItemsChecked(getLoginName());
		
	}
	
	public int  queryTotalFineCollected(){
		return PersistanceActionsAdmin.queryTotalFineCollected(getLoginName());
	}

}
