<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Users</title>
</head>
<body>

<table class="table table-bordered table-hover">
    <tr class="thead-dark">
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-orders"><h3><fmt:message key="menu.orders"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-users"><h3><fmt:message key="menu.users"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-profile"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

<div class="container">
    <table class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th class="text-center"><fmt:message key="user_id"/></th>
            <th class="text-center"><fmt:message key="user_login"/></th>
            <th class="text-center"><fmt:message key="user.block_status"/></th>
            <th class="text-center"><fmt:message key="actions"/></th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td class="text-center">${user.id}</td>
                <td class="text-center">${user.login}</td>
                <c:choose>
                    <c:when test="${user.blockStatus == 'BLOCKED'}">
                        <td class="text-center"><fmt:message key="user.blocked"/></td>
                    </c:when>
                    <c:otherwise>
                        <td class="text-center"><fmt:message key="user.unblocked"/></td>
                     </c:otherwise>
                </c:choose>
                <td class="text-center">
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/admin-users?userID=${user.id}&blockStatus=UNBLOCKED"><fmt:message key="unblock"/></a>
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin-users?userID=${user.id}&blockStatus=BLOCKED"><fmt:message key="block"/></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="container">

    <c:if test="${currentPage != 1}">
        <td><a href="${pageContext.request.contextPath}/admin-users?page=${currentPage - 1}"><fmt:message key="previous"/></a></td>
    </c:if>

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

    <c:if test="${currentPage lt pagesAmount}">
        <td><a href="${pageContext.request.contextPath}/admin-users?page=${currentPage + 1}"><fmt:message key="next"/></a></td>
    </c:if>

</div>

</body>
</html>
