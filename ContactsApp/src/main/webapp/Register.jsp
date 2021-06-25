<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="box">
		<h1>Register!..</h1>
		<form action="RegInit.jsp" method="post">
		<input type="text" name="uname" placeholder="User Name" required>
		<input type="email" name="email" placeholder="Email-Id" required>
		<input type="password" name="pass" placeholder="Password" required>
		<input type="password" name="rpass" placeholder="Re-Password" required>
		<input type="submit" value="Sign Up">
		</form>
	</div>
	${ message }
</body>
</html>