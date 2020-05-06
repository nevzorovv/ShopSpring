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

<h3>Order management</h3>

<section>
    <table style="padding: 20px"><table class="table">
        <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Customer</th>
                <th scope="col">Date</th>
                <th scope="col">Description</th>
                <th scope="col">Total price</th>
                <th scope="col">Current status</th>
                <th scope="col">Status change</th>
            </tr>
        </thead>
        <c:forEach items="${orders}" var="order">
            <tr>
                <th>${order.id}</th>
                <td>${order.user.login}</td>
                <td>${order.date}</td>
                <td>
                    <c:forEach items="${order.orderedProducts}" var="product">
                        Id: ${product.product.id}, ${product.product.manufacturer} ${product.product.model}, quantity: ${product.quantity}
                    </c:forEach>
                </td>
                <td>${order.totalPrice}</td>
                <td>${order.status}</td>
                <td>
                    <c:if test="${order.status != 'DELIVERED'}">
                        <form:form method="post" action="/status_change" modelAttribute="newStatus">
                            <form:select path="status">
                                <form:option value="" label="---Status change---"/>
                                <c:if test="${order.status == 'CREATED'}">
                                    <form:options items="${statusCREATED}" itemLabel="translateName"/>
                                </c:if>
                                <c:if test="${order.status == 'CONFIRMED'}">
                                    <form:options items="${statusCONFIRMED}" itemLabel="translateName"/>
                                </c:if>
                                <c:if test="${order.status == 'IN_PROGRESS'}">
                                    <form:options items="${statusINPROGRESS}" itemLabel="translateName"/>
                                </c:if>
                            </form:select>
                            <form:hidden path="id" value="${order.id}"/>
                            <button type="submit" class="btn btn-primary">Change</button>
                        </form:form>

                        <%--<form method="get" action="status_change">
                            <select name="newStatus">
                                <form:option value="" label="---Status change---"/>
                                <c:if test="${order.status == 'CREATED'}">
                                    <form:options items="${statusCREATED}" itemLabel="translateName"/>
                                </c:if>
                                <c:if test="${order.status == 'CONFIRMED'}">
                                    <form:options items="${statusCONFIRMED}" itemLabel="translateName"/>
                                </c:if>
                                <c:if test="${order.status == 'IN_PROGRESS'}">
                                    <form:options items="${statusINPROGRESS}" itemLabel="translateName"/>
                                </c:if>
                            </select>
                            <input type="hidden" name="orderId" value="${order.id}">
                            <input type="submit" value="Change">
                        </form>--%>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>


</body>
</html>




