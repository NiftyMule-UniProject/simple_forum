<h2><a href="${pageContext.request.contextPath}/">Homepage</a></h2>
<sec:authorize access="isAuthenticated()">
    Hello! <sec:authentication property="principal.username" /> <a href="${pageContext.request.contextPath}/perform_logout">Logout</a>
    <sec:authorize access="hasAnyAuthority('superuser')">
        <a href="${pageContext.request.contextPath}/admin"> Admin portal </a>
    </sec:authorize>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    <a href="${pageContext.request.contextPath}/login">Login</a> <br/>
    <a href="${pageContext.request.contextPath}/register">Register</a>
</sec:authorize>