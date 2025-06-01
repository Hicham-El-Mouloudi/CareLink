package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccessSettings {
		
	   private static final String URL =credentials.DBCredentials.getCredentials().getURL();
	    private static final String USER = credentials.DBCredentials.getCredentials().getuser();
	    private static final String PASSWORD = credentials.DBCredentials.getCredentials().getPasswd();

	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
	
	
}
