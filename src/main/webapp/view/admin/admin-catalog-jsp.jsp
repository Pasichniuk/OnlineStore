<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Catalog</title>
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
    <form action="${pageContext.request.contextPath}/admin-catalog?action=ADD">
        <input type="submit" value="Add product" class="btn btn-primary">
    </form>
</div>

<div class="container">
    <table border="1" class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th>Name</th>
            <th>Category</th>
            <th>Addition date</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td>${product.name}</td>
                <td>${product.category}</td>
                <td>${product.additionDate}</td>
                <td>${product.price}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin-catalog?productID=${product.id}&action=EDIT">Edit</a>
                    /
                    <a href="${pageContext.request.contextPath}/admin-catalog?productID=${product.id}&action=DELETE">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
