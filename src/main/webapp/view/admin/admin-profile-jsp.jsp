<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Profile</title>
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
    <h3 align="center">Admin: ${adminLogin}
        <a href="${pageContext.request.contextPath}/log-out"><h3>Log out</h3></a>
    </h3>
    <hr>
</div>

</body>
</html>
