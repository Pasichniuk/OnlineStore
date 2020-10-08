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
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/home"><h3><fmt:message key="menu.home"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/catalog"><h3><fmt:message key="menu.catalog"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/cart"><h3><fmt:message key="menu.cart"/></h3></a></th>
        <th class="text-center"><a class="text-light" href="${pageContext.request.contextPath}/log-in"><h3><fmt:message key="menu.profile"/></h3></a></th>
    </tr>
</table>

<form action="${pageContext.request.contextPath}/catalog">
    <table class="table table-borderless">
        <tr class="thead-light">
            <th class="text-center">
                <fmt:message key="choose_sorting"/>
            </th>
            <th class="text-center">
                <fmt:message key="choose_category"/>
            </th>
        </tr>
        <tr>
            <td class="text-center">
                <select class="form-control form-control-sm" name="Sort" title=<fmt:message key="sort"/>>
                    <option value="a-z" selected><fmt:message key="sort.a_z"/></option>
                    <option value="z-a"><fmt:message key="sort.z_a"/></option>
                    <option value="cheap-expensive"><fmt:message key="sort.cheap_expensive"/></option>
                    <option value="expensive-cheap"><fmt:message key="sort.expensive_cheap"/></option>
                    <option value="older-newer"><fmt:message key="sort.older_newer"/></option>
                    <option value="newer-older"><fmt:message key="sort.newer_older"/></option>
                </select>
            </td>
            <td class="text-center">
                <select class="form-control form-control-sm" name="Category" title=<fmt:message key="category"/>>
                    <option value="All" selected><fmt:message key="category.all"/></option>

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
            </td>
        </tr>
        <tr>
            <td>
                <input class="form-control form-control-sm" type="number" name="minPrice"  min="0" max="10000" placeholder="<fmt:message key="enter_min_price"/>"/>
            </td>
            <td>
                <input class="form-control form-control-sm" type="number" name="maxPrice" min="0" max="10000" placeholder="<fmt:message key="enter_max_price"/>"/>
            </td>
        </tr>
    </table>
    <div class="text-center">
        <input type="submit" value="<fmt:message key="apply"/>" class="btn btn-primary">
    </div>
</form>

<div class="container">
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
                       <form action="${pageContext.request.contextPath}/catalog?ProductID=${product.id}" method="post">
                           <input type="submit" value="<fmt:message key="product.add_to_cart"/>" class="btn btn-success"/>
                       </form>
                   </td>
               </tr>
           </c:forEach>
       </table>
</div>

<div class="container">

    <c:if test="${currentPage != 1}">
        <td><a href="${pageContext.request.contextPath}/catalog?page=${currentPage - 1}"><fmt:message key="previous"/></a></td>
    </c:if>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${pagesAmount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="${pageContext.request.contextPath}/catalog?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <c:if test="${currentPage lt pagesAmount}">
        <td><a href="${pageContext.request.contextPath}/catalog?page=${currentPage + 1}"><fmt:message key="next"/></a></td>
    </c:if>

</div>

</body>
</html>
