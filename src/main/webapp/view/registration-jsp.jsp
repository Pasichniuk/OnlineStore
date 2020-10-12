<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Registration</title>
</head>
<body>

<%@ include file="/view/user/user-menu.jspf" %>

<div class="container">
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <div class="card">
            <div class="card-header text-center">
                <h2><fmt:message key="registration"/></h2>
            </div>
            <div class="card-body">
                <div class="form-group">
                    <input type="text" minlength="3" name="login" required class="form-control" placeholder="<fmt:message key="enter_login"/>">
                </div>
                <div class="form-group">
                    <input type="password" minlength="3" name="password" required class="form-control" placeholder="<fmt:message key="enter_password"/>">
                </div>
                <div class="form-group">
                    <input type="password" minlength="3" name="confirm-password" required class="form-control" placeholder="<fmt:message key="confirm_password"/>">
                </div>
                <div class="form-group">
                    <input type="text" minlength="3" name="userName" required class="form-control" placeholder="<fmt:message key="enter_full_name"/>">
                </div>
                <div class="form-group">
                    <input type="text" minlength="3" name="userNameRU" required class="form-control" placeholder="<fmt:message key="enter_full_name_ru"/>">
                </div>
            </div>
            <div class="card-footer text-center">
                <input type="submit" value="<fmt:message key="register"/>" class="btn btn-primary"/>
            </div>
        </div>
    </form>
</div>

</body>
</html>
