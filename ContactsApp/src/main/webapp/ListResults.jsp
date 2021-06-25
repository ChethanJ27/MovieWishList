<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List,com.uttara.bean.ContactBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Contacts!..</h1>
	<table>
		<thead>
		<tr>
			<th>Name</th>
			<th>Email-Id</th>
			<th>Phone Number</th>
			<th>Date Of Birth</th>
			<th>Tags</th>
			<th>Gender</th>
		</tr>
		</thead>
		<tbody>	
		<% List<ContactBean>bean=(List<ContactBean>)request.getAttribute("contacts");
			for(ContactBean cb:bean)
			{
				out.write("<tr>"+
						"<td>"+cb.getName()+"</td>"+
						"<td>"+cb.getEmail()+"</td>"+
						"<td>"+cb.getPhoneNum()+"</td>"+
						"<td>"+cb.getDob()+"</td>"+
						"<td>"+cb.getTags()+"</td>"+
						"<td>"+cb.getGender()+"</td>"+
					"</tr>");
			}
		%>
		</tbody>
	</table>
</body>
</html>