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
        <th><a href="${pageContext.request.contextPath}/home"><h3>Home</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/catalog"><h3>Catalog</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/cart"><h3>Cart</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/profile"><h3>Profile</h3></a></th>
    </tr>
</table>

<div class="container">
    <h3 align="center">User: ${userLogin} <a href="${pageContext.request.contextPath}/profile?logout=true"><h3>Log out</h3></a> </h3>
    <hr>
</div>

<div class="container">
    <table border="1" class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th>Order ID</th>
            <th>Order Status</th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.orderID}</td>
                <td>${order.status}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>