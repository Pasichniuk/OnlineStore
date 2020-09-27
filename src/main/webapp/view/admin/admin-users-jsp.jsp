<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Users</title>
</head>
<body>

<table border="1" class="table table-striped table-bordered">
    <tr>
        <th><a href="${pageContext.request.contextPath}/admin-catalog"><h3>Catalog</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/admin-orders"><h3>Orders</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/admin-users"><h3>Users</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/admin-profile"><h3>Profile</h3></a></th>
    </tr>
</table>

<div class="container">
    <table border="1" class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th>User ID</th>
            <th>Login</th>
            <th>Block Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.blockStatus}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin-users?userID=${user.id}&blockStatus=BLOCKED">Block</a>
                    |
                    <a href="${pageContext.request.contextPath}/admin-users?userID=${user.id}&blockStatus=UNBLOCKED">Unblock</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="container">

    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${currentPage != 1}">
        <td><a href="${pageContext.request.contextPath}/admin-users?page=${currentPage - 1}">Previous</a></td>
    </c:if>

    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${pagesAmount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="${pageContext.request.contextPath}/admin-users?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <%--For displaying Next link --%>
    <c:if test="${currentPage lt pagesAmount}">
        <td><a href="${pageContext.request.contextPath}/admin-users?page=${currentPage + 1}">Next</a></td>
    </c:if>

</div>

</body>
</html>
