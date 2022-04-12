<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post detail</title>
</head>
<body>
<%@include file="header.jsp"%>
<h2><a href="${pageContext.request.contextPath}/posts">Posts</a></h2>
<h1>Post detail</h1>
Title: ${post.title} <br/>
Creation time: ${post.creationTime} <br/>
Author: ${post.username} <br/>
Content: ${post.content} <br/>
Upvote: ${post.upvote} <br/>
<hr>
<hr>
<c:forEach items="${comments}" var="comment">
    Comment ID: ${comment.id} <br/>
    Creation time: ${comment.creationTime} <br/>
    Refer to: ${comment.referToCommentId} <br/>
    Author: ${comment.username} <br/>
    Content: ${comment.content} <br/>
    Upvote: ${comment.upvote} <br/>
    <hr>
</c:forEach>

<form id="comment_creation">
    <label for="referTo">Refer To:</label>
    <input type="text" id="referTo" name="referTo"> <br/>

    <label for="content">Content</label>
    <textarea id="content" name="content"></textarea><br/>

    <input type="submit">
</form>

</body>
<script>
    $('#comment_creation').submit(function () {
        const data = $("#comment_creation").serialize()
        $.ajax("/api/post/${post.id}/comment", {
            data: data,
            method: "POST",
            success: function (data, status, xhr) {
                // TODO - update comment list
                alert(data)
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when adding comment: " + jqXhr.responseText)
            }
        })
        return false
    })
</script>
</html>
