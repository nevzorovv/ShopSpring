<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="styles.css" media="all">
</head>
<body>
<%@ include file="basket_footer.jsp" %>

<header>Order form</header>

<section>
    <form:form action="saveNewOrder?id=${cartId}" method="post" modelAttribute="order">
        <p>Choose payment method:
        <form:radiobuttons path="payment.type" items="${paymentTypes}"/></p>
        <p>Choose shipment method:
        <form:radiobuttons path="shipment.type" items="${shipmentTypes}"/></p>
        <%--Нужно, чтобы при выборе типа доставки "курьером" появлялись поля для ввода адреса--%>
        <p><input type="submit" value="Send"></p>
    </form:form>
</section>

</body>
</html>