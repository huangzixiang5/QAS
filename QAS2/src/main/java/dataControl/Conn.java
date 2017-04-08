package dataControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conn
{
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";
	private static final String DATABASE_NAME = "iqas";
	private static final String URL = "jdbc:mysql://localhost:3306/"
										+ DATABASE_NAME
											+ "?useUnicode=true&characterEncoding=utf-8";

	public static Connection getConnection()
	{
		try
		{
				Class.forName("com.mysql.jdbc.Driver").newInstance();

		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			return connection;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void releaseConnection(Connection connection)
	{
		try
		{
			if(connection != null && !connection.isClosed())
			{
				connection.close();
				connection = null;
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Connection connection = Conn.getConnection();
		try
		{
			ResultSet resultSet = connection.createStatement().executeQuery("select * from 1634895469qa where match(pinyin) against('武汉')");
			while(resultSet.next())
			{
				System.out.println(resultSet.getString("answer"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
