<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Profile</title>
</head>
<body>

<table border="1" class="table table-striped table-bordered">
    <tr>
        <th><a href="${pageContext.request.contextPath}/admin-catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th><a href="${pageContext.request.contextPath}/admin-orders"><h3><fmt:message key="menu.orders"/></h3></a></th>
        <th><a href="${pageContext.request.contextPath}/admin-users"><h3><fmt:message key="menu.users"/></h3></a></th>
        <th><a href="${pageContext.request.contextPath}/admin-profile"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

<div class="container">
    <h3 align="center"><fmt:message key="admin"/>: ${adminLogin}
        <a href="${pageContext.request.contextPath}/log-out"><h3><fmt:message key="profile.logout"/></h3></a>
        <a href="${pageContext.request.contextPath}/admin-profile?lang=en"><h3>EN</h3></a>
        <a href="${pageContext.request.contextPath}/admin-profile?lang=ru"><h3>RU</h3></a>
    </h3>
    <hr>
</div>

</body>
</html>
