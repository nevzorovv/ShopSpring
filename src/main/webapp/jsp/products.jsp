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

<h3>${category_name}</h3>

<section>
    <form:form method="post" action="products" modelAttribute="category">
        <form:select path="name">
            <form:option value="" label="-Please select-"/>
            <form:options items="${categories}" itemValue="name" itemLabel="name"/>
            <input type="submit" value="search">
        </form:select>
    </form:form>

    <%--<br><c:if test="${param.allow != null && !param.allow}"><label style="color:red">You are not allowed to buy products</label></c:if>--%>
    <br><c:if test="${param.allow != null && !param.allow}"><div class="alert alert-info" role="alert">You are not allowed to buy products</div></c:if>

    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">Picture</th>
            <th scope="col">Product</th>
            <th scope="col">Price</th>
            <th scope="col">Discount</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${products}" var="product">
                <tr>
                    <th scope="row">Picture</th>
                    <td><a href="/product?id=${product.id}">${product.manufacturer} ${product.model}</a></td>
                    <td>${product.price}</td>
                    <td>${product.discount.value} ${product.discount.type}</td>
                    <td><form:form method="post" action="addToCart" modelAttribute="chosenProduct">
                        <form:hidden path="id" value="${product.id}"/>
                        <button type="submit" class="btn btn-primary">Buy</button>
                    </form:form></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</section>


    <%--<table style="padding: 20px">
    <tr>
        <th>Manufacturer</th>
        <th>Model</th>
        <th>Price</th>
        <th>Discount</th>
        <th></th>
    </tr>
    <c:forEach items="${products}" var="product">
            <tr>
                <th>${product.manufacturer}</th>
                <th>${product.model}</th>
                <th>${product.price}</th>
                <th>${product.discount.value} ${product.discount.type}</th>
                <th>
                    <form:form method="post" action="addToCart" modelAttribute="chosenProduct">
                        <form:hidden path="id" value="${product.id}"/>
                        <button>Buy</button>
                    </form:form>
                </th>
            </tr>
    </c:forEach>
</table>--%>

</section>
</body>
</html>

