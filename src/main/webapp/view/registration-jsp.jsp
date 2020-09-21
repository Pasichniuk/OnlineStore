<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Registration</title>
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
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <div class="card">
            <div class="card-header" align="center">
                <h2>Registration</h2>
            </div>
            <div class="card-body">
                <div class="form-group">
                    <input type="text" name="login" class="form-control" placeholder="Enter login">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="Enter password">
                </div>
                <div class="form-group">
                    <input type="password" name="confirm-password" class="form-control" placeholder="Confirm password">
                </div>
            </div>
            <div class="card-footer" align="center">
                <input type="submit" value="Register" class="btn btn-primary"/>
            </div>
        </div>
    </form>
</div>

</body>
</html>
