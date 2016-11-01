import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class MainAppPstatement {
	public static String url = "jdbc:postgresql://127.0.0.1:5432/postgres?allowMultiQueries=true";
	public static String user = "postgres";
	public static String password = "";
	private static Scanner menu;
	private static Scanner nr;
	private static Scanner pwd;

	public static void main(String[] args) throws SQLException {
		while (true) {
			menu = new Scanner(System.in);
			System.out.print("Maak je keuze:\t 1) Log in \t 2) Toon klas \n");
			switch (menu.nextInt()) {
			case 1:
				login();
				break;

			case 2:
				findClass();
				break;

			default:
				System.err.println("Ongeldige keuze");
				break;
			}
		}
	}

	public static void login() throws SQLException {
		String sqlQuery = null;
		System.out.println("-------- PostgreSQL "
				+ "Check connection ----------------");
		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("No PostgreSQL JDBC Driver found! "
					+ "Please include in your library path!");
			e.printStackTrace();
			return;

		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;

		try {

			connection = DriverManager.getConnection(url, gebruiker, password);

		} catch (SQLException e) {

			System.out.println("Connection Failed!");
			System.out.println("-------- END PostgreSQL "
					+ "Connection check ------------\n");
			return;

		}

		if (connection != null) {
			System.out.println("Connected");

		} else {
			System.out.println("Failed to make a connection!");
		}
		System.out.println("-------- END PostgreSQL "
				+ "Connection check ------------");

		connection.setAutoCommit(false);
		nr = new Scanner(System.in);
		System.out.println("Voer studentnummer in:");
		String username = nr.nextLine();
		System.out.println("Je hebt het volgende studentnummer ingevoerd: "
				+ username);

		pwd = new Scanner(System.in);
		System.out.println("Voer wachtwoord in:");
		String password = pwd.nextLine();
		System.out.println("Je hebt het volgende wachtwoord ingevoerd: "
				+ password);
		sqlQuery = "SELECT * FROM student WHERE studentnummer =? and wachtwoord  = ?";
		PreparedStatement stmt = connection.prepareStatement(sqlQuery);
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet results = stmt.executeQuery();
		ResultSetMetaData rsmd = results.getMetaData();
		System.out.println("----------------------------");
		System.out.println("querying SELECT * FROM student");

		int columnsNumber = rsmd.getColumnCount();
		if (!results.next()) {
			System.out
					.println("Ingevoerde combinatie van gegevens is incorrect");
			System.out.println("----------------------------");
		} else {
			do {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = results.getString(i);
					System.out
							.print(rsmd.getColumnName(i) + ": " + columnValue);
				}
				System.out.println("");
			} while (results.next());
			System.out.println("----------------------------");
		}
	}

	public static void findClass() throws SQLException {
		String sqlQuery = null;
		System.out.println("-------- PostgreSQL "
				+ "Check connection ----------------");
		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("No PostgreSQL JDBC Driver found! "
					+ "Please include in your library path!");
			e.printStackTrace();
			return;

		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;

		try {

			connection = DriverManager.getConnection(url, gebruiker, password);

		} catch (SQLException e) {

			System.out.println("Connection Failed!");
			System.out.println("-------- END PostgreSQL "
					+ "Connection check ------------\n");
			return;

		}

		if (connection != null) {
			System.out.println("Connected");

		} else {
			System.out.println("Failed to make a connection!");
		}
		System.out.println("-------- END PostgreSQL "
				+ "Connection check ------------");

		connection.setAutoCommit(false);

		nr = new Scanner(System.in);
		System.out.println("Voer klas in:");
		String className = nr.nextLine();
		System.out.println("Je hebt de volgende klas ingevoerd: " + className);
		sqlQuery = "SELECT * FROM student WHERE entered = 'yes' AND class = ?";
		PreparedStatement stmt = connection.prepareStatement(sqlQuery);
		stmt.setString(1, className);
		ResultSet results = stmt.executeQuery();
		
		ResultSetMetaData rsmd = results.getMetaData();
		System.out.println("----------------------------");
		System.out.println("querying: " + sqlQuery);
		System.out.println("----------------------------");
		int columnsNumber = rsmd.getColumnCount();
		if (!results.next()) {
			System.out
					.println("Ingevoerde combinatie van gegevens is incorrect");
			System.out.println("----------------------------");
		} else {

			do {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = results.getString(i);
					System.out
							.print(rsmd.getColumnName(i) + ": " + columnValue);
				}
				System.out.println("");
			} while (results.next());
			System.out.println("----------------------------");
		}

	}
}
