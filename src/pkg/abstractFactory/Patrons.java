/**
 * 
 */
package pkg.abstractFactory;

/**
 * @author redokani
 *
 */
public abstract class Patrons extends User {	
	private String loginName;
	private String cardNumber;
	
	public Patrons(String loginName){
		this.loginName = loginName;
	}
	
	public String getLoginName(){
		return loginName;
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
