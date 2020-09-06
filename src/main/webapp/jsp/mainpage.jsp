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


<div class="col-md-4 col-md-offset-3">
    <c:if test="${passExpiresSoon != null}">
        <div class="alert alert-info" role="alert">${passExpiresSoon}</div>
    </c:if>
</div>

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