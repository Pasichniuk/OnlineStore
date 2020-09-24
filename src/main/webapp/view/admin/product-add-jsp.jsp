<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Add product</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/admin-catalog" method="post">
<div class="container">
    <label>
        Enter product name: <input type="text" name="productName"/> <br/>
        Enter category: <input type="text" name="category"/> <br/>
        Enter price: <input type="text" name="price"/> <br/>
        <button type="submit" class="btn btn-primary">Add product</button>
    </label>
</div>
</form>

</body>
</html>
