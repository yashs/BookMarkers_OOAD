/**
 * 
 */
package pkg.misc;

/**
 * @author redokani
 *
 */
public class Payment {
	
	public static void initiatePayment(String cardType, String cardNumber) {

		/*Initiating Payment */
		System.out.println("\nInitiating Payment...Please wait...");
		CreditCardPayment ccInstance = CreditCardPayment.getCreditCardPaymentInstance(cardType, cardNumber);

		acceptPayment(ccInstance);
		System.out.println("\n*******************************");

	}

	public static void acceptPayment(CreditCardPayment ccInstance) {
		System.out.println("\nThanks a lot for choosing Omega Service. Your Payment has been processed on the following Card:\n Card Type:\t"+ ccInstance.getClientCCCardType() + "\nCard Number:\t"+ccInstance.getClientCardNumber());

	}
}
