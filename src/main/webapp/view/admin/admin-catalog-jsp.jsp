<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Catalog</title>
</head>
<body>

<table class="table table-bordered table-hover">
    <tr class="thead-dark">
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-orders"><h3><fmt:message key="menu.orders"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-users"><h3><fmt:message key="menu.users"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/admin-profile"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

<div class="container">
    <div class="row">
        <div class="col-md-6 text-right">
            <form action="${pageContext.request.contextPath}/view/admin/product-add-jsp.jsp">
                <button type="submit" class="btn btn-success"><fmt:message key="admin.add_product"/></button>
            </form>
        </div>
        <div class="col-md-6">
            <form action="${pageContext.request.contextPath}/view/admin/category-add-jsp.jsp">
                <button type="submit" class="btn btn-success"><fmt:message key="admin.add_category"/></button>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <table class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th class="text-center"><fmt:message key="product.name"/></th>
            <th class="text-center"><fmt:message key="product.category"/></th>
            <th class="text-center"><fmt:message key="product.addition_date"/></th>
            <th class="text-center"><fmt:message key="product.price"/></th>
            <th class="text-center"><fmt:message key="product.count"/></th>
            <th class="text-center"><fmt:message key="product.reserve"/></th>
            <th class="text-center"><fmt:message key="actions"/></th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td class="text-center">${product.name}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${lang eq 'en'}">
                            ${product.category}
                        </c:when>
                        <c:otherwise>
                            ${product.categoryRU}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${product.additionDate}</td>
                <td class="text-center">${product.price}</td>
                <td class="text-center">${product.count}</td>
                <td class="text-center">${product.reserve}</td>
                <td class="text-center">
                    <a class="btn btn-warning" href="${pageContext.request.contextPath}/view/admin/product-edit-jsp.jsp?productID=${product.id}"><fmt:message key="edit"/></a>
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin-catalog?productID=${product.id}"><fmt:message key="delete"/></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="container">

    <c:if test="${currentPage != 1}">
        <td><a href="${pageContext.request.contextPath}/admin-catalog?page=${currentPage - 1}"><fmt:message key="previous"/></a></td>
    </c:if>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${pagesAmount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="${pageContext.request.contextPath}/admin-catalog?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <c:if test="${currentPage lt pagesAmount}">
        <td><a href="${pageContext.request.contextPath}/admin-catalog?page=${currentPage + 1}"><fmt:message key="next"/></a></td>
    </c:if>

</div>

</body>
</html>
