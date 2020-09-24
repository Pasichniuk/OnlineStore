<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Orders</title>
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
            <th>Order ID</th>
            <th>User ID</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.orderID}</td>
                <td>${order.userID}</td>
                <td>${order.status}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin-orders?orderID=${order.orderID}&status=PAID">PAID</a>
                    |
                    <a href="${pageContext.request.contextPath}/admin-orders?orderID=${order.orderID}&status=CANCELLED">CANCELLED</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
