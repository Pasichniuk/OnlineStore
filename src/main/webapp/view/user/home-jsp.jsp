<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/custom.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Home</title>
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

<h1 class="thead-light text-center">
    <ex:Welcome>
        <fmt:message key="home_jsp.welcome"/>
    </ex:Welcome>
</h1>

<div>
    <h5 class="fixed-bottom text-center">
        <hr>
        <tf:tagfile/>
        <hr>
    </h5>
</div>

</body>
</html>