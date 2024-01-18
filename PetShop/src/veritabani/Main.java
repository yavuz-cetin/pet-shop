package veritabani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String databaseName = "G28";
		String userName = "postgres";
		String password = "1";
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, userName,
				password);

		StartPage startPage = new StartPage(conn);
		startPage.showFrame();
	}
}