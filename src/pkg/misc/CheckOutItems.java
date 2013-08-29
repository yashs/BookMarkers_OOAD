/**
 * 
 */
package pkg.misc;

import java.util.List;

import pkg.database.Transaction;

/**
 * @author redokani
 *
 */
public class CheckOutItems {
	String loginName;
	String memberCard;
	int totalItemsCheckedOut;
	List<Transaction> totalItemsTransactonInf;
}
