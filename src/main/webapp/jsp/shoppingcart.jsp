<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <th></th>
    </tr>
    <c:forEach items="${buyingProducts}" var="product">
        <tr>
            <th>${product.manufacturer}</th>
            <th>${product.model}</th>
            <th>${product.price}</th>
            <th>${product.discount.value} ${product.discount.type}</th>
        </tr>
    </c:forEach>
</table>
    <p>Total discount: ${totalDiscount}</p>
    <p>Total price: ${totalPrice}</p>

<a href="orderinformation?id=${shoppingCartId}"><button type="submit">Confirm order</button></a>
</section>
</body>
</html>