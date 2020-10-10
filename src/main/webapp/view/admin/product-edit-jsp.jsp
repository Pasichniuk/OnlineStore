<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Edit product</title>
</head>
<body>

<%@ include file="admin-menu.jspf" %>

<div class="container">
    <form action="${pageContext.request.contextPath}/admin-catalog?action=EDIT&productID=<%= request.getParameter("productID") %>" method="post">        <div class="card">
            <div class="card-header text-center">
                <h2><fmt:message key="edit_product"/></h2>
            </div>
            <div class="card-body">
                <div class="form-group">
                    <input class="form-control" type="text" minlength="3" name="productName" required placeholder="<fmt:message key="enter_new_product_name"/>"/>
                </div>

                <div class="form-group">
                    <select class="form-control" name="category" title=<fmt:message key="category"/>>
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
                    <input class="form-control" type="number" min="0" max="10000" name="price" required placeholder="<fmt:message key="enter_new_product_price"/>"/>
                </div>
            </div>
            <div class="card-footer text-center">
                <button type="submit" class="btn btn-primary"><fmt:message key="edit_product"/></button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
