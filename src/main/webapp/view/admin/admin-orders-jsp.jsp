<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Orders</title>
</head>
<body>

<%@ include file="admin-menu.jspf" %>

<div class="container">
    <table class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th class="text-center"><fmt:message key="order_id"/></th>
            <th class="text-center"><fmt:message key="user"/></th>
            <th class="text-center"><fmt:message key="order_status"/></th>
            <th class="text-center"><fmt:message key="actions"/></th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td class="text-center">${order.orderID}</td>
                <td class="text-center">${order.userLogin}</td>
                <c:choose>
                    <c:when test="${order.status == 'PAID'}">
                        <td class="text-center"><fmt:message key="order_paid"/></td>
                    </c:when>
                    <c:when test="${order.status == 'REGISTERED'}">
                        <td class="text-center"><fmt:message key="order_registered"/></td>
                    </c:when>
                    <c:otherwise>
                        <td class="text-center"><fmt:message key="order_cancelled"/></td>
                    </c:otherwise>
                </c:choose>
                <td class="text-center">
                    <a class="btn btn-success" href="${pageContext.request.contextPath}/admin-orders?orderID=${order.orderID}&status=PAID"><fmt:message key="order_paid"/></a>
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin-orders?orderID=${order.orderID}&status=CANCELLED"><fmt:message key="order_cancelled"/></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="container">

    <c:if test="${currentPage != 1}">
        <td><a href="${pageContext.request.contextPath}/admin-orders?page=${currentPage - 1}"><fmt:message key="previous"/></a></td>
    </c:if>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${pagesAmount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="${pageContext.request.contextPath}/admin-orders?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <c:if test="${currentPage lt pagesAmount}">
        <td><a href="${pageContext.request.contextPath}/admin-orders?page=${currentPage + 1}"><fmt:message key="next"/></a></td>
    </c:if>

</div>

</body>
</html>
