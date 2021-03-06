<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Log In</title>
</head>
<body>

<%@ include file="/view/user/user-menu.jspf" %>

    <div class="container">
        <form action="${pageContext.request.contextPath}/log-in" method="post">
            <div class="card">
                <div class="card-header text-center">
                   <h2><fmt:message key="login"/></h2>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <input type="text" minlength="3" name="login" required class="form-control" placeholder="<fmt:message key="enter_login"/>">
                    </div>
                    <div class="form-group">
                        <input type="password" minlength="3" name="password" required class="form-control" placeholder="<fmt:message key="enter_password"/>">
                    </div>
                </div>
                <div class="card-footer text-center">
                    <input type="submit" value="<fmt:message key="login"/>" class="btn btn-primary"/>
                    <p><a href="${pageContext.request.contextPath}/registration"><fmt:message key="not_registered_yet"/></a></p>
                </div>
            </div>
        </form>
    </div>

<div class="container">
    <h5 class="text-center">
        <fmt:message key="language"/>:
        <a href="${pageContext.request.contextPath}/log-in?lang=en">EN</a>
        |
        <a href="${pageContext.request.contextPath}/log-in?lang=ru">RU</a>
    </h5>
</div>

</body>
</html>
