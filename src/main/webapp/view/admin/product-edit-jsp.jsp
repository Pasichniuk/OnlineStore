<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Edit product</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/admin-catalog?action=EDIT&productID=<%= request.getParameter("productID") %>" method="post">
    <div class="container">
        <label>
            Enter new product name: <input type="text" name="productName"/> <br/>
            Enter new category: <input type="text" name="category"/> <br/>
            Enter new price: <input type="text" name="price"/> <br/>
            <button type="submit" class="btn btn-primary">Edit product</button>
        </label>
    </div>
</form>

</body>
</html>
