package com.uttara.util;

public class DaoFactory {
	
	public static UserDao getInstance()
	{
		int b = Constants.DAO;
		if(b==1)
		{
			return new HSQLDbDao();
		}
		return null;
		
	}
}
