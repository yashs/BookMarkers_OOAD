package pkg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public static Connection Get_Connection() throws Exception
	{
		Connection connection = null;
		if(connection == null){
			try
			{
				String connectionURL = "jdbc:mysql://localhost:3306/bookmarkers";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(connectionURL, "root", "root");
				
			}
			catch (SQLException e)
			{
				throw e;	
			}
			catch (Exception e)
			{
				throw e;	
			}
		}
		return connection;
	}
	
	

}
