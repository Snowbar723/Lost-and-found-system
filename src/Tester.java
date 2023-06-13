import java.sql.*;

public class Tester {

	public static void main(String[] args) {
		
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "mongroup12"; // change to your own database
		String url = server + database + "?characterEncoding=utf-8";
		String username = "mongroup12"; // change to your own user name
		String password = "hom6939"; // change to your own password
		
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			HomePageFrame homeF=new HomePageFrame(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
