package dataControl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.mysql.jdbc.Statement;

import data.QuestionAnswer;
import data.TeacherInfo;

/**
 * 封装了对数据库的一些简单操作
 * @author zixiang huang
 *
 */
public class DBHelper
{
	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;
	
	public DBHelper(Connection connection)
	{
		if(connection != null)
			this.connection = connection;
		else
			System.out.println("DBHelper-------Connection为空");
	}

	/**
	 * 通过全文索引进行快速搜索
	 * @param tableName - 需要搜索的表名
	 * @param type -返回数据类型，0则只返回没有解答的数据，1则只返回有解答的数据，其他则返回所有数据
	 * @param columnNames - 需要返回的列名
	 * @param fullTextIndex - 已建立全文索引的列名
	 * @param keyWords - 需要进行模糊匹配的关键词,为null时表示返回全部数据
	 * @param limitIndex - 从第几条数据开始返回，第一条为0
	 * @param limitCount - 限制返回数据数目 (等于零时不限制)
	 * @return
	 */
	private ResultSet searchWithMath(String tableName,int type,String[] columnNames,
			String fullTextIndex,String[] keyWords, int limitIndex,int limitCount)
	{
		StringBuffer stringBuffer = new StringBuffer(); //构造SQL语句
		
		if(connection == null)
		{			
			System.out.println("DBHelper-------Connection为空");
			return null;
		}		
		stringBuffer.append("SELECT ");
		for(int i = 0;i<columnNames.length;i++)
		{
			stringBuffer.append(columnNames[i]+",");
		}
		
		stringBuffer.deleteCharAt(stringBuffer.length()-1);
		stringBuffer.append(" from "+tableName+" where 1=1");
		
		if(type == 0) //根据查询要求判断查询的是问题还是解答
		{
			stringBuffer.append(" and answer is null ");
		}
		else if(type == 1)
		{
			stringBuffer.append(" and answer is not null ");
		} 
		
		//判断需要匹配的关键从是否为空
		if(keyWords == null )
		{
			stringBuffer.append(" order by ").append(columnNames[0]);
		}
		else //不为空则加上全文索引
		{
			stringBuffer.append(" and match("+fullTextIndex+") against('");
			for(int j = 0;j < keyWords.length;j++)
			{
				stringBuffer.append(keyWords[j]+" "); //SQL against函数关键词，中间用空格隔开
			}
			stringBuffer.append("')");
		}		
		
		if(limitCount > 0)
		{
			stringBuffer.append(" limit ").append(limitIndex).append(",").append(limitCount);
		}

		try
		{		
			statement = (Statement) connection.createStatement();
			return statement.executeQuery(stringBuffer.toString());
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally {
		}
		return null;
	}
	
	/**
	 * 向表中插入一条数据
	 * @param tableName - 表名
	 * @param columnNames - 列名
	 * @param values - 与列名对应的列值
	 */
	public int insertTotable(String tableName,String[] columnNames,String[] values)
	{
		StringBuffer stringBuffer = new StringBuffer(); //构造SQL语句
		
		if(connection == null)
		{			
			System.out.println("DBHelper-------Connection为空");
			return 0;
		}
		
		if(columnNames != null)
		{
		
		stringBuffer.append("insert into "+tableName+"(");
		for(int i = 0;i<columnNames.length;i++)
		{
			stringBuffer.append(columnNames[i]+",");
		}
		stringBuffer.deleteCharAt(stringBuffer.length()-1);
		stringBuffer.append(") values(");
		
		}
		else 
		{
			stringBuffer.append("insert into ").append(tableName).append(" values(");
		}
		
		for(int i = 0;i<values.length;i++)
		{
			if(values[i]!=null) //防止插入“null"字符串
				stringBuffer.append("'"+values[i]+"',");
			else 
			{
				stringBuffer.append(values[i]+",");
			}
		}
		stringBuffer.deleteCharAt(stringBuffer.length()-1);
		stringBuffer.append(")");
		try
		{
			statement = (Statement) connection.createStatement();
			return statement.executeUpdate(stringBuffer.toString());
		} catch (SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
		finally
		{
			try
			{
				if(statement != null && !statement.isClosed())
				{
					statement.close();
					statement = null;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 向表中插入QuestionAnswer 对象
	 * @param tableName -表名
	 * @param qA -要插入的对象
	 * @return -插入记录数
	 */
	public int insertQA(String tableName,QuestionAnswer qA)
	{
		return insertTotable(tableName, null
		, new String[]{qA.getId()+"",qA.getQuestion(),qA.getQuestioner(),qA.getQuestionSegment(),qA.getAnswer(),qA.getDate()});
	}
	
	/**
	 * 删除表中的一条数据
	 * @param tableName - 表名
	 * @param columnName - 列名
	 * @param values - 对应列值
	 */
	public int deleteFromTable(String tableName,String columnName,String[] values)
	{
		if(connection == null)
		{			
			System.out.println("DBHelper-------Connection为空");
			return 0;
		}
		StringBuilder stringBuilder  =new StringBuilder();
		try
		{
			if(values != null && values.length >0)
			{
				stringBuilder.append("delete from ").append(tableName).append(" where ")
				.append(columnName).append(" in(");
				for(int i = 0; i<values.length ;i++)
				{
					stringBuilder.append("'").append(values[i]).append("',");
				}
				stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length()).append(")");
				statement = (Statement) connection.createStatement();
				return statement.executeUpdate(stringBuilder.toString());
			}
			else
				return 0 ;

		} catch (SQLException e)
		{
			e.printStackTrace();
			return 0 ;
		}
		finally
		{
			try
			{
				if(statement != null && !statement.isClosed())
				{
					statement.close();
					statement = null;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 从答案表中搜索
	 * @param tableName - 表名
	 * @param keyWords - 关键词数组，为空则返回所有数据
	 * @param type -0则返回匹配到的没有解答的结果集，1则返回匹配到的带有解答的结果集，其他值则不管有没有解答都返回
	 * @param limitIndex -从第几条数据开始返回，第一条为0
	 * @param limitCount - 限制返回数据的条数
	 * @return
	 */
	public List<QuestionAnswer> getQAFormTable(String tableName,String[] keyWords, int type,int limitIndex,int limitCount )
	{
		List<QuestionAnswer> questionAnswers = new ArrayList<>();
		ResultSet resultSet = searchWithMath(tableName, type,
				new String[]{"id","question","q_segment","questioner","answer","date"},
				"q_segment", keyWords, limitIndex, limitCount);
		try
		{
			while(resultSet.next())
			{
				QuestionAnswer answer = new QuestionAnswer();
				answer.setId(resultSet.getInt("id"));
				answer.setQuestion(resultSet.getString("question"));
				answer.setQuestionSegment(resultSet.getString("q_segment"));
				answer.setQuestioner(resultSet.getString("questioner"));
				answer.setAnswer(resultSet.getString("answer"));
				answer.setDate(resultSet.getString("date"));
				questionAnswers.add(answer);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally{
			try
			{
				if(statement != null && !statement.isClosed())
				{
					statement.close();
					statement = null;
				}
				if(resultSet != null && !resultSet.isClosed())
				{
					resultSet.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return questionAnswers;
	}
	
	public int modifyQA(String tableName,int idValue,QuestionAnswer questionAnswer)
	{
		
		if(connection == null)
		{			
			System.out.println("DBHelper-------Connection为空");
			return 0;
		}
		if(idValue < 1 || questionAnswer.getQuestion() == null || questionAnswer.getQuestionSegment() == null)
		{			
			return 0;
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tableName).append(" set question='").append(questionAnswer.getQuestion()).append("'");	
		sql.append(",q_segment='").append(questionAnswer.getQuestionSegment()).append("'");
		sql.append(",date='").append(questionAnswer.getDate()).append("'");
		if(questionAnswer.getQuestioner() != null)
			sql.append(",questioner='").append(questionAnswer.getQuestioner()).append("'");
		if(questionAnswer.getAnswer() != null)
			sql.append(",answer='").append(questionAnswer.getAnswer()).append("'");

		sql.append(" where id= ").append(idValue);

		try
		{		
				statement = (Statement) connection.createStatement();
				return statement.executeUpdate(sql.toString());

		} catch (SQLException e)
		{
			e.printStackTrace();
			return 0 ;
		}
		finally
		{
			try
			{
				if(statement != null && !statement.isClosed())
				{
					statement.close();
					statement = null;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 查找教师信息，失败返回null
	 * @param tableName -表名
	 * @param columnNames -匹配的列名
	 * @param values -对应的列值
	 * @return
	 */
	public TeacherInfo checkTeacherInfo(String tableName,String[] columnNames,String[] values)
	{
		if(columnNames == null || values == null )
			return null;
		TeacherInfo teacherInfo = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(tableName).append(" where 1=1");
		for(int i= 0;i<columnNames.length;i++)
		{
			sql.append(" and ").append(columnNames[i]).append("='").append(values[i]).append("'");
		}
		
		try
		{		
				statement = (Statement) connection.createStatement();
				ResultSet resultSet =statement.executeQuery(sql.toString());
				while(resultSet.next())
				{
					teacherInfo = new TeacherInfo();
					teacherInfo.setTeacherQQ(resultSet.getString("teacher_qq"));
					teacherInfo.setPassword(resultSet.getString("password"));
					teacherInfo.setTeacherName(resultSet.getString("teacher_name"));
					teacherInfo.setTeacherDescription(resultSet.getString("teacher_description"));
					teacherInfo.setPrimaryQuestionAnswerTable(resultSet.getString("primary_qa_table"));
					teacherInfo.setSubQuestionAnswerTables(resultSet.getString("sub_qa_tables"));
					teacherInfo.setTeacherAgentQQ(resultSet.getString("teacher_agent_qq"));
					return teacherInfo;
				}

		} catch (SQLException e)
		{
			e.printStackTrace();
			return null ;
		}
		finally
		{
			try
			{
				if(statement != null && !statement.isClosed())
				{
					statement.close();
					statement = null;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return null;
				
	}
		
	public static void main(String[] args)
	{
		Connection connection = Conn.getConnection();
		DBHelper df  =new DBHelper(connection);
		TeacherInfo rInfo = df.checkTeacherInfo("teacherinfo", new String[]{"teacher_qq","password"}, new String[]{"2643537191","12345678"});
		System.out.println(rInfo);
		
	}
	

}
