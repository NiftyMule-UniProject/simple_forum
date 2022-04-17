<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Portal</title>
</head>
<body>
    <sec:authentication property="principal.username" var="currentUsername"/>
    <h2><a href="${pageContext.request.contextPath}/">Homepage</a></h2>
    <hr>

    <%----  Post types  ----%>
    <h3>Post types</h3>
    <label for="newPostType">Create new post type</label>
    <input type="text" id="newPostType">
    <button id="createPostTypeBtn"> Add </button>
    <br>
    <c:forEach items="${postTypes}" var="postType">
        Post type: ${postType.typeName}
        <br>
    </c:forEach>

    <%----  Admins  ----%>
    <h3>Admin users</h3>
    <c:forEach items="${admins}" var="user">
        Username: ${user.name}
        <c:if test="${!(user.name.equals(currentUsername) || superusers.contains(user))}">
            <button onclick="deleteAdmin(${user.id})">Delete</button>
        </c:if>
        <br>
    </c:forEach>

    <label for="nameKeyword"> Add new admin. Search for username: </label>
    <input type="text" id="nameKeyword">
    <button id="search"> search </button>
    <div id="searchResult"></div>
</body>

<script>
    $("#createPostTypeBtn").click(function () {
        const postTypeName = $("#newPostType").val();
        $.ajax("/api/post/type", {
            method: "POST",
            data: jQuery.param({
                "postTypeName": postTypeName
            }),
            success: function (data, status, xhr) {
                // TODO - update vote button
                // alert(data)
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    })

    function addAdmin(username) {
        $.ajax("/api/admin/" + username, {
            method: "POST",
            success: function (data, status, xhr) {
                // TODO - update vote button
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }

    function deleteAdmin(userId) {
        $.ajax("/api/admin/" + userId, {
            method: "DELETE",
            success: function (data, status, xhr) {
                // TODO - update vote button
                location.reload()
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    }

    $("#search").click(function () {
        const nameKeyword = $("#nameKeyword").val();
        $.ajax("/api/admin/users", {
            method: "GET",
            data: jQuery.param({
                "keyword": nameKeyword
            }),
            success: function (data, status, xhr) {
                // TODO - update vote button
                const jsonArr = JSON.parse(data)
                let content = "";
                jsonArr.forEach(username => {
                    content += "Username: "+username+"<button onclick='addAdmin(\""+username+"\")'>Add</button><br>"
                })
                $("#searchResult").html(content)
            },
            error: function (jqXhr, textStatus, errorMessage) {
                alert("something went wrong when deleting post: " + jqXhr.responseText)
            }
        })
    })
</script>
</html>
