<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Post Creation</title>
</head>
<body>

<%@include file="header.jsp"%>
<h2><a href="${pageContext.request.contextPath}/posts">Posts</a></h2>
<form action="${pageContext.request.contextPath}/post" method="post">
    <label for="title">Title: </label>
    <input type="text" id="title" name="title"
        <c:if test="${title != null}"> value="${title}" </c:if>/> <br/>

    <label for="type">Type: </label>
    <select id="type" name="type">
        <c:forEach items="${types}" var="type">
            <option value="${type.id}"
                <c:if test="${type.id == postType}"> selected </c:if>>
                ${type.typeName}
            </option>
        </c:forEach>
    </select> <br/>

    <label for="content">Content: </label>
    <textarea id="content" name="content" rows="4" placeholder="Enter you post content here">${postContent}</textarea> <br/>

    <input type="submit">
</form>

<c:if test="${errMsg != null}">
    <h2 style="background-color: red;color: aliceblue"> ${errMsg} </h2>
</c:if>

</body>
</html>