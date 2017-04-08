package teacherAgent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.mysql.jdbc.Statement;
import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;
import com.scienjus.smartqq.model.UserInfo;

import data.QuestionAnswer;
import data.Request;
import data.Response;
import data.TeacherInfo;
import dataControl.Conn;
import dataControl.DBHelper;
import segmentation.Segment;
import segmentation.Word;
import textSimilarity.CosineTextSimilarity;
import textSimilarity.Similarity;

/**
 * 教师代理类，可通过此类构建一个能够实现自动回复的QQ智能代理
 * @author zixiang huang
 *
 */
public class TeacherAgent
{
	private TeacherInfo teacherInfo = null;
	private SmartQQClient smartQQClient = null;
	private MessageCallback messageCallback = null;
	
	/**
	 * 初始化一个教师代理，完成初始化后调用 teacherAgentLogin() 方法进行代理QQ的登陆
	 * 
	 * @param teacherQQ - 教师QQ
	 */
	public TeacherAgent(String teacherQQ)
	{
		initTeacherInfo(teacherQQ);
	}
	
	/**
	 * 根据初始化的教师代理进行登陆
	 * @return 
	 */
	public String teacherAgentLogin()
	{
		messageCallback = new CallBack();
		smartQQClient = new SmartQQClient(messageCallback);
		return checkAccount();
	}
	
	
	
