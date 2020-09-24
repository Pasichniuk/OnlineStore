<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Add product</title>
</head>
<body>

<div class="container">

    <h1>Add new product</h1>
    <hr/>

    <div class="row">

        <div class="col-md-4">

            <form action="${pageContext.request.contextPath}/admin-catalog?action=ADD" method="post">

                    <div class="form-group">
                        <label>
                            <input type="text" name="productName" placeholder="Enter product name" class="form-control"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>
                            <input type="text" name="category" placeholder="Enter category" class="form-control"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>
                            <input type="text" name="price" placeholder="Enter price" class="form-control"/>
                        </label>
                    </div>

                    <button type="submit" class="btn btn-primary">Add product</button>
            </form>

        </div>

    </div>

</div>

</body>
</html>
