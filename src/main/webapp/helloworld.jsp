<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>hellojsp</title>
</head>
<body>
Hello JSP
${hello}
<form:form method="post" action="saveCategory" modelAttribute="category">
    <form:input path="name"/>
    <form:hidden path="id"/>
    <input type="submit" value="save">
</form:form>
</body>
</html>