<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link type="text/css" rel="stylesheet" href="addproduct_styles.css" media="all">
</head>
<body>
<%@ include file="navbar.jsp" %>

<section>
    <h3 style="font-weight: normal">Add a new product</h3>
    <form:form method="post" action="addproduct" modelAttribute="product">
        <table style="padding: 20px; font-weight: normal">
            <tr>
                <th>Select category:</th>
                <th>
                    <form:select path="category">
                        <form:option value="" label="--- Select ---" />
                        <form:options items="${categories}" itemValue="id" itemLabel="name"/>
                    </form:select>
                </th>
                <th></th>
            </tr>
            <tr>
                <th>Manufacturer:</th>
                <th><form:input path="manufacturer"/></th>
                <th><form:errors path="manufacturer" cssClass="text-danger"/></th>
            </tr>
            <tr>
                <th>Model:</th>
                <th><form:input path="model"/></th>
                <th><form:errors path="model" cssClass="text-danger"/></th>
            </tr>
            <tr>
                <th>Price:</th>
                <th><form:input path="price"/></th>
                <th><form:errors path="price" cssClass="text-danger"/></th>
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