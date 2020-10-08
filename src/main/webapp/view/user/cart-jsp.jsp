<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Cart</title>
</head>
<body>

<table class="table table-bordered table-hover">
    <tr class="thead-dark">
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/home"><h3><fmt:message key="menu.home"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/cart"><h3><fmt:message key="menu.cart"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/log-in"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

<div class="container">
    <form action="${pageContext.request.contextPath}/order" method="post">
        <h5 class="text-center text-info">
            <fmt:message key="total_price"/>: ${totalPrice}
        </h5>
        <h5 class="text-center">
            <input type="submit" value="<fmt:message key="buy"/>" class="btn btn-success">
        </h5>
    </form>

    <table class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th class="text-center"><fmt:message key="product.name"/></th>
            <th class="text-center"><fmt:message key="product.category"/></th>
            <th class="text-center"><fmt:message key="product.addition_date"/></th>
            <th class="text-center"><fmt:message key="product.price"/></th>
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
                <td class="text-center">
                    <form action="${pageContext.request.contextPath}/cart?ProductID=${product.id}" method="post">
                        <input type="submit" value="<fmt:message key="product.remove"/>" class="btn btn-danger"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="container">

    <c:if test="${currentPage != 1}">
        <td><a href="${pageContext.request.contextPath}/cart?page=${currentPage - 1}"><fmt:message key="previous"/></a></td>
    </c:if>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${pagesAmount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="${pageContext.request.contextPath}/cart?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <c:if test="${currentPage lt pagesAmount}">
        <td><a href="${pageContext.request.contextPath}/cart?page=${currentPage + 1}"><fmt:message key="next"/></a></td>
    </c:if>

</div>

</body>
</html>
