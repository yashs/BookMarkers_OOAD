package pkg.misc;

/**
 *  @author rdokania
 * Name: Reshma Dokania
 * ID: 00000968206
 * Course Number: COEN 359
 * Date Of Submission: 27th July 2013
 *
 */
public class CreditCardPayment{

		private String clientCCCardType;
		private String clientCardNumber;
		private static CreditCardPayment creditcardObjInstance;
		
		/**
		 * @return the clientCCCardType
		 */
		public String getClientCCCardType() {
			return clientCCCardType;
		}

		/**
		 * @return the clientCardNumber
		 */
		public String getClientCardNumber() {
			return clientCardNumber;
		}

		private CreditCardPayment(String clientCCCartType, String clientCardNumber){
			this.clientCCCardType = clientCCCartType;
			this.clientCardNumber = clientCardNumber;
		}
		
		public static CreditCardPayment getCreditCardPaymentInstance(String clientCCCartType, String clientCardNumber){
			if(creditcardObjInstance == null){
				creditcardObjInstance = new CreditCardPayment(clientCCCartType, clientCardNumber);	
			}
			
			return creditcardObjInstance;
			
		}
	}