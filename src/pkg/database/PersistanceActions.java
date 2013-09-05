package pkg.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import pkg.abstractFactory.LibraryItems;

import pkg.misc.SendEmail;
import pkg.subscription.MemberRegistration;
import sun.security.action.GetLongAction;

public class PersistanceActions {
	static PreparedStatement ps = null;
	static ResultSet rs = null;

	public static String userAuthenticate(String loginName, String password)
			throws Exception {
		
		String retString = "";
		try {
			String query = "SELECT * FROM userCredentials Where LoginName='"
					+ loginName + "'";
			ps = Database.Get_Connection().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("Password").equals(password)) {
					String queryStrData = null;
					boolean userStatus = rs.getBoolean("user_status");
					if (!userStatus) {

						query = "SELECT * FROM members Where LoginName='"
								+ loginName + "'";
						ps = Database.Get_Connection().prepareStatement(query);
						rs = ps.executeQuery();
						while (rs.next()) {
							queryStrData = "Email ID: "
									+ rs.getString("EmailID");
							if (queryStrData.equals("")
									|| queryStrData.equals(null)) {
								queryStrData = "Phone Number:"
										+ rs.getString("PhoneNumber");
							}
							retString = "Account Not Activated. Please Activate your account. \nValidation Link/code is sent to "
									+ queryStrData;
						}
					} else {
						query = "SELECT FullName FROM members Where LoginName='"
								+ loginName + "'";
						ps = Database.Get_Connection().prepareStatement(query);
						rs = ps.executeQuery();
						while (rs.next()) {
							queryStrData = rs.getString("FullName");
							retString = "AUTHENTICATED" + queryStrData;
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();

		}
		if (retString.equals(""))
			retString = "Authentication Failed. Please login again!!!";

		return retString;
	}

	public static void setMemberDetailsInDB(MemberRegistration memberReg) {
		//PreparedStatement ps = null;
		try {
			// member id, loginname, password, fullname, email,phone number,
			// address, startdate,membervalidated
			String query = "INSERT into members values (?,?,?,?,?,?,?,?,?)";
			System.out.println(query);
			ps = Database.Get_Connection().prepareStatement(query);

			ps.setInt(1, 0);//memberid
			ps.setString(2, memberReg.getLoginName());
			ps.setString(3, memberReg.getFullName());
			ps.setString(4, memberReg.getEmailID());
			ps.setLong(5, memberReg.getPhoneNumber());
			ps.setString(6, memberReg.getAddress());
			ps.setTimestamp(7, memberReg.getStartDate());
			ps.setLong(8, memberReg.getCardNumber());
			ps.setInt(9, 0); //totalfine
			ps.executeUpdate();

			// User Credentials
			query = "INSERT into userCredentials values (?,?,?,?)";
			System.out.println(query);
			ps = Database.Get_Connection().prepareStatement(query);
			ps.setString(1, memberReg.getLoginName());
			ps.setString(2, memberReg.getPassWord());
			ps.setBoolean(3, false);
			ps.setBoolean(4, false);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// to do
	public static boolean validateConfirmationLink(String hashCode) {
		// TODO Auto-generated method stub

		hashCode = hashCode.replace(" ", "+");
		String[] urlParams = hashCode.split(";");
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		try {
			ps = Database.Get_Connection().prepareStatement(
					"SELECT * FROM users where name = '"
							+ urlParams[0].split("\\[")[1] + "'");
			rs = ps.executeQuery();

			while (rs.next()) {

				String ssnDB = rs.getString("ssn");
				ssnDB = ssnDB.replace(" ", "+");
				System.out.println(hashCode + "\n" + urlParams[0] + "\n"
						+ urlParams[1] + "\n" + Encrypt.encrypt(ssnDB));

				if (urlParams[1].split("]")[0].equals(Encrypt.encrypt(ssnDB))) {
					String psString = "UPDATE users SET user_status = 'true' where name = '"
							+ urlParams[0].split("\\[")[1] + "'";
					ps = Database.Get_Connection().prepareStatement(psString);
					ps.executeUpdate();
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public static List<LibraryItems> getSearchedItems(String searchkeyword) {
		List<LibraryItems> searchedItems = new ArrayList<LibraryItems>();
		String query = "select * from Items where Title='" + searchkeyword
				+ "' OR Title like '%" + searchkeyword + "%' OR Creator=  '"
				+ searchkeyword + "' group by Category";
		System.out.println(query);
		try {
			searchedItems = getItems(searchkeyword,query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return searchedItems;
	}

	private static List<LibraryItems> getItems(String searchkeyword,String query)
			throws SQLException {
		String queryItems = "";
		if (searchkeyword == null || searchkeyword.equals("")) {
			queryItems = "SELECT * FROM Items ORDER BY ItemNumber GROUP BY Type";
		}
		else{
			queryItems = query;
		}

		List<LibraryItems> searchedItems = new ArrayList<LibraryItems>();
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		try {
			ps = Database.Get_Connection().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				LibraryItems libItems = new LibraryItems();
				libItems.setItemNumber(rs.getString("ItemNumber"));
				libItems.setTitle(rs.getString("Title"));
				libItems.setCreator(rs.getString("Creator"));
				libItems.setPrice(rs.getString("Price"));
				libItems.setLocation(rs.getString("Location"));
				libItems.setType(rs.getString("Type"));
				libItems.setCategory(rs.getString("Category"));
				libItems.setStatus(rs.getString("Status"));
				searchedItems.add(libItems);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		}
		return searchedItems;

	}

	// to do
	public static void setLibraryItemsDB() {

	}

	public static boolean checkIfAdmin(String loginName) {
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		try {
			String query = "Select * from usercredentials WHERE LoginName = '"
					+ loginName + "'";
			System.out.println(query);
			ps = Database.Get_Connection().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (!rs.getBoolean("Admin")) {
					return false;
				} else {
					return true;
				}
			}

			/*
			 * libUser.setUserLoginName(rs.getString("LoginName"));
			 * libUser.setUserPassword(rs.getString("Password"));
			 * libUser.setIfAdmin(rs.getBoolean("Admin"));
			 * libUser.setUserStatus(rs.getBoolean("user_status"));
			 * ps.executeUpdate();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static boolean checkDuplicateLoginName(String loginName) {

		//PreparedStatement ps = null;
		//ResultSet rs = null;

		try {
			ps = Database.Get_Connection().prepareStatement(
					"select * from userCredentials where loginName = '"
							+ loginName + "'");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("LoginName") != null) {
					return true;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	public static void changeUserStatus(String loginName) {

		//PreparedStatement ps = null;
		try {
			String query = "UPDATE usercredentials SET user_status = '1' where LoginName = '"
					+ loginName + "'";
			ps = Database.Get_Connection().prepareStatement(query);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}


	public static void reserveItem(String loginName, String bookId) {
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		try {
			ps = Database.Get_Connection().prepareStatement(
					"select * from reservation where item_no='" + bookId + "'");
			rs = ps.executeQuery("select * from reservation where item_no='" + bookId + "'");
			if (rs.next()) {

				String userList = rs.getString("email_list");
				ps = Database.Get_Connection().prepareStatement(
						"select EmailID from members where LoginName='" + loginName + "'");
				rs = ps.executeQuery();
				if(rs.next()){
					userList = userList + ":" + rs.getString("EmailID");
				}
				ps = Database.Get_Connection().prepareStatement(
						"Update reservation set email_list = ? where item_no = '"
								+ bookId + "'");
				ps.setString(1, userList);
				ps.executeUpdate();
			} else {
				String userList ="" ;
				ps = Database.Get_Connection().prepareStatement(
						"select EmailID from members where LoginName='" + loginName + "'");
				rs = ps.executeQuery();
				if(rs.next()){
					userList = rs.getString("EmailID");
				}
				
				ps = Database.Get_Connection().prepareStatement(
						"Insert into reservation values (?, ?)");
				ps.setString(1, bookId);
				ps.setString(2, userList);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String checkOutItems(String loginName, String itemId) {
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		String status = null;
		try {
			//validate if user can checkout or not


			//1. see if this book is available
			//2.check if user has to pay fine
			ps = Database.Get_Connection().prepareStatement(
					"select Status from items where ItemNumber='"+itemId+"'");
			rs = ps.executeQuery();
			while (rs.next())
			{	
				if(rs.getString("Status").equalsIgnoreCase("B")){
					status = "This item is already borrowed!!!";
					return status;
				}
			}

			//2.check if user has to pay fine
			ps = Database.Get_Connection().prepareStatement(
					"select TotalFine from members where loginName = '" + loginName
					+ "'");
			rs = ps.executeQuery();
			while (rs.next())
			{	
				if(rs.getInt("TotalFine")!= 0){
					status = "Fine Not paid!!!";
					return status;
				}
			}


			//3.check how many NF,F and V checked out
			ps = Database.Get_Connection().prepareStatement(
					"select * from transaction where login_name = '" + loginName
					+ "' AND ISNULL(return_date)");
			rs = ps.executeQuery();
			int fiction=0,non_fiction=0,video=0;
			while(rs.next()){
				if(rs.getString("item_no").startsWith("NF")){
					non_fiction += 1;

				}
				else if(rs.getString("item_no").startsWith("F")){
					fiction += 1;

				}
				else if(rs.getString("item_no").startsWith("V")){
					video += 1;					
				}
			}
			if(itemId.startsWith("NF")){
				non_fiction +=1;
			}
			else if(itemId.startsWith("F")){
				fiction +=1;
			}
			else if(itemId.startsWith("V")){
				video += 1;
			}

			if((non_fiction > 3) || fiction > 2 || video > 2){
				status = "Limit already reached. No more Items can be reached!!!";
				return status;
			}

			//if everything is fine then check out process starts
			ps = Database.Get_Connection().prepareStatement(
					"select * from members where loginName = '" + loginName
					+ "'");
			rs = ps.executeQuery();
			int cardNumber = 0;
			while (rs.next())
			{	
				cardNumber = rs.getInt("CardNumber");
			}
			ps = Database.Get_Connection().prepareStatement("select Title from items where ItemNumber = '" + itemId
					+ "'");
			rs = ps.executeQuery();
			String itemTitle = null;
			while(rs.next()){
				itemTitle = rs.getString("Title");
			}

			ps = Database.Get_Connection().prepareStatement(
					"Insert into transaction values (?,?, ?, ?, ?, ?, ? , ?, ?,?)");
			ps.setInt(1,0);
			ps.setString(2, loginName);
			ps.setInt(3, cardNumber);
			ps.setString(4, itemId);
			ps.setString(5, itemTitle);
			Timestamp time = new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(6, time);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time.getTime());
			if(itemId.startsWith("V")){
				cal.add(Calendar.WEEK_OF_YEAR, 1);				
			}
			else{
				cal.add(Calendar.WEEK_OF_YEAR, 2);				
			}
			ps.setTimestamp(7, new Timestamp(cal.getTimeInMillis()));
			ps.setTimestamp(8, null);
			ps.setInt(9, 0);
			ps.setInt(10, 0);
			ps.executeUpdate();

			//change items table status to Borrowed
			ps = Database.Get_Connection().prepareStatement(
					"UPDATE items SET Status = 'B' where ItemNumber = '"
							+ itemId+"'");
			ps.executeUpdate();
			status = "checkout!";

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;

	}

	public static int elapsed(Calendar before, Calendar after, int field) {
		Calendar clone = (Calendar) before.clone(); // Otherwise changes are
		// been reflected.
		int elapsed = -1;
		while (!clone.after(after)) {
			clone.add(field, 1);
			elapsed++;
		}
		return elapsed;
	}

	public static List<Transaction> displayBorrowedItems(String loginName){
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		List<Transaction> itemsBorrowed = new ArrayList<Transaction>();
		Transaction userItemsDetails = null;
		String query = "select * from transaction where login_name='" + loginName +"' AND  ISNULL(return_date)";
		System.out.println(query);
		try {
			ps = Database.Get_Connection().prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				userItemsDetails = new Transaction();
				userItemsDetails.setLoginName(loginName);
				userItemsDetails.setItemId(rs.getString("item_no"));
				userItemsDetails.setItemTitle(rs.getString("item_title"));
				userItemsDetails.setCheckoutDate(rs.getTimestamp("checkout_date"));
				userItemsDetails.setDueDate(rs.getTimestamp("due_date"));
				userItemsDetails.setFineAmountDue(rs.getInt("fine_amt_due"));
				userItemsDetails.setFineAmountPaid(rs.getInt("fine_amt_paid"));

				itemsBorrowed.add(userItemsDetails);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemsBorrowed;
	}
	
	static List<Transaction> itemsBorrowed = new ArrayList<Transaction>();
	
	public static List<Transaction> displayQueryTransactionResults(String loginName, String month, String year){
		itemsBorrowed = new ArrayList<Transaction>();
		String query = "select * from transaction where login_name='" + loginName +"'";
		
		System.out.println(query);
		try {
			ps = Database.Get_Connection().prepareStatement(query);
			rs = ps.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			while(rs.next()){

				Date checkOutDate = rs.getTimestamp("checkout_date");
				String dateStr = sdf.format(checkOutDate);
				String[] dataStrIn = dateStr.split("-");
				
				if(month.equals("") && year.equals("")){
					populateQueryTransaction(loginName, itemsBorrowed);
				}else if(year.equals(dataStrIn[0]) && month.equals("")){
					populateQueryTransaction(loginName, itemsBorrowed);
				}else if(dataStrIn[0].equals(year) && dataStrIn[1].equals(month)){
					populateQueryTransaction(loginName, itemsBorrowed);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemsBorrowed;

	}
	
	private static void populateQueryTransaction(String loginName, List<Transaction> itemsBorrowed){
		
		try{
		Transaction userItemsDetails = new Transaction();
		userItemsDetails.setLoginName(loginName);
		userItemsDetails.setItemId(rs.getString("item_no"));
		userItemsDetails.setItemTitle(rs.getString("item_title"));
		userItemsDetails.setCheckoutDate(rs.getTimestamp("checkout_date"));
		userItemsDetails.setDueDate(rs.getTimestamp("due_date"));
		userItemsDetails.setFineAmountDue(rs.getInt("fine_amt_due"));
		userItemsDetails.setFineAmountPaid(rs.getInt("fine_amt_paid"));
		itemsBorrowed.add(userItemsDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void returnItems(String loginName, String itemId) {
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		try {
			ps = Database.Get_Connection().prepareStatement(
					"select * from transaction where login_name = '"
							+ loginName + "' AND item_no = '" + itemId + "' AND ISNULL(return_date)");
			rs = ps.executeQuery();
			int fine = 0;
			while (rs.next()) {
				Timestamp dueDate = rs.getTimestamp("due_date");
				Calendar dueDateCal = Calendar.getInstance();
				dueDateCal.setTimeInMillis(dueDate.getTime());

				Calendar retDate = Calendar.getInstance();
				retDate.setTimeInMillis(System.currentTimeMillis());
				Calendar clone = (Calendar) dueDateCal.clone();


				int lateBy = elapsed(clone, retDate, Calendar.DATE);

				if(lateBy >0){
					if(lateBy > 2){
						if(rs.getString("item_no").startsWith("NF")){
							fine = 30 * lateBy;	
						}
						else if(rs.getString("item_no").startsWith("F")){
							fine = 15*lateBy;
						}
					}
					if(lateBy > 1){
						if(rs.getString("item_no").startsWith("V")){
							fine = 50 * lateBy;						
						}
					}



					//check fine should not exceed item price
					ps = Database.Get_Connection().prepareStatement(
							"select * from items where ItemNumber = '" + itemId + "'");
					rs = ps.executeQuery();
					while(rs.next()){
						if(fine > rs.getInt("Price")){
							fine = rs.getInt("Price");
						}
					}

					//update return date in transaction history
					ps = Database.Get_Connection().prepareStatement(
							"UPDATE transaction SET fine_amt_due = '"+fine+"' AND fine_amt_paid = '0' where login_name = '"
									+ loginName+"' AND item_no = '" + itemId + "'");
					ps.executeUpdate();

				}
				Timestamp retDatenew = new Timestamp(System.currentTimeMillis());			
				ps = Database.Get_Connection().prepareStatement(
						"UPDATE transaction SET return_date = '"+retDatenew+"' where login_name = '"
								+ loginName+"' AND item_no = '" + itemId + "'");
				ps.executeUpdate();

				//udpate status of items table too
				//change items table status to Borrowed
				ps = Database.Get_Connection().prepareStatement(
						"UPDATE items SET Status = 'Av' where ItemNumber = '"
								+ itemId+"'");
				ps.executeUpdate();
			}
			
			String query = "select * from reservation where item_no ='" + itemId +"'";
			rs = ps.executeQuery(query);
			while(rs.next()){
				String[] emails = rs.getString("email_list").split(":");
				for(int i=0 ; i<emails.length ; i++){
					SendEmail.send(emails[i], "Mail From BookMarkers:\t" + itemId);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public static List<Transaction> getAllCheckOutItems(String loginName) {
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		List<Transaction> getAllCheckedOutDetails = new ArrayList<Transaction>();
		Transaction userItemsDetails = null;
		try {
			ps = Database.Get_Connection().prepareStatement(
					"select * from members where loginName = '" + loginName
					+ "'");
			rs = ps.executeQuery();
			int cardNumber = 0;
			while (rs.next())
			{	
				cardNumber = rs.getInt("CardNumber");
				//userItemsDetails.setLoginName(loginName);

			}
			ps = Database.Get_Connection().prepareStatement(
					"select * from transaction where login_name = '" + loginName
					+ "' AND ISNULL(return_date)");
			rs = ps.executeQuery();
			while(rs.next()){
				userItemsDetails = new Transaction();
				userItemsDetails.setLoginName(loginName);
				userItemsDetails.setMemberCard(Integer.toString(cardNumber));
				userItemsDetails.setItemId(rs.getString("item_no"));
				userItemsDetails.setItemTitle(rs.getString("item_title"));
				userItemsDetails.setCheckoutDate(rs.getTimestamp("checkout_date"));
				userItemsDetails.setDueDate(rs.getTimestamp("due_date"));
				userItemsDetails.setFineAmountDue(rs.getInt("fine_amt_due"));
				userItemsDetails.setFineAmountPaid(rs.getInt("fine_amt_paid"));

				getAllCheckedOutDetails.add(userItemsDetails);
			}
			return getAllCheckedOutDetails;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static int calculateFines(String loginName) {
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		int totalFine = 0;
		try {
			ps = Database.Get_Connection().prepareStatement(
					"select * from transaction where login_name = '"
							+ loginName + "'");
			rs = ps.executeQuery();
			
			Calendar dueDateCal,retDateCal = null,clone;
			while (rs.next()) {
				Timestamp dueDate = rs.getTimestamp("due_date");
				dueDateCal = Calendar.getInstance();
				dueDateCal.setTimeInMillis(dueDate.getTime());


				if(rs.getTimestamp("return_date")!=null){
					totalFine += rs.getInt("fine_amt_due");
					/*Timestamp retDate = rs.getTimestamp("return_date");
					retDateCal = Calendar.getInstance();
					retDateCal.setTimeInMillis(retDate.getTime());*/
				}
				else{
					retDateCal = Calendar.getInstance();
					retDateCal.setTimeInMillis(System.currentTimeMillis());					


					clone = (Calendar) dueDateCal.clone();

					int lateBy = elapsed(clone, retDateCal, Calendar.DATE);
					int fine=0;
					if(lateBy > 0){
						if(lateBy > 2){
							if(rs.getString("item_no").startsWith("NF")){
								fine = 30 * lateBy;	
							}
							else if(rs.getString("item_no").startsWith("F")){
								fine = 15*lateBy;
							}
						}
						if(lateBy > 1){
							if(rs.getString("item_no").startsWith("V")){
								fine = 50 * lateBy;						
							}
						}
						ResultSet rs_chk = null;
						//check fine should not exceed item price
						PreparedStatement ps_chk = Database.Get_Connection().prepareStatement(
								"select * from items where ItemNumber = '" + rs.getString("item_no") + "'");
						rs_chk = ps_chk.executeQuery();
						while(rs_chk.next()){
							if(fine > rs_chk.getInt("Price")){
								fine = rs_chk.getInt("Price");
							}
						}
						rs_chk.close();
						ps_chk.close();
						totalFine += fine;
						ps = Database.Get_Connection().prepareStatement(
								"UPDATE transaction SET fine_amt_due = "+fine+" where login_name = '"
										+ loginName+"' AND item_no = '" + rs.getString("item_no") + "'");
						ps.executeUpdate();
					}
				}


			}
			ps = Database.Get_Connection().prepareStatement(
					"UPDATE members SET TotalFine = '"+totalFine+"' where LoginName = '"
							+ loginName+"'");
			ps.executeUpdate();



		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return totalFine;

	}

	public static String payFine(String loginName) {
		//PreparedStatement ps = null;
		//ResultSet rs = null;
		String payFine = "";
		try {
			ps = Database.Get_Connection().prepareStatement(
					"select * from members where loginName = '"
							+ loginName + "'");
			rs = ps.executeQuery();
			while (rs.next()) {
				payFine += rs.getString("CardNumber");
				payFine += ":";
				payFine += rs.getString("TotalFine"); //total fine is getting clear all at once
			}
			payFine += ":Cleared";

			ps = Database.Get_Connection().prepareStatement(
					"select * from transaction where login_name = '"
							+ loginName + "' AND fine_amt_due != 0");
			rs = ps.executeQuery();

			while(rs.next()){
				PreparedStatement ps_update = Database.Get_Connection().prepareStatement(
						"UPDATE transaction SET fine_amt_paid = '"+rs.getInt("fine_amt_due")+"', fine_amt_due = 0 where login_name = '"
								+ loginName+"' AND item_no = '"+rs.getString("item_no")+"'");
				ps_update.executeUpdate();

				//update return date to now whose return date is not updated
				//assuming as soon fine is paid, item is returned
				if(rs.getString("return_date")==null){
					Timestamp retDate = new Timestamp(System.currentTimeMillis());	
					ps_update = Database.Get_Connection().prepareStatement(
							"UPDATE transaction SET return_date = '"+retDate+"' where login_name = '"
									+ loginName+"' AND item_no = '"+rs.getString("item_no")+"'");
					ps_update.executeUpdate();

					//udpate status of items table too
					//change items table status to Borrowed
					ps_update = Database.Get_Connection().prepareStatement(
							"UPDATE items SET Status = 'Av' where ItemNumber = '"
									+ rs.getString("item_no")+"'");
					ps_update.executeUpdate();
				}

			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return payFine;
	}



	//Persistance Actions for Member


	//Persistence Actions for Admin
}
