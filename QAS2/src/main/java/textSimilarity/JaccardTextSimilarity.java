package textSimilarity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import segmentation.Segment;
import segmentation.Word;

/**
 * 文本相似度计算
 * 判定方式：Jaccard相似性系数（Jaccard similarity coefficient） ，通过计算两个集合交集的大小除以并集的大小来评估他们的相似度
 * 算法步骤描述：
 * 1、分词
 * 2、求交集，计算交集的词的个数 intersectionSize
 * 3、求并集，计算并集的的个数 unionSize
 * 4、2中的值除以3中的值 intersectionSize/unionSize
 */
public class JaccardTextSimilarity implements Similarity
{

	@Override
	public float getSimilarity(List<Word> words1, List<Word> words2)
	{
		if(words1 == null || words2 == null)
			return 0;
		float similarity = 0;
		similarity = getSizeOfIntersection(words1, words2)/(float)getSizeOfUnionset(words1, words2);
		return similarity;
	}
	
	/**
	 * 获取交集大小
	 * @param words1 - 分词结果1
	 * @param words2 - 分词结果2
	 * @return - 交集大小
	 */
	
	private  int getSizeOfIntersection(List<Word> words1, List<Word> words2)
	{
		if(words1 == null || words2 == null)
			return 0;
		
		int size = 0;
		
		for (Word word : words2)
		{
			if(words1.contains(word))
			{
				size++;
			}
			
		}	
		return size;
	}

	/**
	 * 获取并集大小
	 * @param words1
	 * @param words2
	 * @return
	 */
	private int getSizeOfUnionset(List<Word> words1,List<Word> words2)
	{
		if(words1 == null)
		{
			return words2 == null ? 0:words2.size();
		}
		else if (words2 == null) {
			return words1.size();
		}
		
		Set<Word> words =new HashSet<>();
		words.addAll(words1);
		words.addAll(words2);
		return words.size();
	}

	public static void main(String[] args)
	{
		Segment segment = new Segment(false);
		 List<Word> word1 = segment.seg("武汉理工大学在哪");
		 List<Word> word2 = segment.seg("武汉理工在哪里");
		 System.out.println(word1);
		 System.out.println(word2);
		 JaccardTextSimilarity jaccardTextSimilarity = new JaccardTextSimilarity();
		 System.out.println(jaccardTextSimilarity.getSimilarity(word1, word2));
	}

}
