package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.QuestionAnswer;
import dataControl.Conn;
import dataControl.DBHelper;
/**
 * 处理修改请求
 * @author zixiang huang
 *
 */
public class ModificationServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String id = req.getParameter("i");
		String q = req.getParameter("q");
		String a = req.getParameter("a");
		QuestionAnswer questionAnswer = new QuestionAnswer(q, a);
		int count = 0;
		if(id != null && !id.equals("")){
			Connection connection = Conn.getConnection();
			DBHelper dbHelper = new DBHelper(connection);
			count = dbHelper.modifyQA("2643537191qa1", Integer.parseInt(id), questionAnswer);
			Conn.releaseConnection(connection);
			req.setAttribute("count", count);
			req.getRequestDispatcher("/jsp/answerContent.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
	
	

}
