package segmentation;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 用于分词的类
 * 
 * @author zixiang huang
 *
 */
public class Segment
{
	private List<Word> words = null;
	private boolean useSmart = true;	//是否使用智能分词
	private StringReader re = null;
	private IKSegmenter ik = null;
	private Lexeme lex = null;

	
	/**
	 * 
	 * @param useSmart  - true表示使用智能切分，false表示使用最小细度切分
	 * 		
	 */
	public Segment(boolean useSmart)
	{
		this.useSmart = useSmart;
		
	}

	/**
	 * 此方法对字符串进行不计频率的分词
	 * @param text - 将要分词的文本
	 * @return -分词结果，不计频率
	 */
	public List<Word> seg(String text)
	{
		words = null;
		words = new ArrayList<>();
		re = new StringReader(text);
		ik = new IKSegmenter(re, useSmart);
		String string = null;

		try
		{
			while ((lex = ik.next()) != null)
			{
				string = lex.getLexemeText();
				words.add(new Word(string));
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return words;
	}
	
	
	/**
	 * 将分词结果以字符串形式返回，中间以","分割
	 * @param words - 分词结果
	 * @return - 以","划分的分词结果
	 */
	
	public static String getStringFromList(List<Word> words)
	{
		StringBuffer string = new StringBuffer();
		
		for (Word word : words)
		{
			if (word != null && (word.getString() != null && !word.getString().equals("")))
			{
				string.append(word.getString()+",");				
			}
		}
		if(string.length() > 0)
		{
			string.deleteCharAt(string.length()-1);	
		}	
		return string.toString();
	}

	/**
	 * 将字符串以 {@link ","} 为分界进行分词
	 * @param string - 需要分词的字符串
	 * @return
	 */
	public static List<Word> getListFromString(String string)
	{
		List<Word> words = new ArrayList<>();
		String[] strings = string.split(",");
		for (String string2 : strings)
		{
			if(string2 != null && !string2.equals(""))
			{			
					words.add(new Word(string2));			
			}
		}
		
		return words;
	}
	
	public static void main(String[] args)
	{
		Segment segment = new Segment(false);
		List<Word> words = segment.seg("武汉理工大学");
		System.out.println(Segment.getStringFromList(words));
	}


}
