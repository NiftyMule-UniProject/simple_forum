<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Simple Forum</title>
</head>
<body>
    <%@include file="header.jsp"%>
    <h1>Hello!</h1>
    <sec:authorize access="isAuthenticated()">
        <h2><a href="${pageContext.request.contextPath}/posts">See posts</a></h2>
    </sec:authorize>
</body>
</html>