	private String checkAccount()
	{
		UserInfo userInfo = smartQQClient.getAccountInfo();
		if(!userInfo.getAccount().equals(teacherInfo.getTeacherAgentQQ()))
		{
			try
			{
				smartQQClient.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return "用户信息不符，登陆失败";
		}
		return "教师代理QQ"+userInfo.getAccount()+"登陆成功";
	}


		//根据教师QQ初始化教师在数据库中的其他相应信息
	private void initTeacherInfo(String teacherQQ)
	{
		teacherInfo = new TeacherInfo();
		Connection connection = Conn.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = (Statement) connection.createStatement();
			String sql = "select * from teacherinfo where teacher_qq="+teacherQQ;
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				teacherInfo.setTeacherQQ(teacherQQ);
				teacherInfo.setTeacherName(resultSet.getString("teacher_name"));
				teacherInfo.setTeacherDescription(resultSet.getString("teacher_description"));
				teacherInfo.setTeacherAgentQQ(resultSet.getString("teacher_agent_qq"));
				teacherInfo.setPrimaryQuestionAnswerTable(resultSet.getString("primary_qa_table"));
				teacherInfo.setSubQuestionAnswerTables(resultSet.getString("sub_qa_table"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(statement != null && !statement.isClosed())
				{
					resultSet.close();
					statement.close();
					Conn.releaseConnection(connection);
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从数据库中返回答案，若没有则将问题保存在问题库中
	 * @param request - 问题请求
	 * @param groupId - 发起请求的群id
	 */
	private void showAnswerFromDB(Request request,long groupId)
	{
		//将查询操作放在新线程中
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				String question = request.getRequestContent();
				QuestionAnswer questionAnswer = null; //最终匹配项
				float simi = 0; 	//最终匹配项的相似度
				QuestionAnswer questionAnswer2 = null; //当前匹配项
				float simi2 = 0;  	//当前匹配项的相似度
				Segment segment = new Segment(false);
				List<Word> words = segment.seg(question); //问题分词结果
				
				Connection connection = Conn.getConnection();
				DBHelper dbHelper = new DBHelper(connection);				
				Similarity similarity = new CosineTextSimilarity();
				
				//从自带的知识表中搜索
				questionAnswer = getBestMatchFromTable(dbHelper, 
						teacherInfo.getPrimaryQuestionAnswerTable(), words);
				if(questionAnswer != null)
				{
					simi = similarity.getSimilarity(words, 
							Segment.getListFromString(questionAnswer.getQuestionSegment()));
				}

				//遍历需要查询的所有附属知识表
				String subTables = teacherInfo.getSubQuestionAnswerTables();
				if(subTables != null && !subTables.equals(""))
				{
					for (String tableName : subTables.split(","))
					{
						questionAnswer2 = getBestMatchFromTable(dbHelper, tableName, words);
						if(questionAnswer2 == null || questionAnswer2.getAnswer() == null)
						{
							continue;
						}
						
						if( !questionAnswer2.getAnswer().equals(""))
						{
							List<Word> words2 = Segment.getListFromString(questionAnswer2.getQuestionSegment());
							simi2 = similarity.getSimilarity(words, words2);
							if(simi2 > simi)
							{
								questionAnswer = questionAnswer2;
								simi = simi2;
							}
							
						}
					}
				
				}
			
				if(questionAnswer != null && questionAnswer.getAnswer() != null)//找到答案
				{
					smartQQClient.sendMessageToGroup(groupId, questionAnswer.getAnswer());
				}
				else if(questionAnswer == null )//没找到答案，并且库中没有类似问题
				{					
					smartQQClient.sendMessageToGroup(groupId, 
							Response.DEFAULT_ANSWERS[new Random().nextInt(Response.DEFAULT_ANSWERS.length)]);
					appendQuestion(dbHelper, teacherInfo.getPrimaryQuestionAnswerTable(), words, request);
				}
				else//没找到答案，但是问题库中已有类似问题
				{
					smartQQClient.sendMessageToGroup(groupId, 
							Response.DEFAULT_ANSWERS[new Random().nextInt(Response.DEFAULT_ANSWERS.length)]);
				}
				
			}
		}).start();
		
		
	}
	
	/**
	 * 从一张表中搜索答案,没有找到则返回空
	 * @param dbHelper - 数据库帮助类
	 * @param tableName - 表名
	 * @param words - 关键词分词结果
	 * @return
	 */
	private QuestionAnswer getBestMatchFromTable(DBHelper dbHelper,String tableName,List<Word> words)
	{
		QuestionAnswer answer = null;
		float textSimilarity = 0;
		float currentSimi = 0;
		Similarity similarity = new CosineTextSimilarity();
		List<QuestionAnswer> answers = dbHelper.getQAFormTable(tableName, 
				Segment.getStringFromList(words).split(","),2,
				0, 5);
		for (QuestionAnswer answer2 : answers)
		{
			currentSimi = similarity.getSimilarity(
					Segment.getListFromString(answer2.getQuestionSegment()), words);
			if(currentSimi > textSimilarity)
			{
				textSimilarity = currentSimi;
				answer = answer2;
			}
		}
		if(textSimilarity >= Similarity.LOWEST_SIMILARITY1)
			return answer;
			
		return null;
		
	}
	
	/**
	 * 将问题加入到问题库中
	 * @param dbHelper - 数据库帮助类
	 * @param tableName  - 表名
	 * @param words - 问题分词结果
	 * @param request - 与问题请求相关的信息
	 */
	private void appendQuestion(DBHelper dbHelper,String tableName,List<Word> words,Request request)
	{
		Calendar calendar  = Calendar.getInstance();
		String date = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+
				calendar.get(Calendar.DAY_OF_YEAR);
		dbHelper.insertTotable(tableName,
					new String[]{"question","q_segment","questioner","date"}, 
					new String[]{request.getRequestContent(),Segment.getStringFromList(words),
							request.getRequestor(),date});	
	}
	
	private void destroy()
	{
		teacherInfo = null;
		messageCallback = null;
		if(smartQQClient != null)
		{
			try
			{
				smartQQClient.close();
				smartQQClient = null;
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}


	/**
	 * 接收到QQ消息时的回调
	 * @author zixiang haung
	 *
	 */
	private class CallBack implements MessageCallback
	{

		@Override
		public void onMessage(Message message) //收到私聊消息时的回调
		{
			//只对教师的私聊消息进行回应
			if(teacherInfo.getTeacherQQ().equals(smartQQClient.getQQById(message)+""))
			{
				Request request = Request.createRequest(null, message.getContent());
				if(request == null)
					return;
				switch (request.getRequestType())
				{
					case ANSWER_REQUEST:

						break;
					case HELP_REQUEST:
						smartQQClient.sendMessageToFriend(message.getUserId(), Response.DEFAULT_HELP_TEACHER);
						break;
					case CLOSE_REQUEST:
						smartQQClient.sendMessageToFriend(message.getUserId(),
								"机器人代理（" + teacherInfo.getTeacherAgentQQ() + "）\n已下线......");
						destroy();
						break;

					default:
						break;
				}
			}
			
		}

		@Override
		public void onGroupMessage(GroupMessage message)//收到群消息时的回调
		{
			Request request = Request.createRequest(smartQQClient.getQQById(message)+"", 
					message.getContent());
			if(request == null)
				return;
			switch (request.getRequestType())
			{
				case HELP_REQUEST:
					smartQQClient.sendMessageToGroup(message.getGroupId(), Response.DEFAULT_HELP_STUDENT);
					break;
				case ANSWER_REQUEST:
					showAnswerFromDB(request,message.getGroupId());
					break;

				default:
					break;
			}

		}

		@Override
		public void onDiscussMessage(DiscussMessage message)//收到讨论组消息时的回调
		{
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(String[] args)
	{
		TeacherAgent teacherAgent = new TeacherAgent("2643537191");
		teacherAgent.teacherAgentLogin();
	}
}
