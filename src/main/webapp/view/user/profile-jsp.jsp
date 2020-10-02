<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Profile</title>
</head>
<body>

<table class="table table-bordered table-hover">
    <tr class="thead-dark">
        <th><a class="text-light" href="${pageContext.request.contextPath}/home"><h3><fmt:message key="menu.home"/></h3></a></th>
        <th><a class="text-light" href="${pageContext.request.contextPath}/catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th><a class="text-light" href="${pageContext.request.contextPath}/cart"><h3><fmt:message key="menu.cart"/></h3></a></th>
        <th><a class="text-light" href="${pageContext.request.contextPath}/log-in"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

<table class="table table-bordered">
    <tr>
        <th><h5><fmt:message key="profile.user"/>: ${userName}</h5></th>
        <th><h5><a href="${pageContext.request.contextPath}/log-out"><fmt:message key="profile.logout"/></a></h5></th>
        <th><h5><fmt:message key="language"/>:
            <a href="${pageContext.request.contextPath}/profile?lang=en">EN</a>
            |
            <a href="${pageContext.request.contextPath}/profile?lang=ru">RU</a>
        </h5></th>
    </tr>
</table>

<div class="container">
    <table class="table table-striped table-bordered">
        <tr class="thead-dark">
            <th><fmt:message key="order_id"/></th>
            <th><fmt:message key="order_status"/></th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td class="text-center">${order.orderID}</td>
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
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>