<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="styles.css" media="all">
</head>
<body>
<header>Add a new product</header>

<section>
    <form:form method="post" action="saveProduct" modelAttribute="product">
        <p>Select category:
            <form:select path="category">
                <form:option value="" label="--- Select ---" />
                <form:options items="${categories}" itemValue="id" itemLabel="name" />
            </form:select>
        </p>
        <p>Manufacturer:
            <form:input path="manufacturer"/>
        </p>
        <p>Model:
            <form:input path="model"/>
        </p>
        <p>Price:
            <form:input path="price"/>
        </p>
        <p>Discount:
            <form:input path="discount.value"/>
            <form:select path="discount.type">
                <form:options items="${discount_type}"/>
            </form:select>
        </p>
            <form:hidden path="id"/>
        <input type="submit" value="save">
    </form:form>
</section>
</body>
</html>