<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Profile</title>
</head>
<body>

<%@ include file="admin-menu.jspf" %>

<table class="table table-striped table-bordered">
    <tr>
        <th class="text-center"><h5><fmt:message key="admin"/>: ${adminName}</h5></th>
        <th class="text-center"><h5><a class="btn btn-danger" href="${pageContext.request.contextPath}/log-out"><fmt:message key="profile.logout"/></a></h5></th>
        <th class="text-center"><h5><fmt:message key="language"/>:
            <a href="${pageContext.request.contextPath}/admin-profile?lang=en">EN</a>
            |
            <a href="${pageContext.request.contextPath}/admin-profile?lang=ru">RU</a>
        </h5></th>
    </tr>
</table>

</body>
</html>
