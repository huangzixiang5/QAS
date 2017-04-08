package data;

public interface Response
{
	static final String[] DEFAULT_ANSWERS = {
			"等下再告诉你!",
			"我去查资料以后再告诉你！"
			};
	static final String DEFAULT_HELP_STUDENT = "提问格式： "+Request.RequestType.ANSWER_REQUEST.type+
			"question  如：\n"+Request.RequestType.ANSWER_REQUEST.type+"什么是计算机？"
			+ "\n";
	static final String DEFAULT_HELP_TEACHER = "关闭代理： "+
			Request.RequestType.CLOSE_REQUEST.type;

}
