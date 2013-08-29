/**
 * 
 */
package pkg.database;

import java.sql.Timestamp;

/**
 * @author redokani
 *
 */
public class Transaction {
	private String loginName;
	private String memberCard;
	private int itemId;
	private String itemTitle;
	private Timestamp checkoutDate;
	private Timestamp dueDate;
	private Timestamp returnDate;
	private int fineAmountDue;
	private int fineAmountPaid;
	
	/**
	 * @return the itemTitle
	 */
	public String getItemTitle() {
		return itemTitle;
	}
	/**
	 * @param itemTitle the itemTitle to set
	 */
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}
	/**
	 * @return the checkoutDate
	 */
	public Timestamp getCheckoutDate() {
		return checkoutDate;
	}
	/**
	 * @return the dueDate
	 */
	public Timestamp getDueDate() {
		return dueDate;
	}
	/**
	 * @return the returnDate
	 */
	public Timestamp getReturnDate() {
		return returnDate;
	}
	/**
	 * @return the fineAmount
	 */
	public int getFineAmountDue() {
		return fineAmountDue;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	/**
	 * @param checkoutDate the checkoutDate to set
	 */
	public void setCheckoutDate(Timestamp checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}
	/**
	 * @param fineAmount the fineAmount to set
	 */
	public void setFineAmountDue(int fineAmountDue) {
		this.fineAmountDue = fineAmountDue;
	}
	public String getMemberCard() {
		return memberCard;
	}
	public void setMemberCard(String memberCard) {
		this.memberCard = memberCard;
	}
	public int getFineAmountPaid() {
		return fineAmountPaid;
	}
	public void setFineAmountPaid(int fineAmountPaid) {
		this.fineAmountPaid = fineAmountPaid;
	}
	
}
