<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Add product</title>
</head>
<body>

<div class="container">

    <h1><fmt:message key="add_new_product"/></h1>
    <hr/>

    <div class="row">

        <div class="col-md-4">

            <form action="${pageContext.request.contextPath}/admin-catalog?action=ADD" method="post">

                    <div class="form-group">
                        <label>
                            <input type="text" name="productName" placeholder="<fmt:message key="enter_product_name"/>" class="form-control"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>
                            <input type="text" name="category" placeholder="<fmt:message key="enter_product_category"/>" class="form-control"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label>
                            <input type="number" min="0" name="price" placeholder="<fmt:message key="enter_product_price"/>" class="form-control"/>
                        </label>
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="admin.add_product"/></button>
            </form>

        </div>

    </div>

</div>

</body>
</html>
