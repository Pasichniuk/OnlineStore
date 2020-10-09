<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Add category</title>
</head>
<body>

<div class="container">

    <h1><fmt:message key="add_new_category"/></h1>
    <hr/>

    <div class="row">

        <div class="col-md-4">

            <form action="${pageContext.request.contextPath}/admin-catalog?action=ADD_CATEGORY" method="post">

                <div class="form-group">
                    <input class="form-control form-control-lg" type="text" minlength="3" name="categoryName" required placeholder="<fmt:message key="enter_category_name"/>"/>
                </div>

                <div class="form-group">
                    <input class="form-control form-control-lg" type="text" minlength="3" name="categoryNameRU" required placeholder="<fmt:message key="enter_category_name_ru"/>"/>
                </div>

                <button type="submit" class="btn btn-primary"><fmt:message key="admin.add_category"/></button>
            </form>

        </div>

    </div>

</div>

</body>
</html>
