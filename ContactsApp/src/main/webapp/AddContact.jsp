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
		<h1>Add Contact!..</h1>
		<form action="AddContactInit.jsp" method="post">
			<input type="text" name="name" placeholder="Name" required>
			<input type="email" name="email" placeholder="Email Id">
			<input type="number" name="phoneNum" placeholder="Phone Number" required>
			<input type="text" name="tags" placeholder="Tags">
			<input type="date" name="dob" placeholder="DateOfBirth(dd/MM/yyyy)">
			<select name="gender">
				<option value="male">Male</option>
				<option value="female">Female</option>
				<option value="other">Other</option>
			</select>
			<input type="submit" value="Add">
		</form>
		${message }
	</div>
</body>
</html>