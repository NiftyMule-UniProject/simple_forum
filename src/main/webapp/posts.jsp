<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Posts</title>
</head>
<body>
<%@include file="header.jsp"%>
<h1>Post list</h1>
<c:forEach items="${posts}" var="post">
    Creation time: ${post.creationTime} <br/>
    Author: ${post.username} <br/>
    Content: ${post.content} <br/>
    Upvote: ${post.upvote} <br/>
    <a href="/post/detail/${post.id}">details</a> <br/>
    <hr>
</c:forEach>
</body>
</html>
