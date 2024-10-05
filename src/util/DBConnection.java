package util;

import java.sql.*;

public class DBConnection {
	private static Connection c;
    public static Connection getConnection(String fname) {
            try 
            {
                String cs = PropertyUtil.getConnectionString(fname);
                c = DriverManager.getConnection(cs);
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        return c;
    }
}