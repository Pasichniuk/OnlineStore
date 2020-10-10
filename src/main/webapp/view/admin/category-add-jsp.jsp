<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Add category</title>
</head>
<body>

<%@ include file="admin-menu.jspf" %>

    <div class="container">
        <form action="${pageContext.request.contextPath}/admin-catalog?action=ADD_CATEGORY" method="post">
            <div class="card">
                <div class="card-header text-center">
                    <h2><fmt:message key="add_new_category"/></h2>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <input class="form-control" type="text" minlength="3" name="categoryName" required placeholder="<fmt:message key="enter_category_name"/>"/>
                    </div>

                    <div class="form-group">
                        <input class="form-control" type="text" minlength="3" name="categoryNameRU" required placeholder="<fmt:message key="enter_category_name_ru"/>"/>
                    </div>
                </div>
                <div class="card-footer text-center">
                    <button type="submit" class="btn btn-primary"><fmt:message key="admin.add_category"/></button>
                </div>
            </div>
        </form>
    </div>

</body>
</html>
