package com.uttara.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uttara.bean.ContactBean;
import com.uttara.bean.RegBean;

public class HSQLDbDao implements UserDao{
	@Override
	public String register(RegBean bean) {
		Connection con=null;
		PreparedStatement ps_ins=null;
		try {
			con=JDBCHelper.getConnection();
			ps_ins=con.prepareStatement("Insert into ContactRegister(uname,email,password) values(?,?,?)");
			ps_ins.setString(1, bean.getUname());
			ps_ins.setString(2, bean.getEmail());
			ps_ins.setString(3, bean.getPass());
			ps_ins.execute();
			return Constants.SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Oops Something bad happened "+e.getMessage();
		}
		finally {
			JDBCHelper.close(ps_ins);
			JDBCHelper.close(con);
		}
	}
	
	@Override
	public String checkIfEmailExists(RegBean bean) {
		Connection con=null;
		PreparedStatement ps_sel=null;
		ResultSet rs=null;
		try {
			con=JDBCHelper.getConnection();
			ps_sel=con.prepareStatement("Select * from ContactRegister where email=?");
			ps_sel.setString(1, bean.getEmail());
			ps_sel.execute();
			rs=ps_sel.getResultSet();
			if(rs.next())
			{
				return "Email has been Already registered Use a Different Email Or Try Logging into Old Account";
			}
			else {
				return Constants.SUCCESS;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Oops Something bad happened "+e.getMessage();
		}
		finally {
			JDBCHelper.close(rs);
			JDBCHelper.close(ps_sel);
			JDBCHelper.close(con);
		}
	}
	
	@Override
	public String authenticate(RegBean bean) {
		Connection con=null;
		PreparedStatement ps_sel=null;
		ResultSet rs=null;
		try {
			con=JDBCHelper.getConnection();
			ps_sel=con.prepareStatement("Select uname from ContactRegister where email=? and password=?");
			ps_sel.setString(1, bean.getEmail());
			ps_sel.setString(2, bean.getPass());
			ps_sel.execute();
			rs=ps_sel.getResultSet();
			if(rs.next())
			{
				bean.setUname(rs.getNString("uname"));
				return Constants.SUCCESS;
			}
			else {
				return "Please Enter Correct Email and Password";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Oops Something bad happened "+e.getMessage();
		}
		finally {
			JDBCHelper.close(rs);
			JDBCHelper.close(ps_sel);
			JDBCHelper.close(con);
		}
	}
	
	@Override
	public String insertContact(ContactBean bean,RegBean rb) {
		Connection con=null;
		PreparedStatement ps_sel=null;
		PreparedStatement ps_ins_cont=null;
		PreparedStatement ps_ins_phone=null;
		PreparedStatement ps_ins_tag=null;
		ResultSet rs=null;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		try {
			con=JDBCHelper.getConnection();
			ps_sel=con.prepareStatement("select sl_no from contactRegister where email=? and password=?");
			ps_ins_cont=con.prepareStatement("INSERT INTO CONTACTAPP(\"CONTACTREGISTER_SL\", \"NAME\", \"EMAIL\", \"DOB\", \"GENDER\" )"
					+ "VALUES ( ?, ?, ?,? ,?)",Statement.RETURN_GENERATED_KEYS);
			ps_ins_phone=con.prepareStatement("INSERT INTO CONTACT_PHONE(\"CONTACTAPP_SL\", \"PHONE\" )VALUES ( ?, ?)");
			ps_ins_tag=con.prepareStatement("INSERT INTO \"PUBLIC\".\"TAGS\"(\"CONTACTAPP_SL\", \"TAGS\" )VALUES ( ?, ?)");
			con.setAutoCommit(false);
			ps_sel.setString(1,rb.getEmail());
			ps_sel.setString(2, rb.getPass());
			ps_sel.execute();
			rs=ps_sel.getResultSet();
			rs.next();
			int sl_num=rs.getInt("sl_no");
			ps_ins_cont.setInt(1, sl_num);
			ps_ins_cont.setString(2, bean.getName());
			ps_ins_cont.setString(3, bean.getEmail());
			Date parse = sdf.parse(bean.getDob());
			java.sql.Date date = new java.sql.Date(parse.getTime());
			ps_ins_cont.setDate(4, date); 
			ps_ins_cont.setString(5, bean.getGender());
			ps_ins_cont.execute();
			rs= ps_ins_cont.getGeneratedKeys();
			rs.next();
			int sl_no = rs.getInt("sl_no");
			ps_ins_phone.setInt(1, sl_no);
			ps_ins_phone.setString(2, bean.getPhoneNum());
			ps_ins_phone.execute();
			String tags = bean.getTags();
			if(tags!=null)
			{
				String[] split = tags.split(",");
				for(String s:split) {
					ps_ins_tag.setInt(1, sl_no);
					ps_ins_tag.setString(2, s);
					ps_ins_tag.execute();
				}
			}
			con.commit();
			return Constants.SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "Oops Something bad happened "+e.getMessage();
		}
		finally {
			JDBCHelper.close(rs);
			JDBCHelper.close(ps_sel);
			JDBCHelper.close(ps_ins_tag);
			JDBCHelper.close(ps_ins_phone);
			JDBCHelper.close(ps_ins_cont);
			JDBCHelper.close(con);
		}
	}
	
	@Override
	public List<ContactBean> list(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ContactBean> listContacts(String order,RegBean rb) {
		Connection con=null;
		List<ContactBean> list=new ArrayList<ContactBean>();
		ContactBean bean;
		PreparedStatement ps_sel_reg=null;
		PreparedStatement ps_sel=null;
		ResultSet rs=null;
		ResultSet ns=null;
		try {
			con=JDBCHelper.getConnection();
			//Select c.name,c.email,c.dob,c.gender,p.phone,d.tags from contactregister a,contactapp c,contact_phone p,tags d where c.contactRegister_sl=0  and (c.sl_no=p.contactapp_sl and c.sl_no=d.contactapp_sl) order by name
			ps_sel_reg=con.prepareStatement("select sl_no from contactRegister where email=? and password=?");
			ps_sel=con.prepareStatement("Select c.name,c.email,c.dob,c.gender,p.phone,d.tags from contactregister a,contactapp c,contact_phone p,"
					+ "tags d where a.sl_no=? and c.contactRegister_sl=a.sl_no  and (c.sl_no=p.contactapp_sl and c.sl_no=d.contactapp_sl)");
			//ps_sel.setString(1, order);
			ps_sel_reg.setString(1,rb.getEmail());
			ps_sel_reg.setString(2, rb.getPass());
			ps_sel_reg.execute();
			ns=ps_sel_reg.getResultSet();
			ns.next();
			int sl_num=ns.getInt("sl_no");
			ps_sel.setInt(1, sl_num);
			ps_sel.execute();
			rs=ps_sel.getResultSet();
			while(rs.next())
			{
				bean=new ContactBean();
				bean.setName(rs.getNString("name").trim());
				bean.setEmail(rs.getNString("email").trim());
				bean.setDob(rs.getNString("dob").trim());
				bean.setPhoneNum(rs.getNString("phone").trim());
				bean.setTags(rs.getNString("tags").trim());
				bean.setGender(rs.getNString("gender").strip());
				list.add(bean);
				bean=null;
			}
			System.out.println(list);
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			JDBCHelper.close(ns);
			JDBCHelper.close(rs);
			JDBCHelper.close(ps_sel_reg);
			JDBCHelper.close(ps_sel);
			JDBCHelper.close(con);
		}
	}
	
	@Override
	public List<ContactBean> search(String s) {
		// TODO Auto-generated method stub
		return null;
	}
}
