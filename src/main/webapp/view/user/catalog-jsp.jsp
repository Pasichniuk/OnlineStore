<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel='stylesheet' href='https://unpkg.com/bootstrap@4.5.2/dist/css/bootstrap.min.css'>

<html>
<head>
    <title>Catalog</title>
</head>
<body>

<table border="1" class="table table-striped table-bordered">
    <tr>
        <th><a href="${pageContext.request.contextPath}/home"><h3>Home</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/catalog"><h3>Catalog</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/cart"><h3>Cart</h3></a></th>
        <th><a href="${pageContext.request.contextPath}/log-in"><h3>Profile</h3></a></th>
    </tr>
</table>


<div class="container">

    <form action="${pageContext.request.contextPath}/catalog">
        <label>
            <select name="Sort">
                <option value="a-z" selected>A-Z</option>
                <option value="z-a">Z-A</option>
                <option value="cheap-expensive">Cheap-Expensive</option>
                <option value="expensive-cheap">Expensive-Cheap</option>
                <option value="older-newer">Older-Newer</option>
                <option value="newer-older">Newer-Older</option>
            </select>
        </label>
        <input type="submit" value="Sort" class="btn btn-primary">
    </form>

    <form action="${pageContext.request.contextPath}/catalog">
        <label>
            <select name="Category">
                <option value="All" selected>All</option>
                <option value="Phones">Phones</option>
                <option value="Laptops">Laptops</option>
                <option value="Headphones">Headphones</option>
                <option value="Home appliances">Home appliances</option>
            </select>
        </label>
        <input type="submit" value="Apply" class="btn btn-primary">
    </form>

    <form action="${pageContext.request.contextPath}/catalog">
        <label>
            <input type="number" name="minPrice"  min="0" placeholder="Enter min price"/>
            <input type="number" name="maxPrice" max="10_000" placeholder="Enter max price"/>
        </label>
        <input type="submit" value="Apply" class="btn btn-primary">
    </form>

</div>

<div class="container">
       <table border="1" class="table table-striped table-bordered">
           <tr class="thead-dark">
               <th>Name</th>
               <th>Category</th>
               <th>Addition date</th>
               <th>Price</th>
               <th>Actions</th>
           </tr>
           <c:forEach items="${products}" var="product">
               <tr>
                   <td>${product.name}</td>
                   <td>${product.category}</td>
                   <td>${product.additionDate}</td>
                   <td>${product.price}</td>
                   <td>
                       <a href="${pageContext.request.contextPath}/catalog?ProductID=${product.id}" >Add to cart</a>
                   </td>
               </tr>
           </c:forEach>
       </table>
</div>

<div class="container">

    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${currentPage != 1}">
        <td><a href="${pageContext.request.contextPath}/catalog?page=${currentPage - 1}">Previous</a></td>
    </c:if>

    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
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

    <%--For displaying Next link --%>
    <c:if test="${currentPage lt pagesAmount}">
        <td><a href="${pageContext.request.contextPath}/catalog?page=${currentPage + 1}">Next</a></td>
    </c:if>

</div>

</body>
</html>
