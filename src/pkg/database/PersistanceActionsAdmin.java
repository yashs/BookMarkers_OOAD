/**
 * 
 */
package pkg.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pkg.abstractFactory.LibraryItems;

/**
 * @author redokani
 *
 */
public class PersistanceActionsAdmin {

	static PreparedStatement ps = null;
	static ResultSet rs = null;

	public static int queryTotalFineCollected(String loginName) {
		// TODO Auto-generated method stub
		return 0;

	}

	public static Map<String,Integer> queryNoOfItems() {
		Map<String, Integer> noOfItemsCategoryWise = new HashMap<String, Integer>();
		int oldValue = 0;
		try {
			ps = Database.Get_Connection().prepareStatement("select * from Items");
			rs = ps.executeQuery();
			while (rs.next()) {
				if(!noOfItemsCategoryWise.containsKey(rs.getString("Category"))){
					noOfItemsCategoryWise.put(rs.getString("Category"), 1);
				}
				else{
					oldValue = noOfItemsCategoryWise.get(rs.getString("Category"));
					noOfItemsCategoryWise.put(rs.getString("Category"), oldValue+1);
				}

			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return noOfItemsCategoryWise;

	}

	public static int queryNoOfMembers() {
		int totalMembers = 0;
		try {
			ps = Database.Get_Connection().prepareStatement("select * from members");
			rs = ps.executeQuery();
			while (rs.next()) {
				totalMembers++;
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return totalMembers;


	}

	public static Map<String,Integer> queryNoOfItemsBorrowed() {
		Map<String, Integer> noOfItemsBorrowed = new HashMap<String, Integer>();
		int oldValue = 0;
		try {
			ps = Database.Get_Connection().prepareStatement("select * from Items where Status='B'");
			rs = ps.executeQuery();
			while (rs.next()) {
				if(!noOfItemsBorrowed.containsKey(rs.getString("Category"))){
					noOfItemsBorrowed.put(rs.getString("Category"), 1);
				}
				else{
					oldValue = noOfItemsBorrowed.get(rs.getString("Category"));
					noOfItemsBorrowed.put(rs.getString("Category"), oldValue+1);
				}

			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return noOfItemsBorrowed;
	}

	public static String queryMaxItemsChecked(String loginName) {
		return "";

	}

}
