<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form name="f" action="${pageContext.request.contextPath}/perform_login" method="post">
    <br/>
    username:
    <input type="text" name="username" placeholder="name"><br/>
    password:
    <input type="password" name="password" placeholder="password"><br/>
    <input name="submit" type="submit" value="login">
</form>
<a href="${pageContext.request.contextPath}/register">Register new user</a>
<c:if test="${param.error}">
    <h2 style="background-color: red;color: aliceblue"> Login error! </h2>
</c:if>
</body>
</html>
