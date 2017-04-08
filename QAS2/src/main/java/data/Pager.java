package data;

/**
 * 记录分页信息的类
 * @author zixiang huang
 *
 */
public class Pager
{
	private int totalRecords = 0; //总记录条数
	private int totalPages = 1;	//总页数
	private int currentPage = 1; //当前页
	private int pageSize = 4; //每页显示多少条记录
	private int limitArg0 = 0; //SQL limit函数的下标参数
	private int limitArg1 = 0; //SQL limit函数中返回记录的条数
	
	/**
	 * 根据总记录的条数及要显示的页码初始化一个对象
	 * @param totalRecords -总记录数
	 * @param currentPage -要显示的页码
	 */
	public Pager(int totalRecords ,int currentPage)
	{
		if(totalRecords > 0)
		{
			this.totalRecords = totalRecords;
			int a = totalRecords/pageSize;
			int b = totalRecords%pageSize;
			this.totalPages =  a + (b > 0 ? 1:0);
			
			setCurrentPage(currentPage);
			setLimitArgs();
		}
	}

	//设置SQL 中limit函数的参数
	private void setLimitArgs()
	{
		limitArg0 = (currentPage-1)*pageSize;
		limitArg1 = pageSize;		
	}

	//确保页码的合理性
	private void setCurrentPage(int currentPage)
	{
		if(currentPage < 1)
			this.currentPage = 1;
		else if(currentPage > totalPages)
			this.currentPage = totalPages;
		else
			this.currentPage = currentPage;
	}
	
	
	/**
	 * 返回当前页数
	 * @return
	 */
	public int getCurrentPage()
	{
		return currentPage;
	}

	/**
	 * 返回SQL 中limit函数的第一个参数，设置从第几条记录开始返回
	 * @return
	 */
	public int getLimitArg0()
	{
		return limitArg0;
	}

	/**
	 * 返回SQL 中limit函数的第二个参数，限制返回记录的条数
	 * @return
	 */
	public int getLimitArg1()
	{
		return limitArg1;
	}

	/**
	 * 返回总记录数
	 * @return
	 */
	public int getTotalRecords()
	{
		return totalRecords;
	}

	/**
	 * 返回总页数
	 * @return
	 */
	public int getTotalPages()
	{
		return totalPages;
	}
	
	
	
	@Override
	public String toString()
	{
		return "Page [totalRecords=" + totalRecords + ", totalPages=" + totalPages + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + ", limitArg0=" + limitArg0 + ", limitArg1=" + limitArg1 + "]";
	}

	public static void main(String[] args)
	{
		System.out.print(new Pager(12, 2));
		
	}
	
	
	
	
	

}
