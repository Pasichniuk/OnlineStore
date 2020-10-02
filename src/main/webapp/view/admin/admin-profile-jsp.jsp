<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Profile</title>
</head>
<body>

<table class="table table-bordered table-hover">
    <tr class="thead-dark">
        <th><a class="text-light" href="${pageContext.request.contextPath}/admin-catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th><a class="text-light" href="${pageContext.request.contextPath}/admin-orders"><h3><fmt:message key="menu.orders"/></h3></a></th>
        <th><a class="text-light" href="${pageContext.request.contextPath}/admin-users"><h3><fmt:message key="menu.users"/></h3></a></th>
        <th><a class="text-light" href="${pageContext.request.contextPath}/admin-profile"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

<table class="table table-striped table-bordered">
    <tr>
        <th><h5><fmt:message key="admin"/>: ${adminName}</h5></th>
        <th><h5><a href="${pageContext.request.contextPath}/log-out"><fmt:message key="profile.logout"/></a></h5></th>
        <th><h5><fmt:message key="language"/>:
            <a href="${pageContext.request.contextPath}/admin-profile?lang=en">EN</a>
            |
            <a href="${pageContext.request.contextPath}/admin-profile?lang=ru">RU</a>
        </h5></th>
    </tr>
</table>

</body>
</html>
