package com.uttara.util;

import java.util.List;

import com.uttara.bean.ContactBean;
import com.uttara.bean.RegBean;

public interface UserDao {
	
	String register(RegBean bean);
	
	String checkIfEmailExists(RegBean bean);
	
	String authenticate(RegBean bean);
	
	String insertContact(ContactBean bean,RegBean rb);
	
	List<ContactBean> search(String s);
	
	List<ContactBean> listContacts(String order,RegBean rb);
	
	List<ContactBean> list(String name);
}
