<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="styles.css" media="all">
</head>
<body>

<header>Order management</header>

<section>
    <table style="padding: 20px">
        <tr>
            <th>ID</th>
            <th>Customer</th>
            <th>Date</th>
            <th>Description</th>
            <th>Total price</th>
            <th>Current status</th>
            <th>Status change</th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <th>${order.id}</th>
                <th>${order.user.login}</th>
                <th>${order.date}</th>
                <th>
                    <c:forEach items="${order.orderedProducts}" var="product">
                        Id: ${product.product.id}, ${product.product.manufacturer} ${product.product.model}, quantity: ${product.quantity}
                    </c:forEach>
                </th>
                <th>${order.totalPrice}</th>
                <th>${order.status}</th>
                <th>
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
                            <input type="submit" value="Change">
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
                </th>
            </tr>
        </c:forEach>
    </table>
</section>


</body>
</html>