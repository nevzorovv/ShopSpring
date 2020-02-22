<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="styles.css" media="all">
</head>
<body>

<header>${category_name}</header>

<section>
    <form:form method="post" action="products" modelAttribute="category">
        <form:select path="name">
            <form:option value="" label="-Please select-"/>
            <form:options items="${categories}" itemValue="name" itemLabel="name"/>
            <input type="submit" value="search">
        </form:select>
    </form:form>

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
            <th>${product.discount.value} ${product.discount.type}</th>
        </tr>
    </c:forEach>
</table>

<button name="addProduct" id="addProduct" onclick="document.location='addproduct'">Add product</button>
</section>
</body>
</html>