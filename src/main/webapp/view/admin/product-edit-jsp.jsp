<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Edit product</title>
</head>
<body>

<div class="container">

    <h1><fmt:message key="edit_product"/></h1>
    <hr/>

    <div class="row">

        <div class="col-md-4">

            <form action="${pageContext.request.contextPath}/admin-catalog?action=EDIT&productID=<%= request.getParameter("productID") %>" method="post">

                    <div class="form-group">
                        <input class="form-control form-control-lg" type="text" minlength="3" name="productName" required placeholder="<fmt:message key="enter_new_product_name"/>"/>
                    </div>

                    <div class="form-group">
                        <select class="form-control form-control-sm" name="category" title=<fmt:message key="category"/>>
                            <c:forEach items="${categories}" var="category">
                                <c:choose>
                                    <c:when test="${lang eq 'en'}">
                                        <option value="${category.name}">${category.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${category.name}">${category.nameRU}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <input class="form-control form-control-lg" type="number" min="0" max="10000" name="price" required placeholder="<fmt:message key="enter_new_product_price"/>"/>
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="edit_product"/></button>
            </form>

        </div>

    </div>

</div>

</body>
</html>
