<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id=box>
	<h1>Listing Contacts</h1>
		<form action="listcontactsinfo.do" method="post">
		<h1>Based On Order</h1>
			<select name="order">
				<option value="name">Name</option>
				<option value="email">Email Id</option>
				<option value="dob">Date Of Birth</option>
			</select>
		<input type="submit" value="search">	
		</form>
	</div>
</body>
</html>