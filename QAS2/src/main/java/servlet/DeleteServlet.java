package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataControl.Conn;
import dataControl.DBHelper;

public class DeleteServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String[] ids = req.getParameterValues("id");
		if(ids == null || ids.length < 1)
		{			
		}
		else
		{
			Connection connection = Conn.getConnection();
			DBHelper dbHelper = new DBHelper(connection);
			 int  i = dbHelper.deleteFromTable("2643537191qa1", "id", ids);
			 req.setAttribute("count", i);
			 req.getRequestDispatcher("/jsp/deleteMes.jsp").forward(req, resp);
			 
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
	
	

}
