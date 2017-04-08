package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.QuestionAnswer;
import dataControl.Conn;
import dataControl.DBHelper;
import segmentation.Segment;
import segmentation.Word;
import textSimilarity.CosineTextSimilarity;
import textSimilarity.Similarity;

/**
 * 处理添加操作
 * @author zixiang huang
 *
 */
public class AdditionServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("utf8");
		String question = req.getParameter("q");
		String answer = req.getParameter("a");
		int count =0;
		String cover  = req.getParameter("cover");
		if(question != null && !question.equals("") && answer != null && !answer.equals(""))
		{
			QuestionAnswer questionAnswer = new QuestionAnswer(question,answer);
			
			Connection connection = Conn.getConnection();
			DBHelper dbHelper = new DBHelper(connection);
			QuestionAnswer questionAnswer2  = getSimilarQA(dbHelper, "2643537191qa1", question);
			if(questionAnswer2 != null)//已有相似记录
			{
				if(cover != null && cover.contains("true")) //覆盖原有记录
				{
					dbHelper.deleteFromTable("2643537191qa1", "id", new String[]{questionAnswer2.getId()+""});
					questionAnswer.setId(questionAnswer2.getId()); //将原有id替换
					 count = dbHelper.insertQA("2643537191qa1", questionAnswer); //插入数据
				}
				else if(cover != null && cover.contains("false")) //添加并保留原有记录
				{
					 count = dbHelper.insertQA("2643537191qa1", questionAnswer);
				}
				else 
				{
					req.setAttribute("existingQA", questionAnswer2);
					req.setAttribute("currentQA", questionAnswer);
				}
				
			}
			else//未有相似记录
			{
				 count = dbHelper.insertQA("2643537191qa1", questionAnswer);
			}
			req.setAttribute("count",count); //添加的记录数
			req.getRequestDispatcher("/jsp/additionMes.jsp").forward(req, resp);
		}
		else
		{
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
	
	private QuestionAnswer getSimilarQA(DBHelper dbHelper,String tableName,String question)
	{
		Segment segment = new Segment(false);
		List<Word> words  =segment.seg(question);
		String wString = Segment.getStringFromList(words);
		
		if(wString == null || wString.equals(""))
		{
			return null;
		}
		Similarity similarity = new CosineTextSimilarity();
		List<QuestionAnswer> questionAnswers  = dbHelper.getQAFormTable(tableName, 
				wString.split(","), 2, 0, 1);
		if(questionAnswers != null && questionAnswers.size()>0)
		{
			String qString = questionAnswers.get(0).getQuestionSegment();
			if(similarity.getSimilarity(words, Segment.getListFromString(qString)) > Similarity.LOWEST_SIMILARITY1)
			{
				return questionAnswers.get(0);
			}
		}
		return null;
	}
	

}
