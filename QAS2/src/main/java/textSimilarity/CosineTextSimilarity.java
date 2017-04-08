package textSimilarity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import segmentation.Word;

/**
 * 文本相似度计算
 * 判定方式：余弦相似度，通过计算两个向量的夹角余弦值来评估他们的相似度
 * 余弦夹角原理：
 * 向量a=(x1,y1),向量b=(x2,y2)
 * similarity=a.b/|a|*|b|
 * a.b=x1x2+y1y2
 * |a|=根号[(x1)^2+(y1)^2],|b|=根号[(x2)^2+(y2)^2]
 */
public class CosineTextSimilarity implements Similarity
{
		
	public CosineTextSimilarity()
	{
	}

	@Override
	public float getSimilarity(List<Word> words1, List<Word> words2)
	{
		//构造权重容器
		LinkedHashMap<String,Float> map1 = getWeightMap(words1);
		LinkedHashMap<String,Float> map2 = getWeightMap(words2);
		
		//计算向量|a|		
		float absMap1 = 0;
		for (Float f : map1.values())
		{
			absMap1 = absMap1+f*f;
		}
		absMap1 = (float) Math.sqrt(absMap1);
		
		//计算向量|b|	
		float absMap2 = 0;
		for(Float f:map2.values())
		{
			absMap2 = absMap2+f*f;
		}
		absMap2 = (float) Math.sqrt(absMap2);
		
		//计算向量 a.b
		Set<String> set = new HashSet<>();
		set.addAll(map1.keySet());
		set.addAll(map2.keySet());

		float f1 = 0;
		float f2 = 0;
		float f = 0;
		for (String string : set)
		{
			f1 = map1.get(string) ==null? 0:map1.get(string);
			f2 = map2.get(string) ==null? 0:map2.get(string);
			f = f+f1*f2;
		}
		//返回两向量余弦值
		return f/(absMap1*absMap2);
	}

	/**
	 * 计算单个词权重
	 * @param word - 需要计算权重的单词
	 * @return
	 */
	private float getWeight(Word word)
	{
		//按照不同比例综合考虑单词频率和单词长度来计算权重
		if(word.getLength() >= 4)
			return 4;
		return word.getLength();
	}
	
	/**
	 * 为每个分词结果分配权重
	 * @param words - 分词列表
	 * @return
	 */
	private LinkedHashMap<String,Float> getWeightMap(List<Word> words)
	{
		// 为每个词标记权重
		LinkedHashMap<String,Float> map = new LinkedHashMap<String, Float>();
		for (Word word : words)
		{
			if (word != null && word.getString() != null && !word.getString().equals(""))
			{
				map.put(word.getString(), getWeight(word));
			}
		}
		return map;
	}
	

}
