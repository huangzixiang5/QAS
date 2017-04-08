package data;

public class Request
{
	public enum RequestType
	{
		ANSWER_REQUEST("q%"),
		HELP_REQUEST("h%"),
		CLOSE_REQUEST("shutdown%")
		;
		
		String type = null;
		RequestType(String type)
		{
			this.type = type;
		}
		
		@Override
		public String toString()
		{
			return type;
		}
	}
		
	private String requestor = null;
	private RequestType requestType = null;
	private String requestContent = null;
	
	private Request(String requestor,RequestType rType, String content)
	{
		this.requestor = requestor;
		requestType = rType;
		requestContent = content;
	}
	
	/**
	 * 返回请求人QQ
	 * @return
	 */
	public String getRequestor()
	{
		return requestor;
	}
	
	/**
	 * 返回请求类型
	 * @return
	 */
	public RequestType getRequestType()
	{
		return requestType;
	}
	
	/**
	 * 返回请求内容
	 * @return
	 */
	public String getRequestContent()
	{
		return requestContent;
	}
	
	/**
	 * 根据请求描述生成一个请求，若该请求类型未定义则返回null
	 * @param requestor - 发起请求的人
	 * @param requestDescription - 请求描述
	 * @return
	 */
	public static Request createRequest(String requestor,String requestDescription)
	{
		if(requestDescription == null)
			return null;
		else if(requestDescription.trim().equals(""))
			return null;
		else
		{
			requestDescription = requestDescription.trim().toLowerCase();
			for (RequestType rType : RequestType.values())
			{
				if(requestDescription.startsWith(rType.type))
				{
					String content = requestDescription.replaceFirst(rType.type, "");
					if(content.equals("") && rType == RequestType.ANSWER_REQUEST)
						return null;
					else
						return new Request(requestor,rType,content);
				}
			}
		}
		return null;
	}
	
	
}
