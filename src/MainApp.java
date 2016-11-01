import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainApp {
	public static String url = "jdbc:postgresql://127.0.0.1:5432/postgres?allowMultiQueries=true";
	public static String username = "postgres";
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
				getklas();
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

			connection = DriverManager.getConnection(url, username, password);

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

		Statement stmt = (Statement) connection.createStatement();
	
		sqlQuery = "SELECT * FROM student WHERE studentnummer ='" + username + "' and wachtwoord  = '" + password + "' ";
		ResultSet results = stmt.executeQuery(sqlQuery);
		System.out.println("----------------------------");
		System.out.println("querying: " + sqlQuery);
		System.out.println("----------------------------");
		while(results.next()){
			String studentnummer = results.getString("studentnummer");
			String wachtwoord = results.getString("wachtwoord");
			String naam = results.getString("naam");
			String klas = results.getString("klas");
			String ingeschreven = results.getString("ingeschreven");
			System.out.println("Student: " + studentnummer + " Wachtwoord: "+ wachtwoord + " Naam: " + naam + " Klas: "+ klas + " Ingeschreven: " +ingeschreven);
		
		}
		System.out.println("----------------------------");
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

			connection = DriverManager.getConnection(url, username, password);

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

		//connection.setAutoCommit(false);

		nr = new Scanner(System.in);
		System.out.println("Voer klas in:");
		String klas = nr.nextLine();
		System.out.println("Je hebt de volgende klas ingevoerd: " + klas);

		Statement stmt = (Statement) connection.createStatement();
		sqlQuery = "SELECT * FROM student WHERE entered = 'ja' AND klas = '"
				+ klas + "'";

		ResultSet results = stmt.executeQuery(sqlQuery);
		System.out.println("----------------------------");
		System.out.println("querying: " + sqlQuery);
		System.out.println("----------------------------");
		while(results.next()){
			String studentId = results.getString("studentId");
			String password = results.getString("password");
			String name = results.getString("name");
			String classansw = results.getString("class");
			String entered = results.getString("entered");
			System.out.println("Student: " + studentId + " Wachtwoord: "+ password + " Naam: " + name + " Klas: "+ classansw + " Ingeschreven: " + entered);
			
		}
		System.out.println("----------------------------");
	}
}
