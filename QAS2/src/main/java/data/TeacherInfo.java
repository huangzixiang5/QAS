package data;

public class TeacherInfo
{
	private String teacherQQ = null;
	private String password = null;
	private String teacherName = null;
	private String teacherDescription = null;
	private String teacherAgentQQ = null;
	private String primaryQuestionAnswerTable = null;
	private String subQuestionAnswerTables = null;
		
	public TeacherInfo()
	{
	}

	public String getTeacherQQ()
	{
		return teacherQQ;
	}

	public void setTeacherQQ(String teacherQQ)
	{
		this.teacherQQ = teacherQQ;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getTeacherName()
	{
		return teacherName;
	}

	public void setTeacherName(String teacherName)
	{
		this.teacherName = teacherName;
	}

	public String getTeacherDescription()
	{
		return teacherDescription;
	}

	public void setTeacherDescription(String teacherDescription)
	{
		this.teacherDescription = teacherDescription;
	}

	public String getTeacherAgentQQ()
	{
		return teacherAgentQQ;
	}

	public void setTeacherAgentQQ(String teacherAgentQQ)
	{
		this.teacherAgentQQ = teacherAgentQQ;
	}

	public String getPrimaryQuestionAnswerTable()
	{
		return primaryQuestionAnswerTable;
	}

	public void setPrimaryQuestionAnswerTable(String primaryQuestionAnswerTable)
	{
		this.primaryQuestionAnswerTable = primaryQuestionAnswerTable;
	}

	public String getSubQuestionAnswerTables()
	{
		return subQuestionAnswerTables;
	}

	public void setSubQuestionAnswerTables(String subQuestionAnswerTables)
	{
		this.subQuestionAnswerTables = subQuestionAnswerTables;
	}

	@Override
	public String toString()
	{
		return "TeacherInfo [teacherQQ=" + teacherQQ + ", password=" + password + ", teacherName=" + teacherName
				+ ", teacherDescription=" + teacherDescription + ", teacherAgentQQ=" + teacherAgentQQ
				+ ", primaryQuestionAnswerTable=" + primaryQuestionAnswerTable + ", subQuestionAnswerTables="
				+ subQuestionAnswerTables + "]";
	}
	
	
}
