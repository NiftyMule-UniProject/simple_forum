<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Login</h1>
<form action="${pageContext.request.contextPath}/register" method="post">
    <br/>
    username (will be used as displayed name):
    <input type="text" name="username" placeholder="name"><br/>
    password:
    <input type="password" name="password" placeholder="password"><br/>
    confirm password:
    <input type="password" name="password_confirm" placeholder="password"><br/>

    <input name="submit" type="submit" value="Register">
</form>
<a href="${pageContext.request.contextPath}/login">Login to existing user</a>
<c:if test="${err_message != null}">
    <h2 style="background-color: red;color: aliceblue"> ${err_message} </h2>
</c:if>
</body>
</html>
