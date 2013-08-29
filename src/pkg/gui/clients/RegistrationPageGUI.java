/**
 * 
 */
package pkg.gui.clients;

import java.sql.Timestamp;
import java.util.Random;
import java.util.Scanner;

import pkg.database.Encrypt;
import pkg.subscription.MemberRegistration;

/**
 * @author redokani
 *
 */
public class RegistrationPageGUI {
	
	private String loginName;
	private String password;
	private String confirmPassword;
	private String fullName;
	private String emailID;
	private long phoneNumber = 0;
	private String address;
	private long cardNumber;
	
	//take value from GUI or scanner and set all values in MemberRegistration
	//call persist action in DB method memberRegistrationDetails
	public void registerMember() {
		MemberRegistration memberReg = new MemberRegistration();
		Scanner userDetails = new Scanner(System.in);
		
		System.out.println("Enter Login Name:");
		while(true){
			loginName = userDetails.nextLine();
			if(memberReg.checkDuplicateLoginName(loginName)){
				System.out.println("Duplicate LoginName!!! Enter again:\n");
			}
			else{
				break;
			}
		}
		
	
		memberReg.setLoginName(loginName);
		
		while(true){
			System.out.println("Enter Password:"); // to do - encrypt
			password = userDetails.nextLine();
			
			System.out.println("Enter Confirm Password:");
			confirmPassword = userDetails.nextLine();
			
			if(!password.equals(confirmPassword)){
				System.out.println("Password does not Match!!!");
			}
			else{
				memberReg.setPassWord(password);
				break;
			}			
		}		
		
		System.out.println("Enter FullName:"); 
		fullName = userDetails.nextLine();
		memberReg.setFullName(fullName);
		
		System.out.println("Enter Email ID:"); //if email id specified confirm registration with emailid
		while(true){
			emailID = userDetails.nextLine();	
			if(emailID.equalsIgnoreCase("")){
				break;
			}
			if(!memberReg.isValidEmailAddress(emailID)){
				System.out.println("Not a valid email address! Please enter again!!\n");
			}
			else{
				break;
			}
			
		}
		memberReg.setEmailID(emailID);
		
		System.out.println("Enter Phone Number:"); //if phone number confirm sending text with code 
		
		//check in GUI
		while(true){
			phoneNumber = userDetails.nextLong();		
			if(phoneNumber == 0 && emailID.equalsIgnoreCase("")){
				System.out.println("Enter phone number as email id is not provided!!");				
			}
			else{
				break;
			}			
		}
		memberReg.setPhoneNumber(phoneNumber);
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		memberReg.setStartDate(time);//not necessary call only when user validate
		
		Random generator = new Random();
		cardNumber = generator.nextInt(400);
		memberReg.setCardNumber(cardNumber);
		
		System.out.println("Enter Address(Enter city):");
		address = userDetails.nextLine();
		memberReg.setAddress(address);	
		
		
		if(!address.equalsIgnoreCase("spingfield")){
			/*CreditCardPaymentGUI ccGUI = new CreditCardPaymentGUI();
			ccGUI.payment(memberReg,userDetails);	*/
			
			String cardType;
			String ccardNumber;
			System.out.println("Enter card type:");
			cardType = userDetails.nextLine();
			System.out.println("Enter Card number:");
			ccardNumber = userDetails.nextLine();
			memberReg.initiatePayment(cardType,ccardNumber);
		}
		
		memberReg.setMemberDetailsInDB(memberReg);
		
		//make text field for entering code too.
		System.out.println("Enter a code sent in your mail/phone for validation!!!");
		String code = userDetails.nextLine();
		try {
			if(code.equals(Encrypt.encrypt(memberReg.getLoginName()))){
				System.out.println("Registration Successful!!!");
				memberReg.changeUserStatus();
			}
			else{
				System.out.println("Code entered is wrong!!!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userDetails.close();
			
	}
	
	
}
