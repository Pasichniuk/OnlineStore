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

<%@ include file="user-menu.jspf" %>

<h2 class="thead-light text-center">
    <ex:Welcome>
        <fmt:message key="home_jsp.welcome"/>
    </ex:Welcome>
</h2>

<div>
    <h5 class="fixed-bottom text-center">
        <hr>
        <tf:tagfile/>
        <hr>
    </h5>
</div>

</body>
</html>