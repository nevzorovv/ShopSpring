<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="style/styles.css" media="all">
</head>
<body>
<%@ include file="navbar.jsp" %>

<h3>Your Shopping cart</h3>

<section>
    <c:if test="${countItems == 0}">Your shopping cart is empty. Go and buy something <a href="/products">now!</a></c:if>
    <c:if test="${countItems != 0}">
        <table style="padding: 20px">
            <tr>
                <th>Manufacturer</th>
                <th>Model</th>
                <th>Price</th>
                <th>Discount</th>
                <th>Quantity</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${orderedProducts}" var="orderedProduct">
                <tr>
                    <th>${orderedProduct.product.manufacturer}</th>
                    <th>${orderedProduct.product.model}</th>
                    <th>${orderedProduct.product.price}</th>
                    <th>${orderedProduct.product.discount.value} ${orderedProduct.product.discount.type}</th>
                    <form:form method="post" action="/changeQuantity" modelAttribute="newQuantityObject">
                        <th>
                            <form:input path="quantity" value="${orderedProduct.quantity}" cssStyle="width: 50px"/>
                            <form:hidden path="id" value="${orderedProduct.id}"/>
                        </th>
                        <th>
                            <input type="submit" value="recalculate">
                        </th>
                    </form:form>
                    <th>
                        <a href="deleteOrderedProduct?id=${orderedProduct.id}"><button type="submit">delete</button></a>
                    </th>
                </tr>
            </c:forEach>
        </table>
            <p>Total discount: ${totalDiscount}</p>
            <p>Total price: ${totalPrice}</p>

        <a href="orderinformation?id=${shoppingCartId}"><button type="submit">Confirm order</button></a>
    </c:if>
</section>
</body>
</html>