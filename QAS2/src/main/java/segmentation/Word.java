package segmentation;

/**
 * 记录单个分词信息的类
 * 
 * @author zixiang huang;
 * 
 *
 */
public class Word
{
	private String string = null; // 
	private int length = 0;
	public Word()
	{
	}

	public Word(String text)
	{
		if (text != null)
		{
			string = text;
			length = text.length();
		}

	}

	/**
	 * 
	 * @return return the word
	 */
	public String getString()
	{
		return string;
	}

	/**
	 * 
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * 
	 * @param string
	 *            -the new string you want to set
	 */
	public void setString(String string)
	{
		this.string = string;
		length = string.length();
	}

	@Override
	public int hashCode()
	{

		return string.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		
		if(! (obj instanceof Word))
			return false;
		else
		{
			Word word = (Word) obj;
			return string.equals(word.getString());
		}
	}
	
	@Override
	public String toString()
	{
		return string+"-"+length;
	}
	
}
