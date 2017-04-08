package textSimilarity;

import java.util.List;

import segmentation.Word;

public interface Similarity
{
	/**
	 * 文本最低相似度1
	 */
	float LOWEST_SIMILARITY1 = (float) 0.5;
	
	/**
	 * 文本最低相似度2
	 */
	float LOWEST_SIMILARITY2 = (float) 0.3;
	
	public float getSimilarity(List<Word> words1,List<Word> words2);
	

}
