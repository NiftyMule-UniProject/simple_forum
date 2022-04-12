<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Posts</title>
</head>
<body>
<%@include file="header.jsp"%>
<h1>Post list</h1>
<c:forEach items="${posts}" var="post">
    Title: ${post.title} <br/>
    Creation time: ${post.creationTime} <br/>
    Author: ${post.username} <br/>
    Content: ${post.content} <br/>
    Upvote: ${post.upvote} <br/>
    <a href="/post/detail/${post.id}">details</a> <br/>
    <sec:authorize access="principal.username.equals('${post.username}') || hasAuthority('admin')">
        <button onclick="deletePost(${post.id})">delete</button>
    </sec:authorize>
    <hr>
</c:forEach>
<a href="${pageContext.request.contextPath}/create_post">Create new post</a>
</body>
<script>
    function deletePost(postId) {
        $.ajax("/api/post/" + postId, {
            method: "DELETE",
            success: function (data, status, xhr) {
                // TODO - update comment list
                alert(data)
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }
</script>
</html>
