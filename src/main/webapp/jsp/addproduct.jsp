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
        <table>
            <tr>
                <th>Select category:</th>
                <th>
                    <form:select path="category">
                        <form:option value="" label="--- Select ---" />
                        <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </th>
                <th><form:errors path="category"/></th>
            </tr>
            <tr>
                <th>Manufacturer:</th>
                <th><form:input path="manufacturer"/></th>
                <th><form:errors path="manufacturer"/></th>
            </tr>
            <tr>
                <th>Model:</th>
                <th><form:input path="model"/></th>
                <th><form:errors path="model"/></th>
            </tr>
            <tr>
                <th>Price:</th>
                <th><form:input path="price"/></th>
                <th><form:errors path="price"/></th>
            </tr>
            <tr>
                <th>Discount:</th>
                <th><form:input path="discount.value"/></th>
                <th>
                    <form:select path="discount.type">
                        <form:options items="${discount_type}"/>
                    </form:select>
                </th>
            </tr>
                <form:hidden path="id"/>
            <tr><th/>
                <th><input type="submit" value="save"></th>
                <th/>
            </tr>
        </table>
    </form:form>
</section>
</body>
</html>