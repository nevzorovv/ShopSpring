<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <%--<link type="text/css" rel="stylesheet" href="styles.css" media="all">--%>
</head>
<body>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <%-- <link type="text/css" rel="stylesheet" href="styles.css" media="all">--%>
</head>
<body>
<%@ include file="navbar.jsp" %>

<h3>Your orders</h3>

<section>
    <c:if test="${usersOrders == null}">
        You dont have any order yet? Go and buy something <a href="/products">now!</a>
    </c:if>
    <c:if test="${usersOrders != null}">
        <table style="padding: 20px"><table class="table">
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Date</th>
                <th scope="col">Description</th>
                <th scope="col">Total price</th>
                <th scope="col">Current status</th>
                <th scope="col">Status change</th>
            </tr>
            </thead>
            <c:forEach items="${usersOrders}" var="order">
                <tr>
                    <th>${order.id}</th>
                    <td>${order.date}</td>
                    <td>
                        <c:forEach items="${order.orderedProducts}" var="orderedProduct">
                            ${orderedProduct.product.manufacturer} ${orderedProduct.product.model} - ${orderedProduct.quantity} pcs<br>
                        </c:forEach>
                    </td>
                    <td>${order.totalPrice}</td>
                    <td>${order.status}</td>
                    <td>
                        <form:form method="post" action="/status_change" modelAttribute="confirmedByUser">
                            <form:hidden path="id" value="${order.id}"/>
                            <form:hidden path="status" value="CONFIRMED"/>
                            <button type="submit" class="btn btn-primary">Confirm receiving</button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</section>


</body>
</html>
</body>
</html>