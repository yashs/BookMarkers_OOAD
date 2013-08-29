/**
 * 
 */
package pkg.gui.clients;

import java.util.Scanner;

import pkg.subscription.MemberRegistration;

/**
 * @author redokani
 *
 */
public class CreditCardPaymentGUI {

	public void payment(MemberRegistration memberReg,Scanner userDetails) {
		String cardType;
		String ccardNumber;
		System.out.println("Enter card type:");
		cardType = userDetails.next();
		System.out.println("Enter Card number:");
		ccardNumber = userDetails.next();
		memberReg.initiatePayment(cardType,ccardNumber);
		
	}

}
