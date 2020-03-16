package org.openmrs.module.hydra.api.form_service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class Utils {

	public static SimpleDateFormat formatterTimeDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static SimpleDateFormat openMrsDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static int getPatientId() {
		int toReturn = 0;
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";

		String DB_URL = "jdbc:mysql://localhost/openmrs";

		// Temp
		String USER = "pssi_user";
		String PASS = "LHQE4keg";
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT MAX(patient_id) as maxLevel FROM patient";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				toReturn = rs.getInt("maxLevel");
				System.out.println(", Last: " + toReturn);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}
		catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		}
		catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			}
			catch (SQLException se2) {} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return toReturn;
	}
}
