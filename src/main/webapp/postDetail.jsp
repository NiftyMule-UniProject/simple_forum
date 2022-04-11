<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post detail</title>
</head>
<body>
<%@include file="header.jsp"%>
<h1>Post detail</h1>
Creation time: ${post.creationTime} <br/>
Author: ${post.username} <br/>
Content: ${post.content} <br/>
Upvote: ${post.upvote} <br/>
<hr>
<c:forEach items="${comments}" var="comment">
    Creation time: ${comment.creationTime} <br/>
    Refer to: ${comment.referToCommentId} <br/>
    Author: ${comment.username} <br/>
    Content: ${comment.content} <br/>
    Upvote: ${comment.upvote} <br/>
    <hr>
</c:forEach>
</body>
</html>
