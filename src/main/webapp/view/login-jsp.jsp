<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Log In</title>
</head>
<body>

<table border="1" class="table table-striped table-bordered">
    <tr>
        <th><a href="${pageContext.request.contextPath}/home"><h3><fmt:message key="menu.home"/></h3></a></th>
        <th><a href="${pageContext.request.contextPath}/catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th><a href="${pageContext.request.contextPath}/cart"><h3><fmt:message key="menu.cart"/></h3></a></th>
        <th><a href="${pageContext.request.contextPath}/log-in"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

    <div class="container">
        <form action="${pageContext.request.contextPath}/log-in" method="post">
            <div class="card">
                <div class="card-header" align="center">
                   <h2><fmt:message key="login"/></h2>
                    <a href="${pageContext.request.contextPath}/log-in?lang=en"><h3>EN</h3></a>
                    <a href="${pageContext.request.contextPath}/log-in?lang=ru"><h3>RU</h3></a>
                </div>
                <div class="card-body">
                    <div class="form-group">
                        <input type="text" name="login" class="form-control" placeholder="<fmt:message key="enter_login"/>">
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" class="form-control" placeholder="<fmt:message key="enter_password"/>">
                    </div>
                </div>
                <div class="card-footer" align="center">
                    <input type="submit" value="<fmt:message key="login"/>" class="btn btn-primary"/>
                    <p><a href="${pageContext.request.contextPath}/registration"><fmt:message key="not_registered_yet"/></a></p>
                </div>
            </div>
        </form>
    </div>

</body>
</html>
