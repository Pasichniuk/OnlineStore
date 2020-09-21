<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Cart</title>
</head>
<body>

<table border="1" class="table table-striped table-bordered">
    <tr>
        <th><a href="${pageContext.request.contextPath}/home"><h3>Home</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/catalog"><h3>Catalog</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/cart"><h3>Cart</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/log-in"><h3>Profile</h3></a></th>
    </tr>
</table>

<div class="container">
    <h5>
        Total price = <c:out value="${totalPrice}"/>
    </h5>
    <form action="${pageContext.request.contextPath}/order" method="post">
        <input type="submit" value="Buy" class="btn btn-primary">
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
                    <a href="${pageContext.request.contextPath}/cart?ProductID=${product.id}">Remove from cart</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
