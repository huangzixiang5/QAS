package data;

import java.util.Calendar;
import java.util.List;

import segmentation.Segment;
import segmentation.Word;

public class QuestionAnswer
{
	private int id = 0;
	private String question  =null;
	private String questionSegment =null;
	private String questioner = null;
	private String answer = null;
	private String date = null;
	
	public QuestionAnswer()
	{
		
	}
	public QuestionAnswer(String question)
	{
		this(question,null);
	}
	public QuestionAnswer(String question,String answer)
	{
		this.question  = question;
		this.answer = answer;
		Segment segment = new Segment(false);
		List<Word> words = segment.seg(question);
		questionSegment  = Segment.getStringFromList(words);
		Calendar calendar = Calendar.getInstance();
		date = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
	} 
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getQuestion()
	{
		return question;
	}
	public void setQuestion(String question)
	{
		this.question = question;
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
	public String getAnswer()
	{
		return answer;
	}
	public void setAnswer(String answerContent)
	{
		this.answer = answerContent;
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
		return "QuestionAnswer [id=" + id + ", question=" + question + ", questionSegment=" + questionSegment
				+ ", questioner=" + questioner + ", answer=" + answer + ", date=" + date + "]";
	}
	
	
	

}
