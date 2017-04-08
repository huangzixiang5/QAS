package data;

public class Question
{
	private String id = null;
	private String questionContent = null;
	private String questionSegment = null;
	private String questioner = null;
	private String date = null;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getQuestionContent()
	{
		return questionContent;
	}
	public void setQuestionContent(String questionContent)
	{
		this.questionContent = questionContent;
	}
	public String getQuestionSegment()
	{
		return questionSegment;
	}
	public void setQuestionSegment(String questionSegment)
	{
		this.questionSegment = questionSegment;
	}
	public String getQuestioner()
	{
		return questioner;
	}
	public void setQuestioner(String questioner)
	{
		this.questioner = questioner;
	}
	
	
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	@Override
	public String toString()
	{
		return "Question [id=" + id + ", questionContent=" + questionContent + ", questionSegment=" + questionSegment
				+ ", questioner=" + questioner + ", date=" + date + "]";
	}
	
	

}
