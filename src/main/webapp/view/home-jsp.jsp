<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Home</title>
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

<h1 class="thead-light" align="center">Welcome!</h1>

</body>
</html>