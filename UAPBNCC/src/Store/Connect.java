package Store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	
	// CREDENTIALS
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE_NAME = "ptpudding";
	
	private final String HOST = "localhost";
	private final String PORT = "3307";
	
	private final String CONNECTION = String.format("jdbc:mysql://%s:%s/%s", 
			HOST,
			PORT,
			DATABASE_NAME);
	
	private Connection connect;
	private Statement stmt;
	
	ResultSet rs;
	PreparedStatement ps;

	public Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect  = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			stmt = connect.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// SELECT
	public ResultSet getMenu() {
		try 
		{
			ps = connect.prepareStatement("SELECT * FROM menu");
			rs = ps.executeQuery();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	// INSERT 
	public ResultSet executeInsert(String MenuId, String MenuName, int MenuPrice, int MenuStock) {
		try 
		{
			ps = connect.prepareStatement("INSERT INTO menu (`MenuId`, `MenuName`, `MenuPrice`, `MenuStock`) VALUES (?,?,?,?)");
			ps.setString(1, MenuId);
			ps.setString(2, MenuName);
			ps.setInt(3, MenuPrice);
			ps.setInt(4, MenuStock);
			ps.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	// UPDATE
	public ResultSet executeUpdate(String MenuId, String MenuName, int MenuPrice, int MenuStock) {
		try 
		{
			ps = connect.prepareStatement(
					"UPDATE menu \r\n"
					+ "SET MenuName = ?, MenuPrice = ?, MenuStock = ?\r\n"
					+ "WHERE MenuId = ?");
			ps.setString(1, MenuName);
			ps.setInt(2, MenuPrice);
			ps.setInt(3, MenuStock);
			ps.setString(4, MenuId);
			ps.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return rs;
	}
	
	//DELETE
	public ResultSet executeDelete(String id)
	{
		try
		{
			ps = connect.prepareStatement("DELETE FROM menu WHERE `MenuId` = (?)");
			ps.setString(1, id);
			ps.execute();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
}
