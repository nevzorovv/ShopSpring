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

<header>Your order</header>
<section>
    <form:form method="post" action="order_confirmed" modelAttribute="order">
        <table style="padding: 20px">
            <tr>
                <th>Manufacturer</th>
                <th>Model</th>
                <th>Price</th>
                <th>Discount</th>
            </tr>
            <c:forEach items="${products}" var="product">
                <tr>
                    <th>${product.manufacturer}</th>
                    <th>${product.model}</th>
                    <th>${product.price}</th>
                    <th>${product.discount}</th>
                </tr>
            </c:forEach>
        </table>
    </form:form>
</section>

</body>
</html>