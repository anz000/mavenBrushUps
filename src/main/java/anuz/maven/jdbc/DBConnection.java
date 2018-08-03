package anuz.maven.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private static Connection conxn;
	
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/imcs?useSSL=false";
        String user = "imcs";
        String password = "imcs";
        
        String query = "SELECT VERSION()";
        try {
        Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query); 

            if (rs.next()) {
                conxn = con;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
		return conxn;
	}
}
