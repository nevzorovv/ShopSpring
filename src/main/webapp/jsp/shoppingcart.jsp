<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="styles.css" media="all">
</head>
<body>
<%@ include file="basket_footer.jsp" %>

<header>Your shopping cart</header>

<section>
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
            <form method="post" action="/changeQuantity">
                <th>
                    <input type="text" name="quantity" id="quantity" value="${orderedProduct.quantity}" style="width: 50px">
                    <input type="hidden" name="orderedProductId" value="${orderedProduct.id}">
                </th>
                <th>
                    <input type="submit" value="recalculate">
                </th>
            </form>
            <th>
                <a href="deleteOrderedProduct?id=${orderedProduct.id}"><button type="submit">delete</button></a>
            </th>
        </tr>
    </c:forEach>
</table>
    <p>Total discount: ${totalDiscount}</p>
    <p>Total price: ${totalPrice}</p>

<a href="orderinformation?id=${shoppingCartId}"><button type="submit">Confirm order</button></a>
</section>
</body>
</html>