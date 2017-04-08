package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Pager;
import data.QuestionAnswer;
import dataControl.Conn;
import dataControl.DBHelper;
import segmentation.Segment;
import segmentation.Word;

/**
 * 返回答案列表的类
 * @author zixiang huang
 *
 */
public class SearchListServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("utf8");
		Connection connection = Conn.getConnection();
		DBHelper dbHelper = new DBHelper(connection);
		String q = req.getParameter("q");
		String t = req.getParameter("type");
		int type = 2;
		if(t !=null && !t.equals(""))
		{
			type = Integer.parseInt(t);
		}
		
		String[] keys = null; //查询关键字
		if(q != null && !q.equals(""))
		{
			Segment segment = new Segment(false);
			List<Word> words = segment.seg(q);
			String question = Segment.getStringFromList(words);
			if(question != null && !question.equals("") ){
				keys = question.split(",");
			}
			
		}
		Pager page = null;
		if(req.getParameter("tr") != null && req.getParameter("cp") != null) //从请求中获取总记录条数及要查询的页码数
		{
			int tr = Integer.parseInt(req.getParameter("tr"));
			int cp = Integer.parseInt(req.getParameter("cp"));
			page = new Pager(tr, cp);
		}
		else //如果是第一次请求则查询出总记录条数
		{
			List<QuestionAnswer> answers = dbHelper.getQAFormTable("2643537191qa1", keys, type,0, 0);
			page = new Pager(answers.size(), 1);
		}
		List<QuestionAnswer> answers = dbHelper.getQAFormTable("2643537191qa1", keys, type,page.getLimitArg0(), page.getLimitArg1());
		req.setAttribute("answers", answers);
		req.setAttribute("page", page);
		req.setAttribute("q", q);
		req.setAttribute("type", type);
		Conn.releaseConnection(connection);
		req.getRequestDispatcher("/jsp/showList.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
	
	
	

}
