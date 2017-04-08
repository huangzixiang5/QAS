package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataControl.Conn;
import dataControl.DBHelper;

/**
 * 检查用户登录合法性
 * @author zixiang huang
 *
 */
public class AccountServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String account = req.getParameter("username");
		String password = req.getParameter("password");
		if(account == null || password == null )
		{
			
		}
		else
		{
			account = account.trim();
			password = password.trim();
			Connection connection = Conn.getConnection();
			DBHelper dbHelper = new DBHelper(connection);
			dbHelper.checkTeacherInfo("teacherinfo", new String[]{}, values)
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	

}
