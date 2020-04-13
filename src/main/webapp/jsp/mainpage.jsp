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

<header>Main Page</header>

<%--<nav>
    <c:forEach items="${categories}" var="category">
        <li>
            <a href="${category.id}">${category.name}</a>
        </li>
    </c:forEach>
</nav>--%>

<section>
    <form:form method="post" action="products" modelAttribute="category">
        <form:select path="name" >
            <form:option value="" label="-Please select-"/>
            <form:options items="${categories}" itemValue="name" itemLabel="name"/>
        </form:select>
        <input type="submit" value="search">
    </form:form>
</section>
</body>
</html>