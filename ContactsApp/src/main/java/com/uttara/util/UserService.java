package com.uttara.util;

import java.util.List;

import com.uttara.bean.ContactBean;
import com.uttara.bean.RegBean;

public class UserService {
	private static UserDao dao;
	public static String register(RegBean bean)
	{
		dao = DaoFactory.getInstance();
		String result="";
		result=dao.checkIfEmailExists(bean); //returns success if email is not registered
		if(result.equals(Constants.SUCCESS))
		{
			result = dao.register(bean);
		}
		return result;
	}
	
	public static String login(RegBean bean)
	{
		dao = DaoFactory.getInstance();
		String result = dao.authenticate(bean);
		return result;
	}
	public static String addContact(ContactBean bean,RegBean rb)
	{
		dao = DaoFactory.getInstance();
		String result = dao.insertContact(bean,rb);
		return result;		
	}

	public static List<ContactBean> viewContacts(String order, RegBean bean) {
		dao=DaoFactory.getInstance();
		List<ContactBean> listContacts = dao.listContacts(order,bean);
		return listContacts;
		
	}
}
