/**
 * 
 */
package pkg.subscription;

import java.sql.Timestamp;
import java.util.regex.Matcher;


import java.util.regex.Pattern;

import com.twilio.sdk.TwilioRestException;


import pkg.misc.*;


import pkg.database.Encrypt;
import pkg.database.PersistanceActions;

/**
 * @author redokani
 *
 */
public class MemberRegistration {

	private int memberID;
	private String loginName;
	private String passWord;
	private String fullName;
	private String emailID;
	private long phoneNumber;
	private String address;
	private Timestamp startDate;	
	private long cardNumber;

	public MemberRegistration(){
		this.phoneNumber = 0;
	}

	/**
	 * @return the memberID
	 */
	public int getMemberID() {
		return memberID;
	}



	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}



	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}



	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}



	/**
	 * @return the emailID
	 */
	public String getEmailID() {
		return emailID;
	}



	/**
	 * @return the phoneNumber
	 */
	public long getPhoneNumber() {
		return phoneNumber;
	}



	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}



	/**
	 * @return the startDate
	 */
	public Timestamp getStartDate() {
		return startDate;
	}



	/**
	 * @param memberID the memberID to set
	 */
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}



	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	/**
	 * @param passWord the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}



	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	/**
	 * @param emailID the emailID to set
	 */
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}



	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}



	/**
	 * @param startDate2 the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}


	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setMemberDetailsInDB(MemberRegistration memberReg) {
		PersistanceActions.setMemberDetailsInDB(memberReg);
		sendCodeForValidation();
	}

	private void sendCodeForValidation() {		
		try{
		if(!getEmailID().equals("")){
			SendEmail.send(getEmailID(), Encrypt.encrypt(getLoginName()));	
		}
		else if(getPhoneNumber()!=0){
			try {
				SendSMS.sendSms(getPhoneNumber(), Encrypt.encrypt(getLoginName()));
			} catch (TwilioRestException e) {
				System.out.println("Cannot convert string to long!!!");
				e.printStackTrace();
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean checkDuplicateLoginName(String loginName) {
		return PersistanceActions.checkDuplicateLoginName(loginName);

	}

	public boolean isValidEmailAddress(String aEmailAddress){
		String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(aEmailAddress);

		if(m.find()) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	
	public void initiatePayment(String cardType, String cardNumber) {
		//Payment payment = new Payment();
		Payment.initiatePayment(cardType, cardNumber);		
	}

	public void changeUserStatus() {
		PersistanceActions.changeUserStatus(getLoginName());
		
	}

}
