<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post detail</title>
</head>
<body style="padding-bottom: 100vh">
<%@include file="header.jsp"%>
<h2><a href="${pageContext.request.contextPath}/posts">Posts</a></h2>

<%---- Post details ----%>
<h1>Post detail</h1>
Title: ${post.title} <br/>
Creation time: ${post.creationTime} <br/>
Author: ${post.username} <br/>
Content: ${post.content} <br/>
Upvote: ${post.upvote} <br/>
<c:choose>
    <c:when test="${postUpvote}"> <button onclick="cancelUpvotePost(${post.id})">Cancel upvote</button> </c:when>
    <c:otherwise> <button onclick="upvotePost(${post.id})">Upvote</button> </c:otherwise>
</c:choose>
<sec:authorize access="principal.username.equals('${post.username}') || hasAuthority('admin')">
    <button onclick="deletePost(${post.id})">delete</button>
</sec:authorize>
<hr>
<hr>

<%---- Comments ----%>
<c:forEach items="${comments}" var="comment">
<div id="${comment.id}">
    Comment ID: ${comment.id} <br/>
    Creation time: ${comment.creationTime} <br/>
    <c:if test="${comment.referToCommentId != null}">
        Refer to: <a href="#${comment.referToCommentId}"> ${comment.referToCommentId} </a><br/>
    </c:if>
    Author: ${comment.username} <br/>
    Content: ${comment.content} <br/>
    Upvote: ${comment.upvote} <br/>
    <button onclick="$('#referTo').val(${comment.id})">Refer</button>
    <c:choose>
        <c:when test="${upvoteMap.get(comment.id)}"> <button onclick="cancelUpvoteComment(${post.id}, ${comment.id})">Cancel upvote</button> </c:when>
        <c:otherwise> <button onclick="upvoteComment(${post.id}, ${comment.id})">Upvote</button> </c:otherwise>
    </c:choose>
    <sec:authorize access="principal.username.equals('${comment.username}') || hasAuthority('admin')">
        <button onclick="deleteComment(${post.id}, ${comment.id})">delete</button>
    </sec:authorize>
    <hr>
</div>
</c:forEach>

<%---- New comment form ----%>
<form id="comment_creation">
    <label for="referTo">Refer To:</label> <button onclick="$('#referTo').val(''); return false;">Clear</button>
    <input type="text" id="referTo" name="referTo" readonly> <br/>

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
                // alert(data)
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when adding comment: " + jqXhr.responseText)
            }
        })
        return false
    })

    function deletePost(postId) {
        $.ajax("/api/post/" + postId, {
            method: "DELETE",
            success: function (data, status, xhr) {
                // TODO - update post list
                // alert(data)
                window.location.replace('/posts')
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }

    function upvotePost(postId) {
        $.ajax("/api/post/" + postId + "/upvote", {
            method: "POST",
            success: function (data, status, xhr) {
                // TODO - update vote button
                // alert(data)
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }

    function cancelUpvotePost(postId) {
        $.ajax("/api/post/" + postId + "/upvote", {
            method: "DELETE",
            success: function (data, status, xhr) {
                // TODO - update vote button
                // alert(data)
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }

    function deleteComment(postId, commentId) {
        $.ajax("/api/post/" + postId + "/comment/" + commentId, {
            method: "DELETE",
            success: function (data, status, xhr) {
                // TODO - delete following line, use jQuery to update comment list
                // alert(data)
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting comment: " + jqXhr.responseText)
            }
        })
    }

    function upvoteComment(postId, commentId) {
        $.ajax("/api/post/" + postId + "/comment/" + commentId + "/upvote", {
            method: "POST",
            success: function (data, status, xhr) {
                // TODO - update vote button
                // alert(data)
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }

    function cancelUpvoteComment(postId, commentId) {
        $.ajax("/api/post/" + postId + "/comment/" + commentId + "/upvote", {
            method: "DELETE",
            success: function (data, status, xhr) {
                // TODO - update vote button
                // alert(data)
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }
</script>
</html>
