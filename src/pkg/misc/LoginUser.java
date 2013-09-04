/**
 * 
 */
package pkg.misc;

import pkg.database.Encrypt;
import pkg.database.PersistanceActions;

/**
 * @author redokani
 *
 */
public class LoginUser{
	String loginName;
	String password;
	Boolean ifAccountValidated;

	public LoginUser(String loginName, String password){
		this.loginName = loginName;
		this.password = password;
	}

	public boolean validateConfirmationCode(String code){
		try {
			if(code.equals(Encrypt.encrypt(loginName))){
				PersistanceActions.changeUserStatus(loginName);
				return true;
			}else{
				return false;
			}
//			userSignedIn = new LibraryMemberGUI(loginName);	
//			userSignedIn.showWelcomeMessage(loginName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public String allowLogin(){

		String status = null;
		//LoggedInUserPageGUI userSignedIn = null;
		try {
			status = PersistanceActions.userAuthenticate(loginName, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		/*if(status.startsWith("Account Not Activated")){
			UserNotActivatedGUI accNotActivated = new UserNotActivatedGUI();
			Scanner takeCode = new Scanner(System.in);
			accNotActivated.showErrorMessage(status,loginName);
			System.out.println("Enter a code:\n");
			String code = takeCode.nextLine();
			try {
				if(code.equals(Encrypt.encrypt(loginName))){
					PersistanceActions.changeUserStatus(loginName);
				}
				userSignedIn = new LibraryMemberGUI(loginName);	
				userSignedIn.showWelcomeMessage(loginName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				takeCode.close();
			}
		}
		else if(status.equalsIgnoreCase("Authentication Failed")){
			UserNotAuthenticatedGUI accNotAuth = new UserNotAuthenticatedGUI();
			accNotAuth.showErrorMessage(status);
		}
		else if(status.startsWith("AUTHENTICATED")){
			
			if(!checkifAdmin(loginName)){
				userSignedIn = new LibraryMemberGUI(loginName);					
			}
			else{
				userSignedIn = new LibraryAdminGUI(loginName);
			}

			userSignedIn.showWelcomeMessage(status.substring(13));
		}*/

		return status;

	}

	public boolean checkifAdmin(String loginName) {
		return PersistanceActions.checkIfAdmin(loginName);
	}


}
