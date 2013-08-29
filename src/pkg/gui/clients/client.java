/**
 * 
 */
package pkg.gui.clients;


import java.sql.SQLException;

import java.util.Scanner;


import pkg.abstractFactory.NonMemberUser;
import pkg.abstractFactory.User;
import pkg.database.Database;
import pkg.misc.LoginUser;

/**
 * @author redokani
 *
 */
public class client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//GUI first screen with sign up button, sign in button, two textfields taking input for name and 
		//password and two labels with login name and password
		Scanner input = new Scanner(System.in);
		try{	

			System.out.println("Enter your option : 1 for Sign up, 2 for Sign in, 3 for Search");
			int option = input.nextInt();

			if(option == 1){
				//sign up
				System.out.println("Registration page");
				RegistrationPageGUI regDetails = new RegistrationPageGUI();
				regDetails.registerMember();
			}

			//sign in
			else if(option == 2){
				System.out.println("Enter Login Name:");
				String loginName = input.nextLine();
				loginName = input.nextLine();
				System.out.println("Enter Password:");
				String password = input.nextLine();

				LoginUser loginMember = new LoginUser(loginName, password);
				loginMember.allowLogin();
			}

			else if(option == 3){
				//search
				System.out.println("Enter search options: Search By Keyword/Location/Title/Author/Keyword in a title\n");
				String keyword = input.nextLine();
				User userObjForSearch = new NonMemberUser();
				userObjForSearch.searchItems(keyword);
			}

			else{
				System.out.println("wrong option!!!");
			}

		}
		catch (Exception e){

		}
		finally{
			try {
				Database.Get_Connection().close();
				input.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
