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
		<form action="LoginInit.jsp" method="post">
			<input type="email" name="email" placeholder="Enail-Id" required>
			<input type="password" name="pass" placeholder="Password" required>
			<input type="submit" value="Sign In">
		</form>
		${ message }
	</div>
</body>
</html>